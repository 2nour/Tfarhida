/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui.produit;

import Tfarhida.entities.vente.Produit;
import Tfarhida.services.venteproduit.ProduitService;
import javafx.geometry.Insets;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Nour
 */
public class TestTemplateController implements Initializable {

    ProduitService ps= new ProduitService();
   ArrayList<Produit>  produits = (ArrayList<Produit>) ps.AfficherProduits();
    private MyListener myListener;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;
    
    
    

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    public void produitClicked(Produit p){
        if(p !=null){
         try{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Tfarhida/gui/VoirProduit.fxml"));
        Parent parent =loader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Voir produit");
        VoirProduitController controler = loader.getController();
            // System.out.println("im herrrrrrrre");
        stage.setScene(scene);
        controler.setProduit(p);
        stage.show();
        }catch(IOException ex){
            ex.printStackTrace();
           System.out.println(ex.getMessage());
           System.out.println("im herrrrrrrre in err");
    }   
        }
         
    }

    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        int column = 0;
        int row = 1; 
        if (produits.size()>0){
           // produitClicked(produits.get(0));
            myListener = new MyListener() {
                
                @Override
                public void onClickListener(Produit p) {
                   produitClicked( p) ;
                }
            };
            
            
           for(int i=0; i<produits.size();i++)  {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Tfarhida/gui/Card.fxml"));
                Parent parent = loader.load();
                

                CardController cardController;
                cardController = loader.getController();
                cardController.setData(produits.get(i),myListener);
                
                
                
                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(parent, column++, row);
                 //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                
                GridPane.setMargin(parent, new Insets(10));

                } catch (IOException ex) {
                Logger.getLogger(TestTemplateController.class.getName()).log(Level.SEVERE, null, ex);
            } 
                   
        }
    
                
           
       
        
    } ;  
    
   
}

    @FXML
    private void voirPanier(ActionEvent event) {
        
        try{
        Parent parent = FXMLLoader.load(getClass().getResource("/Tfarhida/gui/AfficherPanier.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        //stage.initStyle(StageStyle.UTILITY);
        stage.show();
        }catch(IOException ex){
            System.out.println(ex.getMessage());
    }
        
    }
    
       
    
}