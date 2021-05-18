/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui.Organisation;

import Tfarhida.entities.Organisation;
import Tfarhida.services.OrganisationService;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author 21651
 */
public class AfficheBestOrgController implements Initializable {

    @FXML
    private TableView<Organisation> tableview;
    private TableColumn<?, ?> clnid;
    @FXML
    private TableColumn<?, ?> clnbpersonne;
    @FXML
    private TableColumn<?, ?> clnnbJours;
    @FXML
    private TableColumn<?, ?> clndate;
    @FXML
    private TableColumn<?, ?> clnactivite;
    @FXML
    private TableColumn<?, ?> clnlieu;
    @FXML
    private TableColumn<?, ?> clnCommentaire;
    private TableColumn<?, ?> clnEtat;
    @FXML
    private TableColumn<?, ?> clnnote;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
  try {
            OrganisationService os = new OrganisationService();
            List<Organisation> lo = os.afficherLesMeilleursOrganisation();
            ObservableList<Organisation> data = FXCollections.observableArrayList(lo);
           

            clnbpersonne.setCellValueFactory(new PropertyValueFactory<>("nbrpersonne"));
         
           
            clnnbJours.setCellValueFactory(new PropertyValueFactory<>("nbrjours"));
            clndate.setCellValueFactory(new PropertyValueFactory<>("date"));
            clnactivite.setCellValueFactory(new PropertyValueFactory<>("activite"));
            clnlieu.setCellValueFactory(new PropertyValueFactory<>("Lieu"));
            clnnote.setCellValueFactory(new PropertyValueFactory<>("note"));
            tableview.setItems(data);

           
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    
 
    
    }}