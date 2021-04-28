/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.services;

import Tfarhida.base.MaConnexion;
import Tfarhida.entities.Chambre;
import Tfarhida.entities.Maison;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class ChambreService {
    
    Connection cnx;
    
    PreparedStatement ste;
    
    public ChambreService() throws ClassNotFoundException{
        cnx = MaConnexion.getinstance().getCnx();
    }
    
    public void ajouterChambre(Chambre ch){
        try {
            String sql = "insert into chambre(nom, type_lit, nbr_pers, prix, photo, maison_id)"+"values(?,?,?,?,?,?)";
            ste = cnx.prepareStatement(sql);
            ste.setString(1, ch.getNom());
            ste.setString(2, ch.getType_lit());
            ste.setInt(3, ch.getNbr_pers());
            ste.setDouble(4, ch.getPrix());
            ste.setString(5, ch.getPhoto());
            ste.setInt(6, ch.getIdM());
            ste.executeUpdate();
            System.out.println("Chambre ajoutée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public List<Chambre> afficherChambres(){
        List<Chambre> chambres = new ArrayList<>();
        try {
            String sql = "select * from chambre";
            ste = cnx.prepareStatement(sql);
            ResultSet rs = ste.executeQuery();
            while(rs.next()){
                Chambre ch = new Chambre();
                ch.setId(rs.getInt("id"));
                ch.setIdM(rs.getInt("idM"));
                ch.setNom(rs.getString("nom"));
                ch.setType_lit(rs.getString("type_lit"));
                ch.setNbr_pers(rs.getInt("nbr_pers"));
                ch.setPrix(rs.getInt("prix"));
                ch.setPhoto(rs.getString("photo"));
                chambres.add(ch);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return chambres;
    }
    
}
