/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.services;

import Tfarhida.base.MaConnexion;
import Tfarhida.entities.Chambre;
import Tfarhida.entities.Maison;
import Tfarhida.entities.Reservation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class ReservationService {
    
    Connection cnx;
    
    PreparedStatement ste;
    
    public ReservationService(){
        try {
            cnx = MaConnexion.getinstance().getCnx();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ajouterReservation(Reservation ch){
        try {
            String sql = "insert into reservation_maison(id, chambre_id, user_id, date_reservation, etats, nbrJour, totalPrix)"+"values(?,?,?,?,?,?,?)";
            ste = cnx.prepareStatement(sql);
            ste.setInt(1, ch.getId());
            ste.setInt(2, ch.getChambre_id());
            ste.setInt(3, ch.getUser_id());
            ste.setDate(4, new java.sql.Date(ch.getDate().getTime()));
            ste.setString(5, ch.getEtats());
            ste.setInt(6, ch.getNbrJour());
            ste.setDouble(7, ch.getTotalPrix());
            ste.executeUpdate();
            System.out.println("Reservation ajoutée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public List<Reservation> afficherReservations(){
        List<Reservation> reservations = new ArrayList<>();
        try {
            String sql = "select * from reservation_maison";
            ste = cnx.prepareStatement(sql);
            ResultSet rs = ste.executeQuery();
            while(rs.next()){
                Reservation m = new Reservation();
                m.setId(rs.getInt("id"));
                m.setChambre_id(rs.getInt("chambre_id"));
                m.setUser_id(rs.getInt("user_id"));
                m.setDate(new Date(rs.getDate("date_reservation").getTime()));
                m.setEtats(rs.getString("etats"));
                m.setNbrJour(rs.getInt("nbrJour"));
                m.setTotalPrix(rs.getDouble("totalPrix"));
                reservations.add(m);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return reservations;
    }
    
    public Boolean testDate(java.sql.Date date){
        List<Reservation> reservations = new ArrayList<>();
         try {
            String sql = "select * from reservation_maison m WHERE m.date_reservation="+date;
            ste = cnx.prepareStatement(sql);
            ResultSet rs = ste.executeQuery();
            while(rs.next()){
                Reservation m = new Reservation();
                m.setId(rs.getInt("id"));
                m.setChambre_id(rs.getInt("chambre_id"));
                m.setUser_id(rs.getInt("user_id"));
                m.setDate(new Date(rs.getDate("date_reservation").getTime()));
                m.setEtats(rs.getString("etats"));
                m.setNbrJour(rs.getInt("nbrJour"));
                m.setTotalPrix(rs.getDouble("totalPrix"));
                reservations.add(m);
                System.out.println(reservations);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
         if(reservations.size()>0){
        return true;
    }else
        return false;
    }
    
}
