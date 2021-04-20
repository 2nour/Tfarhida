/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui;

import Tfarhida.entities.Produit;
import Tfarhida.services.PanierService;
import Tfarhida.services.ProduitService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 
        refresh();
    } 
    void refresh(){
       colIdProd.setCellValueFactory(new PropertyValueFactory<>("id"));
       colNomProd.setCellValueFactory(new PropertyValueFactory<>("nom"));
       colQttProd.setCellValueFactory(new PropertyValueFactory<>("quantite"));
       colPrixProd.setCellValueFactory(new PropertyValueFactory<>("prix"));
      
       this.tableProduits.setItems(FXCollections.observableArrayList(ps.AfficherProduits()));
    }

    @FXML
    private void getSelected(MouseEvent event) {
    }
    
}
