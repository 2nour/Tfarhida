/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.services.venteproduit;

import Tfarhida.base.MaConnexion;
import Tfarhida.entities.vente.Categorie;
import Tfarhida.entities.vente.Produit;
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
 * @author Nour
 */
public class CategorieService {

    Connection cnx;
    PreparedStatement ste;
    Statement stm;

    public CategorieService() {
        try {
            cnx = MaConnexion.getinstance().getCnx();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Produit> AfficherProduits() {
        List<Produit> produits = new ArrayList<>();

        String query = "Select * from `produit`";
        try {
            stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery(query);

            while (rst.next()) {
                Produit U = new Produit();
                U.setId(rst.getInt("ID"));
                U.setNom(rst.getString("nom"));
                U.setPrix(rst.getDouble("prix"));
                U.setMarque(rst.getString("marque"));
                U.setDescription(rst.getString("description"));
                U.setQuantite(rst.getInt("quantite"));
                U.setImage(rst.getString("image"));

                produits.add(U);
            }

        } catch (Exception e) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, e);
        }
        return produits;
    }

    public List<Categorie> AfficherCategories() {
        List<Categorie> categories = new ArrayList<>();

        String query = "Select * from `categorie`";
        try {
            stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery(query);

            while (rst.next()) {
                Categorie U = new Categorie();
                U.setId(rst.getInt("ID"));
                U.setNom(rst.getString("nom"));

                categories.add(U);
            }

        } catch (Exception e) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, e);
        }
        return categories;
    }

    public void ajouterCategorie(int p, Categorie c) {
        try {
            String sql = "insert into produit_categorie(produit_id,categorie_id)" + "values(?,?)";
            ste = cnx.prepareStatement(sql);
            ste.setInt(1, p);
            ste.setInt(2, c.getId());

            ste.executeUpdate();
            System.out.println("Categorie ajouté");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Categorie findbynom(String nom) throws SQLException {

        String sql = "select * from categorie where nom='"+nom+"'";

        stm = cnx.createStatement();
        ResultSet rst = stm.executeQuery(sql);
        if (rst.next()) {
            Categorie categorie = new Categorie();
            categorie.setId(rst.getInt("id"));
            categorie.setNom(nom.trim());
            System.out.println("im in service categ" + categorie.getId());

            return categorie;

        }
        return null;
    }

}
