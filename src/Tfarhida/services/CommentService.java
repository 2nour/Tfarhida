/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.services;

import Tfarhida.base.MaConnexion;
import Tfarhida.entities.Comment;
import Tfarhida.entities.Panier;
import Tfarhida.entities.Produit;
import Tfarhida.entities.Utilisateur;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

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
   
     
      public void ajouterCommentProduit(Produit p,Comment com )
    {
        try { 
            String sql="insert into comment(produit_id,contenue,datedecommentaire,user_id)"+"values(?,?,?,?)";
            ste = cnx.prepareStatement(sql);
            ste.setInt(1, p.getId());
            ste.setString(2,com.getContenue());
            ste.setDate(3, (Date) com.getDatedecommentaire());
            ste.setInt(4,u.getId());
            
            ste.executeUpdate();
            System.out.println("comment ajouté");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
      
        public List<Comment> AfficherComments(Produit p) {
        List<Comment>Comments = new ArrayList<>();

        String query = "Select * from comment where produit_id="+p.getId()+"";
        try  {
             stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery(query);

            while(rst.next()) {
                Comment U = new Comment();
                U.setId(rst.getInt("ID"));
                U.setContenue(rst.getString("contenue"));
                U.setDatedecommentaire(rst.getDate("datedecommentaire"));
               
                Comments.add(U);
            }


        } catch(Exception e){
                Logger.getLogger(CommentService.class.getName()).log(Level.SEVERE,null,e);
        }
        return Comments ;
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