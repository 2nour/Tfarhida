/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.services;

import Tfarhida.base.MaConnexion;
import Tfarhida.entities.Randonnee;
import Tfarhida.entities.Reservation;
import Tfarhida.entities.Reservation2;
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
public class ReservationService2 implements IService<Reservation2>  {
private Connection cnx;
private Statement st;
private PreparedStatement pst;
private ResultSet rs;

public ReservationService2() throws ClassNotFoundException{
    cnx=MaConnexion.getinstance().getCnx();
}

   
       
    @Override
    public boolean insert(Reservation2 re) {
     String req="insert into reservation(numreservation,datereservation,observation,montant,id_randonnee,nombrepersonne,etat) values ('"+re.getNumreservation()+"','"+re.getDatereservation()+"','"+re.getObservation()+"','"+re.getMontant()+"','"+re.getRandonnee().getId()+"','"+re.getNombrepersonne()+"','"+re.getEtat()+"')";
    try
    {
        st=cnx.createStatement();
        st.executeUpdate(req);
    return true;        
    }
       catch (SQLException ex) {
           Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
       }
return false; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Reservation2 re) {
     String req="UPDATE reservation SET numreservation='"+re.getNumreservation()+"',datereservation='"+re.getDatereservation()+"', observation='"+re.getObservation()+"',montant='"+re.getMontant() +"',id_randonnee='" +re.getRandonnee().getId()+"',nombrepersonne='"+re.getNombrepersonne() +"',etat='"+re.getEtat()+"' where id='"+re.getId()+"'";
try
    {
        st=cnx.createStatement();
        st.executeUpdate(req);
       return true;       
    }
        
       catch (SQLException ex) {
        Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
    }
return false;  
    }

    @Override
    public boolean delete(int id) {
 String req="DELETE FROM reservation WHERE id='"+id+"'";
try
    {
        st=cnx.createStatement();
        st.executeUpdate(req);
       return true;       
    }
        
       catch (SQLException ex) {
        Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);    
       }
return false;
    }

    @Override
    public List<Reservation2> displayAll() {
        String red="select id,numreservation,datereservation,observation,montant,id_randonnee,nombrepersonne,etat";
    List<Reservation2> list=new ArrayList<>();
    try {
        st=cnx.createStatement();
        rs=st.executeQuery(red);
        RandonneService randonneService = null;
            try {
                randonneService = new RandonneService();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        while(rs.next()){
            int x1=rs.getInt(6);
            Randonnee r= randonneService.displayAll().stream().filter(x->x.getId()==x1).findFirst().get();
            
           list.add(new Reservation2(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getDouble(5),r,rs.getInt(7),rs.getString(8)));
        }
    } catch (SQLException ex) {
        Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return list;
    }

    @Override
    public Reservation2 findById(int id) {
String sql = "Select* from reservation where id=" + id + ";";
        Reservation2 p = null;
        Randonnee r=null;
        RandonneService randonneService=null;
    try {
        randonneService = new RandonneService();
          st = cnx.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                r=randonneService.findById(rs.getInt("id_randonnee"));
               p = new Reservation2(rs.getInt(1),rs.getInt(2),rs.getString(3), rs.getString(4), rs.getInt(5), r,rs.getInt(7),rs.getString(8));
            }
            return p;
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
    }
         catch (SQLException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    return null;
    }
    }

    

 

   

   
    
