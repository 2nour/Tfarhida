/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui.produit;
import Tfarhida.entities.vente.Panier;
import Tfarhida.outils.constants;

import Tfarhida.entities.vente.Produit;
import Tfarhida.services.venteproduit.CommandeService;
import Tfarhida.services.venteproduit.ProduitService;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JOptionPane;


/**
 * FXML Controller class
 *
 * @author Nour
 */
public class CardController  {

    
     File file;
     Produit produit;
     private MyListener myListener;
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
    
    CommandeService cms =new CommandeService();
        Panier pi =new Panier(2,1,0,0.0);


    /**
     * Initializes the controller class.
     */
   
      @FXML
    private void click(MouseEvent mouseEvent) {
        
      myListener.onClickListener(produit); 
     // System.out.println(produit.getNom());   
     // return produit;


    }

  
   

    
     public void setData(Produit p, MyListener myListener){
       if(p != null) {
           this.produit=p;
           this.myListener = myListener;
              try {
          file = new File(constants.getImagePath() + "produits\\" + p.getImage());
       
            Image img = new Image(file.toURI().toURL().toExternalForm());
            ImageView i = new ImageView(img);
             prodImage.setImage(i.getImage());


        } catch (MalformedURLException ex) {
            Logger.getLogger(AfficherListeProduitsController.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex);
        }   
          lnom.setText(p.getNom());
          ldesc.setText(p.getDescription());
          lprix.setText(String.valueOf(p.getPrix())+"dt");
     
       }
     
      
           
 
     
  
    
        
    };

    @FXML
    private void AjouterAuPanier(ActionEvent event) {
        
           
            cms.ajouterCommande(produit, pi);
        
        
    }
     
     
    
    
}
