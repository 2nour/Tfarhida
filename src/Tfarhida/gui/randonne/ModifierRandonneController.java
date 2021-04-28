/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui.randonne;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class ModifierRandonneController implements Initializable {

    @FXML
    private Button AjouterRandonnee;
    @FXML
    private TextField Text_budget;
    @FXML
    private TextField Text_difficulte;
    @FXML
    private TextField Text_duree;
    @FXML
    private TextArea Text_description;
    @FXML
    private TextField Text_activite;
    @FXML
    private DatePicker Datepicker_retour;
    @FXML
    private DatePicker Datepicker_depart;
    @FXML
    private TextField Text_villearrivee;
    @FXML
    private TextField Text_villedepart;
    @FXML
    private TextField image;

  

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void AddRandonnee(ActionEvent event) {
    }
    
}
