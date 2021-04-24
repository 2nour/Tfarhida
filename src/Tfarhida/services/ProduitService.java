/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.services;

import Tfarhida.base.MaConnexion;
import Tfarhida.entities.Produit;
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
public class ProduitService {
    Connection cnx;
    PreparedStatement ste;
    Statement stm;
    public ProduitService() {
        try {
            cnx= MaConnexion.getinstance().getCnx();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ajouterProduit(Produit p)
    {
        try { 
            String sql="insert into produit(nom,quantite,description,prix,marque,image)"+"values(?,?,?,?,?,?)";
            ste = cnx.prepareStatement(sql);
            ste.setString(1, p.getNom());
            ste.setInt(2,p.getQuantite());
            ste.setString(3,p.getDescription());
            ste.setDouble(4,p.getPrix());
            ste.setString(5, p.getMarque());
            ste.setString(6, p.getImage());
            ste.executeUpdate();
            System.out.println("Produit ajouté");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
      public List<Produit> AfficherProduits() {
        List<Produit>produits = new ArrayList<>();

        String query = "Select * from `produit`";
        try  {
             stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery(query);

            while(rst.next()) {
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


        } catch(Exception e){
                Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE,null,e);
        }
        return produits ;
    }
      
       public void ModifierProduit(Produit p) {

        String query = "UPDATE produit SET nom= '"+p.getNom()+"',quantite= '"+p.getQuantite()+"',description='"+p.getDescription()+"'"
              + ",image='"+p.getImage()+"',prix='"+p.getPrix()+"',marque='"+p.getMarque()+"' where id='"+p.getId()+"'";
        try {

             stm = cnx.createStatement();

            stm.executeUpdate(query);

        } catch(SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE,null,ex);
        }
        
     


    }
       
          public Produit findProd(int id){
           
            String query = "select * from produit where id="+id+"";
            try{
               stm = cnx.createStatement();  
                  ResultSet rst = stm.executeQuery(query);

            if(rst.next()) {
                Produit p = new Produit();

                p.setId(rst.getInt("ID"));
                p.setNom(rst.getString("nom"));
                p.setPrix(rst.getDouble("prix"));
                p.setMarque(rst.getString("marque"));
                p.setDescription(rst.getString("description"));
                p.setQuantite(rst.getInt("quantite"));
                p.setImage(rst.getString("image"));
                return p;

            
            }

                
            }
            catch(SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE,null,ex);
        }
         
            return null;
        };
       
        public void SupprimerProduit(int ID) {
        String query="Delete FROM produit WHERE ID="+ID+"";
        try {

            Statement stm = cnx.createStatement();

            stm.executeUpdate(query);

        } catch(SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE,null,ex);
        }

        
    }


    
    
}
