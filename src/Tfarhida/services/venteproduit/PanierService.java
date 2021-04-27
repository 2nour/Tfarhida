/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.services.venteproduit;

import Tfarhida.base.MaConnexion;
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
public class PanierService {
    Connection cnx;
    PreparedStatement ste;
    Statement stm;
    public PanierService() {
        try {
            cnx= MaConnexion.getinstance().getCnx();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PanierService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public List<Produit> AfficherProduits() {
        List<Produit>produits = new ArrayList<>();

        String query = "Select p.id,p.nom,p.description,p.prix,c.quantite_produit,p.image from produit p, commande c where p.id = c.produit_id and c.panier_id=2";
        try  {
             stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery(query);

            while(rst.next()) {
               Produit U =new Produit();
                U.setId(rst.getInt("ID"));
                U.setNom(rst.getString("nom"));
                U.setPrix(rst.getDouble("prix"));
                U.setDescription(rst.getString("description"));
                U.setQuantite(rst.getInt("quantite_produit"));
                U.setImage(rst.getString("image"));

                produits.add(U);/*
                
                
              /* String id =rst.getString("id");
               String nom =rst.getString("nom");
               String qtt =rst.getString("quantite_produit");
               String prix=rst.getString("prix");
               
                produits.add(id);
                 produits.add(nom);
                 produits.add(qtt);
                 produits.add(prix);*/
            }


        } catch(Exception e){
                Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE,null,e);
        }
        return produits ;
    } 
    
   


    
}
