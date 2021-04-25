/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui;

import Tfarhida.entities.Produit;
import Tfarhida.services.PanierService;
import Tfarhida.services.ProduitService;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Nour
 */
public class AfficherPanierController implements Initializable {
    PanierService ps = new PanierService();
    @FXML
    private TableView<Produit> tableProduits;
    @FXML
    private TableColumn<?, ?> colNomProd;
    @FXML
    private TableColumn<?, ?> colQttProd;
    @FXML
    private TableColumn<?, ?> colPrixProd;
    @FXML
    private TableColumn<?, ?> colIdProd;
  ;
    
    ArrayList<Produit>  produits = (ArrayList<Produit>) ps.AfficherProduits();
    @FXML
    private VBox vbox;

     /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 
        
        if (produits.size()>0){
           for(int i=0; i<produits.size();i++)  {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Tfarhida/gui/PanierItem.fxml"));
               Parent parent = loader.load();
                
                System.out.println("homaaaa");   

                PanierItemController panierItemController = loader.getController();
                panierItemController.setData(produits.get(i));
                
                vbox.getChildren().add(parent);
                
                
                
                } catch (IOException ex) {
                Logger.getLogger(AfficherPanierController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("hereee");
            } 
                   
        
        
           }
        
       
    } 
    }
   
    
    
    

    @FXML
    private void getSelected(MouseEvent event) {
    }


    @FXML
    private void BTN_CHECKOUT(ActionEvent event) {
    }
    
}
