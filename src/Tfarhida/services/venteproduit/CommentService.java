/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.services.venteproduit;
import org.json.JSONObject;
import Tfarhida.base.MaConnexion;
import Tfarhida.entities.vente.Comment;
import Tfarhida.entities.vente.Panier;
import Tfarhida.entities.vente.Produit;
import Tfarhida.entities.vente.Utilisateur;
import Tfarhida.outils.ApiCall;
import Tfarhida.outils.constants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.JSONException;

/**
 *
 * @author Nour
 */


public class CommentService {
    Connection cnx;
    PreparedStatement ste;
    Statement stm;
    Utilisateur u = new Utilisateur(1,"jo@gmail.com","jo","12345678","ROLE_USER");
    
     public CommentService() {
        try {
            cnx= MaConnexion.getinstance().getCnx();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CommentService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
     
      public void ajouterCommentProduit(Produit p,Comment comment) throws IOException, JSONException
    {
        int nb = 0;
        HashMap<String, String> headers = new HashMap();
        String review=comment.getContenue().replaceAll(" ", "%25");
        System.out.println(review);
        headers.put("Accept", "application/json");
        HttpURLConnection conn = ApiCall.callApi("https://api.meaningcloud.com/sentiment-2.1?verbose=y&key=" + constants.getKeyMeaningcloudApi() + "&lang=en&txt=" + review + "&model=general", headers);
         
         
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            JSONObject obj = new JSONObject(br.readLine());
         
        try { 
            
            String sql="insert into comment(produit_id,contenue,datedecommentaire,sentiment,user_id)"+"values(?,?,?,?,?)";
            ste = cnx.prepareStatement(sql);
            ste.setInt(1, p.getId());
            ste.setString(2,comment.getContenue());
            ste.setDate(3, (Date) comment.getDatedecommentaire());
            System.out.println(obj.getString("score_tag"));
            ste.setString(4, obj.getString("score_tag"));
            ste.setInt(5,u.getId());
            
            ste.executeUpdate();
            System.out.println("comment ajouté");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
      
        public List<Comment> AfficherComments(Produit p) {
        List<Comment>Comments = new ArrayList<>();
        
        try  {
             String query = "Select * from comment where produit_id="+p.getId()+"";

             stm = cnx.createStatement();
             ResultSet rst = stm.executeQuery(query);

            if(rst.next()){
                while(rst.next()) {
                Comment U = new Comment();
                String sentimentmeaning ="";
                U.setId(rst.getInt("ID"));
                U.setContenue(rst.getString("contenue"));
                U.setDatedecommentaire(rst.getDate("datedecommentaire"));
                String sentimentRes=rst.getString("sentiment");
                 switch(sentimentRes){
                   case "P+": sentimentmeaning ="excellent";break;
                   case "P": sentimentmeaning ="bon";break;
                   case"NEU":sentimentmeaning ="neutre";break;
                   case "N": sentimentmeaning ="mauvais";break;
                   case "N+":sentimentmeaning ="horrible";break;
                   default:sentimentmeaning ="aucun";break;
               }
                    
                        
                U.setSentiment(sentimentmeaning);
                U.setUsername(u.getUsername());
               
                Comments.add(U);
            }


       
            } 
             } catch(Exception e){
                //Logger.getLogger(CommentService.class.getName()).log(Level.SEVERE,null,e);
        }
        return Comments ;  
          
    }
        
        
         public List<Comment> getPostivesAvis(Produit p) {

        List<Comment> comments = new ArrayList();

       
            String req = "select * from comment where sentiment like('P%') and where produit_id="+p.getId()+"  order by id  desc";
           
            try  {
             stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery(req);

            while(rst.next()) {
                Comment U = new Comment();
                U.setId(rst.getInt("ID"));
                U.setContenue(rst.getString("contenue"));
                U.setDatedecommentaire(rst.getDate("datedecommentaire"));
                U.setSentiment(rst.getString("sentiment"));
                U.setUsername(u.getUsername());
               
                comments.add(U);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return comments;
    }

        
        public List<Integer> displayAvisStatistic(Produit p){
        List<Comment> as= AfficherComments(p);
        List<Integer> rev=new ArrayList<Integer>();
        int nbpos=0;
        int nbneg=0;
        int neutre=0;
        for(Comment avi:as){
            String text=avi.getContenue().replaceAll(" ","%20");
            HashMap<String,String> headers=new HashMap();
            headers.put("Accept", "application/json");
            HttpURLConnection conn=ApiCall.callApi("https://api.meaningcloud.com/sentiment-2.1?verbose=y&key="+constants.getKeyMeaningcloudApi()+"&lang=en&txt="+text+"&model=general", headers);
            try {
               if (conn.getResponseCode() != 200) {
                   throw new RuntimeException("Failed : HTTP Error code : "
                           + conn.getResponseCode());
               }
               InputStreamReader in = new InputStreamReader(conn.getInputStream());
               BufferedReader br = new BufferedReader(in);
               JSONObject obj = new JSONObject(br.readLine());
               String sentimentRes=obj.getString("score_tag");
               switch(sentimentRes){
                   case "P":case"P+":nbpos++;break;
                   case "N":case "N+":nbneg++;break;
                   case "NEU":neutre++;break;
                   default:neutre++;break;
               }
               

           } catch (Exception e) {
               System.out.println("Exception in NetClientGet:- " + e);
           }
           
        }
        if(as.size()>0){
        nbpos=nbpos*100/as.size();
        nbneg=nbneg*100/as.size();
        neutre=neutre*100/as.size();  
        }
       
        System.out.println("Avis Positive : "+nbpos+"% ,Négative : "+nbneg+"%"+" Neutre : "+neutre+"%");
        rev.add(0, nbpos);
        rev.add(1,nbneg);
        rev.add(2,neutre);
        return rev;
        
    }
        
        
        
        
        
      
     public void SupprimerComment(int ID) {
        String query="Delete FROM comment WHERE ID="+ID+"";
        try {

            Statement stm = cnx.createStatement();

            stm.executeUpdate(query);

        } catch(SQLException ex) {
            Logger.getLogger(CommentService.class.getName()).log(Level.SEVERE,null,ex);
        }

        
    }
    
    
}