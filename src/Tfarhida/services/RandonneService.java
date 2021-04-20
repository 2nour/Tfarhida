/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.services;

import Tfarhida.base.MaConnexion;
import Tfarhida.entities.Randonnee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sun.security.pkcs11.Secmod;

/**
 *
 * @author PC
 */
public class RandonneService implements IService<Randonnee> {
    private Connection cnx;
private Statement st;
private PreparedStatement pst;
private ResultSet rs;

public RandonneService() throws ClassNotFoundException{
    cnx=MaConnexion.getinstance().getCnx();
}

    @Override
    public boolean insert(Randonnee r) {
            
         String req="insert into randonnee (villedepart,villearrivee,datedepart,dateretour,activite,description,image,duree,difficulte,budget) values ('"+r.getVilledepart()+"','"+r.getVillearrivee()+"','"+r.getDatedepart()+"','"+r.getDateretour()
                 +"','"+r.getActivite()+"','"+r.getDescription()+"','"+r.getImage()+"','"+r.getDuree()+"','"+r.getDifficulte()+"','"+r.getBudget()+"')";
    try
    {
        st=cnx.createStatement();
        st.executeUpdate(req);
    return true;        
    }
       catch (SQLException ex) {
           Logger.getLogger(RandonneService.class.getName()).log(Level.SEVERE, null, ex);
       }
return false; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Randonnee r) {
 String req="UPDATE randonnee SET villedepart='"+r.getVilledepart()+"',villearrivee='"+r.getVillearrivee()+"', datedepart='"+r.getDatedepart()+"',dateretour='"+r.getDateretour()
                 +"',activite='"+r.getActivite()+"',description='"+r.getDescription()+"',image='"+r.getImage()+"',duree='"+r.getDuree()+"',difficulte='"+r.getDifficulte()+"',budget='"+r.getBudget()+"' where id='"+r.getId()+"'";
try
    {
        st=cnx.createStatement();
        st.executeUpdate(req);
       return true;       
    }
        
       catch (SQLException ex) {
        Logger.getLogger(RandonneService.class.getName()).log(Level.SEVERE, null, ex);
    }
return false;    }

    @Override
    public boolean delete(int id) {
 String req="DELETE FROM randonnee WHERE id='"+id+"'";
try
    {
        st=cnx.createStatement();
        st.executeUpdate(req);
       return true;       
    }
        
       catch (SQLException ex) {
        Logger.getLogger(RandonneService.class.getName()).log(Level.SEVERE, null, ex);    
       }
return false;
    }
    @Override
    public List<Randonnee> displayAll() {
        String red="select id,villedepart,villearrivee,datedepart,dateretour,activite,description,image,duree,difficulte,budget from randonnee";
    List<Randonnee> list=new ArrayList<>();
    try {
        st=cnx.createStatement();
        rs=st.executeQuery(red);
        while(rs.next()){
           list.add(new Randonnee(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getInt(9),rs.getString(10),rs.getInt(11)));
        }
    } catch (SQLException ex) {
        Logger.getLogger(RandonneService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return list;
    }

    @Override
    public Randonnee findById(int id) {
       String sql = "Select* from randonnee where id=" + id + ";";
       Randonnee p = null;
        try {
            st = cnx.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
               p=new Randonnee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),rs.getString(7),rs.getString(8),rs.getInt(9),rs.getString(10),rs.getInt(11));
            }
            return p;

        } catch (SQLException ex) {
            Logger.getLogger(RandonneService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } //To change body of generated methods, choose Tools | Templates.
    }
    
     public ObservableList<Randonnee> trier() throws SQLException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         ObservableList<Randonnee> ls = FXCollections.observableArrayList();
          Statement st = cnx.createStatement();
        String req = "SELECT * FROM randonnee ORDER BY randonnee.villedepart ASC";
        ResultSet rs = st.executeQuery(req);

        while(rs.next()){
            int id   = rs.getInt("id");
            String villedepart   = rs.getString("villedepart");
            String villearrivee = rs.getString("villearrivee");
            String datedepart = rs.getString("datedepart");
            String dateretour = rs.getString("dateretour");
            String activite = rs.getString("activite");
            String description = rs.getString("description");
            int duree = rs.getInt("duree");
             String difficulte = rs.getString("difficulte");
             int budget = rs.getInt("budget");
             String image=rs.getString("image");
            Randonnee r = new Randonnee(villedepart,villearrivee,datedepart,dateretour,activite,description,image,duree,difficulte,budget);
            ls.add(r);
        }
        return ls;
    }
    }

   

   
    
