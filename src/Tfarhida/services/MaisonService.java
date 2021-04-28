package Tfarhida.services;

import Tfarhida.base.MaConnexion;
import Tfarhida.entities.Maison;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MaisonService {
    
    Connection cnx;
    
    PreparedStatement ste;
    
    public MaisonService(){
        try {
            cnx = MaConnexion.getinstance().getCnx();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MaisonService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ajouterMaison(Maison m){
        try {
            String sql = "insert into maison(nbr_chambre,tel,nom,adresse,photo,description)"+"values(?,?,?,?,?,?)";
            ste = cnx.prepareStatement(sql);
            ste.setInt(1, m.getNbr_chambre());
            ste.setInt(2, m.getTel());
            ste.setString(3, m.getNom());
            ste.setString(4, m.getAdresse());
            ste.setString(5, m.getPhoto());
            ste.setString(6, m.getDescription());
            ste.executeUpdate();
            System.out.println("Maison ajoutée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public List<Maison> afficherMaison(){
        List<Maison> maisons = new ArrayList<>();
        try {
            String sql = "select * from maison";
            ste = cnx.prepareStatement(sql);
            ResultSet rs = ste.executeQuery();
            while(rs.next()){
                Maison m = new Maison();
                m.setId(rs.getInt("id"));
                m.setNom(rs.getString("nom"));
                m.setAdresse(rs.getString("adresse"));
                m.setNbr_chambre(rs.getInt("nbr_chambre"));
                m.setTel(rs.getInt("tel"));
                m.setPhoto(rs.getString("photo"));
                m.setDescription(rs.getString("description"));
                maisons.add(m);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return maisons;
    }
}
