/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.services.randonne;

import Tfarhida.base.MaConnexion;
import Tfarhida.entities.randonne.CommentaireR;
import Tfarhida.entities.randonne.Randonnee;
import Tfarhida.entities.randonne.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
public class CommentaireService implements  IService<CommentaireR>{
    
     private Connection cnx;
private Statement st;
private PreparedStatement pst;
private ResultSet rs;

public CommentaireService() throws ClassNotFoundException{
    cnx=MaConnexion.getinstance().getCnx();
}



   
    public boolean insert(CommentaireR c) {
        
       String req="insert into commentaire (contenue,datedecommentaire,randonne_id,user_id) values ('"+c.getContenue()+"','"+c.getDatedecommentaire()+"','"+c.getR().getId()+"','"+c.getU().getId()+"')";
         try
    {
        st=cnx.createStatement();
        st.executeUpdate(req);
    return true;        
    }
       catch (SQLException ex) {
           Logger.getLogger(CommentaireService.class.getName()).log(Level.SEVERE, null, ex);
       }
return false;
    }

 
    public boolean update(CommentaireR c) {
        String req="UPDATE commentaire SET contenue='"+c.getContenue()+"',datedecommentaire='"+c.getDatedecommentaire()+"', randonne_id='"+c.getR().getId()+"',user_id='"+c.g.getId()+"'";
    try
    {
        st=cnx.createStatement();
        st.executeUpdate(req);
       return true;       
    }
        
       catch (SQLException ex) {
        Logger.getLogger(CommentaireService.class.getName()).log(Level.SEVERE, null, ex);
    }
return false;    }
   
    public boolean delete(int id) {
        String req="DELETE FROM commentaire WHERE id='"+id+"'";
    try
    {
        st=cnx.createStatement();
        st.executeUpdate(req);
       return true;       
    }
        
       catch (SQLException ex) {
        Logger.getLogger(CommentaireService.class.getName()).log(Level.SEVERE, null, ex);
    }
return false;    }

    
    public List<CommentaireR> displayAll() {
        String red="select id,contenue,datedecommentaire,randonne_id,user_id";
    List<CommentaireR> list=new ArrayList<>();
    try {
        st=cnx.createStatement();
        rs=st.executeQuery(red);
        RandonneService randonneService=new RandonneService();
        while(rs.next()){
                    Randonnee r=randonneService.findById(rs.getInt(4));

           list.add(new CommentaireR(rs.getInt(1),rs.getString(2),rs.getString(3),r,new User(1,"hana@gmail.com","hana1","hana1234","hana1234","aaaa","ROLE_USER","aaaaaaaaaaaaa")));
        }
    } catch (SQLException ex) {
        Logger.getLogger(CommentaireService.class.getName()).log(Level.SEVERE, null, ex);
    }    catch (ClassNotFoundException ex) {
             Logger.getLogger(CommentaireService.class.getName()).log(Level.SEVERE, null, ex);
         }
    return list;
    }
    

  
    public CommentaireR findById(int id) {
      String sql = "Select* from commentaire where id=" + id + ";";
       CommentaireR c = null;
        try {
                    RandonneService randonneService=new RandonneService();

            st = cnx.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                 Randonnee r=randonneService.findById(rs.getInt(4));
               c=new CommentaireR(rs.getInt(1), rs.getString(2), rs.getString(3), r, new User(1,"hana@gmail.com","hana1","hana1234","hana1234","aaaa","ROLE_USER","aaaaaaaaaaaaa"));
            }
            return c;

        } catch (SQLException ex) {
            Logger.getLogger(CommentaireService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ClassNotFoundException ex) {
             Logger.getLogger(CommentaireService.class.getName()).log(Level.SEVERE, null, ex);
         } 
        return null;
    }
   
    
}
