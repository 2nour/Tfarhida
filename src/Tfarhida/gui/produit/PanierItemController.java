/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui.produit;

import Tfarhida.entities.vente.Commande;
import Tfarhida.entities.vente.Panier;
import Tfarhida.entities.vente.Produit;
import Tfarhida.outils.constants;
import Tfarhida.services.venteproduit.CommandeService;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Nour
 */
public class PanierItemController  {

    @FXML
    private ImageView prodImg;
    @FXML
    private Text ProdNom;
    @FXML
    private Text ProdDesc;
    @FXML
    private Text ProdQtt;
    @FXML
    private Text ProdPrix;
    File file;
    Produit produit;
    private MyListener myListener;
    CommandeService cms =new CommandeService();
    Panier panier =new Panier(2,1,0,0.0);
    @FXML
    private JFXButton plusid;
    @FXML
    private JFXButton minusid;
    
    /**
     * Initializes the controller class.
     */
    
    public void init() throws SQLException{
        setData(produit);
        if(produit.getQuantite()==0){
            minusid.setDisable(true);
            cms.supprimerCommande(produit, panier);
        }
        
    }
   
    public void setData(Produit p){
        this.produit=p;
        if(p != null) {
         
          System.out.println(p.getNom());   
              try {
          file = new File(constants.getImagePath() + "produits\\" + p.getImage());
       
            Image img = new Image(file.toURI().toURL().toExternalForm());
            ImageView i = new ImageView(img);
             System.out.println(p.getNom());   

             prodImg.setImage(i.getImage());


        } catch (MalformedURLException ex) {
            Logger.getLogger(AfficherListeProduitsController.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex);
        }   
          ProdNom.setText(p.getNom());
          ProdDesc.setText(p.getDescription());
          ProdPrix.setText(String.valueOf(p.getPrix()));
          ProdQtt.setText(String.valueOf(p.getQuantite()));
          ProdPrix.setText(String.valueOf(p.getPrix()+" dt"));
     
       }
     
      

        
    }

    @FXML
    private void plus(ActionEvent event) {
       
      
        cms.ajouterCommande(produit, panier);
        ProdQtt.setText(String.valueOf(produit.getQuantite() +1));
        setData(produit);
        
    }

    @FXML
    private void minus(ActionEvent event) {
        
        cms.moinsdeqtt(produit, panier);
        
    }

    

    @FXML
    private void click(MouseEvent event) {

    }

    @FXML
    private void delete(MouseEvent event) throws SQLException {
        cms.supprimerCommande(produit, panier);
        
    }
    
}
