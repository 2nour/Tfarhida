/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui.reclamation;

import Tfarhida.entities.Reclamation;
import Tfarhida.services.ServiceReclamation;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class AjouterReclamationController implements Initializable {

    @FXML
    private JFXTextField fxObjet;
    @FXML
    private JFXTextArea fxReclamation;
    @FXML
    private Button fxbtn;
    @FXML
    private Button returntoo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void ajouterReclamation(ActionEvent event) {

        ServiceReclamation sr = new ServiceReclamation();
        sr.ajouterReclamation(new Reclamation(fxObjet.getText(), fxReclamation.getText()));
        JOptionPane.showMessageDialog(null, "Votre reclamation a ete envoye");

    }

    @FXML
    private void returntoo(ActionEvent event) throws IOException {
        
           returntoo.getScene().getWindow().hide();

        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("firstPageRec.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Welcome!");
        primaryStage.setScene(scene);
        primaryStage.show(); 
        
    }
}
