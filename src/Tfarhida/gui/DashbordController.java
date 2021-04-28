/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class DashbordController implements Initializable {

    @FXML
    private JFXButton btnUsers;
    @FXML
    private JFXButton btnDemandeOrg;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void redirecttooo(ActionEvent event) throws IOException {
        
       btnUsers.getScene().getWindow().hide();

        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("GererUser.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Welcome!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    @FXML
    private void gererProduit(ActionEvent event) throws IOException {
        
        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/Tfarhida/gui/produit/AfficherListeProduits.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Welcome!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    @FXML
    private void DemandeOrganisation(ActionEvent event) throws IOException {
        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/Tfarhida/gui/Organisation/OrganisationAdmin.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Welcome!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}
