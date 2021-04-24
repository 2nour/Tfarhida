/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui;

import Tfarhida.entities.Produit;
import Tfarhida.entities.constants.constants;
import Tfarhida.services.ProduitService;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


/**
 * FXML Controller class
 *
 * @author Nour
 */
public class CardController  {

    
     File file;
     Produit produit;
    
    @FXML
    private VBox card;
    @FXML
    private ImageView prodImage;
    @FXML
    private Text lnom;
    @FXML
    private Text ldesc;
    @FXML
    private Text lprix;

    /**
     * Initializes the controller class.
     */
   
      @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(produit);
    }

  
    private MyListener myListener;

    
     public void setData(Produit p){
       if(p != null) {
         
          System.out.println(p.getNom());   
              try {
          file = new File(constants.getImagePath() + "produits\\" + p.getImage());
       
            Image img = new Image(file.toURI().toURL().toExternalForm());
            ImageView i = new ImageView(img);
             System.out.println(p.getNom());   

             prodImage.setImage(i.getImage());


        } catch (MalformedURLException ex) {
            Logger.getLogger(AfficherListeProduitsController.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex);
        }   
          lnom.setText(p.getNom());
          ldesc.setText(p.getDescription());
          System.out.println("im in 2"); 
         // lprix.setText(String.valueOf(p.getPrix()));
          System.out.println("i m alll here");  
     
       }
     
       else {
           
        System.out.println("i m empty");   
 
       }

    
        
    };
    
    
}
