/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui;

import Tfarhida.entities.Commande;
import Tfarhida.entities.Produit;
import Tfarhida.outils.constants;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    /**
     * Initializes the controller class.
     */
   
    public void setData(Produit p){
        
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
          System.out.println("im in 2"); 
         // lprix.setText(String.valueOf(p.getPrix()));
          System.out.println("i m alll here");  
     
       }
     
       else {
           
        System.out.println("i m empty");   
 
       }

        
    }

    @FXML
    private void plus(ActionEvent event) {
    }

    @FXML
    private void minus(ActionEvent event) {
    }

    @FXML
    private void delete(ActionEvent event) {
    }
    
}
