/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui;

import Tfarhida.entities.Randonnee;
import static Tfarhida.gui.AfficheRandonneeClientController.r;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author PC
 */
public class DetailRandonneeClientController implements Initializable {

    
    private TableView<Randonnee> tableRand;
    public static ObservableList<Randonnee> list;
   @FXML
    private JFXTextField Text_id;

    @FXML
    private JFXTextField Text_villedepart;

    @FXML
    private JFXTextField Text_villearrivee;

    @FXML
    private JFXTextField Datepicker_depart;

    @FXML
    private JFXTextField Datepicker_retour;

    @FXML
    private JFXTextField Text_activite;

    @FXML
    private JFXTextField Text_difficulte;

    @FXML
    private JFXTextField Text_budget;

    @FXML
    private JFXTextArea Text_description;

     @FXML
    private ImageView image_Randonnee;
      @FXML
    private TextField Text_duree;
       @FXML
    private AnchorPane anchropane;
     
    @FXML
    private Button ReserverRandonnee;
    
    
    
     
    public Randonnee r1= AfficheRandonneeClientController.r;
      
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     Image image = new Image("/images/randonnee.jpg"); 
     image_Randonnee.setImage(image);
        System.out.println(r1);
       // Text_id.setText(Integer.toString(r1.getId()));
        
        Text_activite.setText(r1.getActivite());
        Text_villedepart.setText(r1.getVilledepart());
        Text_villearrivee.setText(r1.getVillearrivee());
        Datepicker_depart.setText(r1.getDateretour());
        Datepicker_retour.setText(r1.getDateretour());
        Text_difficulte.setText(r1.getDifficulte());
        Text_budget.setText(Integer.toString(r1.getBudget()));
        Text_description.setText(r1.getDescription());
         Text_duree.setText(Integer.toString(r1.getDuree()));
        
        
     
    }  

     public  void DetailsRandonnee() {
        Randonnee r=tableRand.getSelectionModel().getSelectedItem();
        Text_id.setText(Integer.toString(r.getId()));
        Text_budget.setText(Integer.toString(r.getBudget()));
        Text_activite.setText(r.getActivite());
        Text_description.setText(r.getDescription());
        Text_difficulte.setText(r.getDifficulte());
        Text_duree.setText(Integer.toString(r.getDuree()));
        Text_villearrivee.setText(r.getVillearrivee());
        Text_villedepart.setText(r.getVilledepart());
        System.out.println(r.getDatedepart()+" "+r.getDateretour());
       // Datepicker_depart.setValue(LocalDate.parse(r.getDatedepart()));
       // Datepicker_retour.setValue(LocalDate.parse(r.getDateretour()));
        
    }
    
       
}
    
