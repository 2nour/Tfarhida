/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.services.randonne;

import Tfarhida.base.MaConnexion;
import Tfarhida.entities.randonne.Post_like;
import Tfarhida.entities.randonne.Randonnee;
import Tfarhida.entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static sun.misc.MessageUtils.where;

/**
 *
 * @author PC
 */
public class PostLikeService implements IService<Post_like>{
    private Connection cnx;
private Statement st;
private PreparedStatement pst;
private ResultSet rs;

public PostLikeService() throws ClassNotFoundException{
    cnx=MaConnexion.getinstance().getCnx();
}
    

    @Override
    public boolean insert(Post_like p) {
        String req="insert into post_like (randonnee_id,user_id) values ('"+p.getRandonnee().getId()+"','"+p.getUser().getId()+"')";
    try
    {
        st=cnx.createStatement();
        st.executeUpdate(req);
    return true;        
    }
       catch (SQLException ex) {
           Logger.getLogger(PostLikeService.class.getName()).log(Level.SEVERE, null, ex);
       }
return false; //To change body of generated methods, choose Tools | Templates.
    }
    

    @Override
    public boolean update(Post_like p) {
        String req="UPDATE post_like SET randonnee_id='"+p.getRandonnee().getId()+"',user_id='"+p.getUser().getId()+ "where id='"+p.getId()+"'";
try
    {
        st=cnx.createStatement();
        st.executeUpdate(req);
       return true;       
    }
        
       catch (SQLException ex) {
        Logger.getLogger(PostLikeService.class.getName()).log(Level.SEVERE, null, ex);
    }
return false;    }

    

    @Override
    public boolean delete(int id) {
        String req="DELETE FROM post_like WHERE id='"+id+"'";
try
    {
        st=cnx.createStatement();
        st.executeUpdate(req);
       return true;       
    }
        
       catch (SQLException ex) {
        Logger.getLogger(PostLikeService.class.getName()).log(Level.SEVERE, null, ex);    
       }
return false;
    }

    @Override
    public List<Post_like> displayAll() {
         String red="select id,randonnee_id,user_id, from post_like";
    List<Post_like> list=new ArrayList<>();
    try {
        st=cnx.createStatement();
        rs=st.executeQuery(red);
                      RandonneService randonneService=new RandonneService();
                                      Randonnee r=randonneService.findById(rs.getInt(2));


        while(rs.next()){
           list.add(new Post_like(rs.getInt(1),r,new User(1,"hana@gmail.com","hana1","hana1234","hana1234","aaaa","ROLE_USER","aaaaaaaaaaaaa")));
        }
    } catch (SQLException ex) {
        Logger.getLogger(PostLikeService.class.getName()).log(Level.SEVERE, null, ex);
    }   catch (ClassNotFoundException ex) {
            Logger.getLogger(PostLikeService.class.getName()).log(Level.SEVERE, null, ex);
        }
    return list;
    }

    @Override
    public Post_like findById(int id) {
         String sql = "Select* from post_like where id=" + id + ";";
       Post_like p= null;
        try {
            st = cnx.createStatement();
            rs = st.executeQuery(sql);
              RandonneService randonneService=new RandonneService();
            if (rs.next()) {
                Randonnee r=randonneService.findById(rs.getInt(2));
               p=new Post_like(rs.getInt(1),r,new User(1,"hana@gmail.com","hana1","hana1234","hana1234","aaaa","ROLE_USER","aaaaaaaaaaaaa"));
            }
            return p;

        } catch (SQLException ex) {
            Logger.getLogger(PostLikeService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PostLikeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;//To change body of generated methods, choose Tools | Templates.
    }
    
}
