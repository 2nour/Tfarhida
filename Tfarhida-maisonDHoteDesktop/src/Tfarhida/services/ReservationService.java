/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.services;

import Tfarhida.base.MaConnexion;
import Tfarhida.entities.Chambre;
import Tfarhida.entities.Reservation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author User
 */
public class ReservationService {
    
    Connection cnx;
    
    PreparedStatement ste;
    
    public ReservationService() throws ClassNotFoundException{
        cnx = MaConnexion.getinstance().getCnx();
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
    
    
}
