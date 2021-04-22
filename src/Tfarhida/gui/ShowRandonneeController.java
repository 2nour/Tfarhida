/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui;

import Tfarhida.entities.Randonnee;
import static Tfarhida.gui.AfficheRandonneeController.list;
import Tfarhida.services.RandonneService;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class ShowRandonneeController implements Initializable {

      @FXML
    private TextField rand_vdepart;

    @FXML
    private TextField rand_varrive;

    @FXML
    private DatePicker rand_dated;

    @FXML
    private DatePicker rand_dater;

    @FXML
    private TextField rand_activite;

    @FXML
    private TextField rand_duree;

    @FXML
    private TextField rand_diff;

    @FXML
    private TextField rand_budget;

    @FXML
    private TextArea rand_desc;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
    }
    
    }
    
