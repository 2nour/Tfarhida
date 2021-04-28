/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.services.venteproduit;

import Tfarhida.base.MaConnexion;
import Tfarhida.base.UserSession;
import Tfarhida.entities.vente.Commande;
import Tfarhida.entities.vente.Panier;
import Tfarhida.entities.vente.Produit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Nour
 */
public class CommandeService {
    Connection cnx;
    PreparedStatement ste;
    Statement stm;
    int u = UserSession.getId();
    public CommandeService() {
        try {
            cnx= MaConnexion.getinstance().getCnx();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CommandeService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public void ajouterCommande(Produit p,Panier pi)
    {
        try { 
            //verifier si la commande existe
            String exsist= "select * from commande where panier_id='" +pi.getId()+"'"+" and produit_id='"+p.getId()+"'";
            
            
            System.out.println("panier is"+ pi.getId());
           System.out.println("user is"+ UserSession.getId());

            
            
            stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery(exsist);
            if(rst.next())
            {
                int qtt=Integer.valueOf(rst.getInt("quantite_produit"));
                Double prixCommande= Double.valueOf(rst.getDouble("prixcommande")) + p.getPrix();
                
                //update commande
                Commande c = new Commande();
                c.setPanier_id(pi.getId());
                c.setId(rst.getInt("id"));
                c.setProduit_id(rst.getInt("Produit_id"));
                c.setPanier_id(pi.getId());
                c.setQuantite_produit(qtt+1);
                c.setPrixcommande(prixCommande);
                
                String updateCommande="UPDATE commande SET quantite_produit='"+c.getQuantite_produit()+"'"+ ",prixcommande='"+c.getPrixcommande()+"' where id='"+c.getId()+"'";
                ste = cnx.prepareStatement(updateCommande);
                ste.executeUpdate();
             
             
            }
           
           else{
            
            String sql="insert into commande(produit_id,panier_id,quantite_produit,prixcommande)"+"values(?,?,?,?)";
            ste = cnx.prepareStatement(sql);
            ste.setInt(1, p.getId());
            ste.setInt(2,pi.getId());
            ste.setInt(3,1);
            ste.setDouble(4,p.getPrix());
            ste.executeUpdate();

            System.out.println("Produit ajouté");
        }
            
                //Update panier
                String getpanier= "select * from panier where id ='" +pi.getId()+"'"+" and user_id='"+u+"'";
                stm = cnx.createStatement();
                ResultSet rste = stm.executeQuery(getpanier);
                
                if(rste.next()){
                   
                    int id=Integer.valueOf(rste.getInt("id"));
                    int nbprod = Integer.valueOf(rste.getInt("nbproduit"))+1;
                    Double somme = Double.valueOf(rste.getDouble("somme")+p.getPrix());
                    
                    String updatePanier="UPDATE panier SET nbproduit= '"+nbprod+"'"+",somme= '"+somme+"'"+" where id='"+id+"'";
                    
                    ste = cnx.prepareStatement(updatePanier);
                    ste.executeUpdate();
                    JOptionPane.showMessageDialog(null, "produit ajouter au panier");

                    
                }               
        }catch (Exception ex) {
            System.out.println(ex.getMessage()+"hiii");
        }
    }
     
     public Integer getQtt(Produit p,Panier pi)
     {
         int qtt=0;
     
          try { 
            //verifier si la commande existe
            String exsist= "select * from commande where panier_id='" +pi.getId()+"'"+" and produit_id='"+p.getId()+"'";
            stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery(exsist);
            if(rst.next())
            {
               qtt=rst.getInt("quantite_produit");
               
            }
            
     }
          
    catch (SQLException ex) {
            System.out.println(ex.getMessage());
       }
       return qtt;

}
     
     public void supprimerCommande(Produit produit,Panier panier) throws SQLException{
         
          String exsist= "select * from commande where panier_id='" +panier.getId()+"'"+" and produit_id='"+produit.getId()+"'";
            stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery(exsist);
            if(rst.next())
            {
                int nbprod = Integer.valueOf(rst.getInt("quantite_produit"));
                Double prixcommande= produit.getPrix() *nbprod ;
                Panier pi =new Panier();
                pi.setId(panier.getId());
                pi.setNbproduit(panier.getNbproduit() - nbprod);
                pi.setSomme(panier.getSomme() - prixcommande);
                 String updatePanier="UPDATE panier SET nbproduit= '"+pi.getNbproduit()+"'"+",somme= '"+pi.getSomme()+
                         "'"+" where id='"+pi.getId()+"'";
                    
                    ste = cnx.prepareStatement(updatePanier);
                    ste.executeUpdate();

                
            }
         
        
         String query="Delete FROM commande WHERE  panier_id='" +panier.getId()+"'"+" and produit_id='"+produit.getId()+"'";

        try {

            Statement stm = cnx.createStatement();

            stm.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "produit supprimé du panier");


        } catch(SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE,null,ex);
        }
         
         
     };
     
     
     
     public void moinsdeqtt(Produit p,Panier pi){
         try { 
            //verifier si la commande existe
            String exsist= "select * from commande where panier_id='" +pi.getId()+"'"+" and produit_id='"+p.getId()+"'";
            stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery(exsist);
            if(rst.next())
            {
                int qtt=Integer.valueOf(rst.getInt("quantite_produit"));
                Double prixCommande= Double.valueOf(rst.getDouble("prixcommande")) - p.getPrix();
                
                //update commande
                Commande c = new Commande();
                c.setPanier_id(rst.getInt("panier_id"));
                c.setId(rst.getInt("id"));
                c.setProduit_id(rst.getInt("Produit_id"));
                
                c.setQuantite_produit(qtt-1);
                
                c.setPrixcommande(prixCommande);
                
                String updateCommande="UPDATE commande SET quantite_produit='"+c.getQuantite_produit()+"'"+ ",prixcommande='"+c.getPrixcommande()+"' where id='"+c.getId()+"'";
                ste = cnx.prepareStatement(updateCommande);
                ste.executeUpdate();
             
             
            }
           
          
                //Update panier
                String getpanier= "select * from panier where id ='" +pi.getId()+"'"+" and user_id='"+u+"'";
                stm = cnx.createStatement();
                ResultSet rste = stm.executeQuery(getpanier);
                
                if(rste.next()){
                   
                    int id=Integer.valueOf(rste.getInt("id"));
                    int nbprod = Integer.valueOf(rste.getInt("nbproduit"))-1;
                    Double somme = Double.valueOf(rste.getDouble("somme")-p.getPrix());
                    
                    String updatePanier="UPDATE panier SET nbproduit= '"+nbprod+"'"+",somme= '"+somme+"'"+" where id='"+id+"'";
                    
                    ste = cnx.prepareStatement(updatePanier);
                    ste.executeUpdate();
                    JOptionPane.showMessageDialog(null, "produit ajouter au panier");

                    
                }               
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
     }
     
     
}