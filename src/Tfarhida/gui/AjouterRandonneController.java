/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui;

import Tfarhida.entities.Randonnee;
import static Tfarhida.gui.AfficheRandonneeController.list;
import Tfarhida.services.RandonneService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class AjouterRandonneController implements Initializable {

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
    private TextField Text_id;

    @FXML
    private Button AjouterRandonnee;

    @FXML
    private Button image;

    @FXML
    private Text textimg;

    @FXML
    private ImageView image_Randonnee;

 
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    void handleAdd(java.awt.event.ActionEvent event) {
 RandonneService randonneeService=null;
        try {
            randonneeService=new RandonneService();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AjouterRandonneController.class.getName()).log(Level.SEVERE, null, ex);
        }
            Randonnee r=new Randonnee(Text_villedepart.getText(), Text_villearrivee.getText(),Datepicker_depart.getValue().toString(), Datepicker_retour.getValue().toString(),Text_activite.getText(), Text_description.getText(), "aaa",Integer.parseInt(Text_duree.getText()), Text_difficulte.getText(), Integer.parseInt(Text_budget.getText()));
            randonneeService.insert(r);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Randonnee ajouter avec success");
            a.show();
        
        
    }
    
}