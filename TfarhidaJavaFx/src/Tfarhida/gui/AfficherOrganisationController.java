/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui;

import Tfarhida.entities.Organisation;
import Tfarhida.services.OrganisationService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
/**
 * FXML Controller class
 *
 * @author 21651
 */
public class AfficherOrganisationController implements Initializable {

    @FXML
    private TableView<Organisation> tableview;
    @FXML
    private TableColumn<Organisation, String> clnbpersonne;
    @FXML
    private TableColumn<Organisation, String> clnCommentaire;
    @FXML
    private TableColumn<Organisation, String> clnEtat;
    @FXML
    private TableColumn<Organisation, String> clnnbJours;
    @FXML
    private TableColumn<Organisation, String> clndate;
    @FXML
    private TableColumn<Organisation, String> clnactivite;
    @FXML
    private TableColumn<Organisation, String> clnlieu;
    @FXML
    private Button btnsupp;
    @FXML
    private Label label;
    @FXML
    private TableColumn<Organisation, ?> clnid;
    @FXML
    private Button btnModifier;
    @FXML
    private TextField txtnbrpersonne;
    @FXML
    private TextField txtnbrjours;
    @FXML
    private TextField txtcommentaire;
    @FXML
    private ComboBox selectLieu;
    @FXML
    private ComboBox selectActivite;
    @FXML
    private DatePicker txtdate;
    @FXML
    private Label labelnbp;
    @FXML
    private Label labelnbj;
    @FXML
    private Label labelactivite;
    @FXML
    private Label labellieu;
    @FXML
    private Label labelcommentaire;
    @FXML
    private TextField filtreField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
      
        try {
            //  try {
            // list des activites
            ObservableList<String> list = FXCollections.observableArrayList("Camping", "TourVelo", "CircuitSahara");
            selectActivite.setItems(list);
            //liste des lieux
            ObservableList<String> listlieu = FXCollections.observableArrayList("Tunis", "Beja", "Jendouba");
            
            selectLieu.setItems(listlieu);
            //
            
            
            OrganisationService os = new OrganisationService();
            List<Organisation> lo = os.afficherOrganisation();
            ObservableList<Organisation> data = FXCollections.observableArrayList(lo);
            clnid.setCellValueFactory(new PropertyValueFactory<>("id"));

            clnbpersonne.setCellValueFactory(new PropertyValueFactory<>("nbrpersonne"));
            clnCommentaire.setCellValueFactory(new PropertyValueFactory<>("commentaire"));
            clnEtat.setCellValueFactory(new PropertyValueFactory<>("etat"));
            clnnbJours.setCellValueFactory(new PropertyValueFactory<>("nbrjours"));
            clndate.setCellValueFactory(new PropertyValueFactory<>("date"));
            clnactivite.setCellValueFactory(new PropertyValueFactory<>("activite"));
            clnlieu.setCellValueFactory(new PropertyValueFactory<>("Lieu"));

            tableview.setItems(data);
            
            
            // } catch (SQLException ex) {
            //   System.out.println(ex.getMessage());
            //}
            
            FilteredList<Organisation> filteredData = new FilteredList<>(data, b -> true);
            
            // 2. Set the filter Predicate whenever the filter changes.
            filtreField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(pharm -> {
                    // If filter text is empty, display all persons.
                    
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    
                    // Compare first name and last name of every person with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();
                    
                    if (pharm.getActivite().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                        return true; // Filter matches first name.
                    } else if (pharm.getLieu().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true; // Filter matches last name.
                    }
                    else if (String.valueOf(pharm.getId()).indexOf(lowerCaseFilter)!=-1)
                        return true;
                    
                    else
                        return false; // Does not match.
                });
            });
            
         
            SortedList<Organisation> sortedData = new SortedList<>(filteredData);
            
        
            sortedData.comparatorProperty().bind(tableview.comparatorProperty());
         
            tableview.setItems(sortedData);
        } catch (SQLException ex) {  
            Logger.getLogger(AfficherOrganisationController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void ligneselectionner(MouseEvent event) {
        Organisation o = tableview.getSelectionModel().getSelectedItem();
        

        Integer id = o.getId();
        String ids = id.toString();
       // System.out.println(o);
        label.setText(ids);
      //  labelnbp.setText(o.getNbrpersonne());
        
        Integer nbj = o.getNbrjours();
        String nbjs = nbj.toString();
        txtnbrjours.setText(nbjs);
        txtnbrpersonne.setText(o.getNbrpersonne());
        txtcommentaire.setText(o.getCommentaire());
    }

    @FXML
    private void deleteorg(ActionEvent event) throws IOException {
        OrganisationService os = new OrganisationService();
        String ids = label.getText();
     //   System.out.println(ids);
        //int id ) clnid.getValue()
        int id = Integer.parseInt(ids);
      //  System.out.println(id);

        os.supprimerOrganisation(id);
        JOptionPane.showMessageDialog(null,"Votre demande de programme a été supprimée");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Tfarhida/gui/Menu/Accueil.fxml"));
        Parent root = loader.load();
        tableview.getScene().setRoot(root);

    }

    @FXML
    private void modifierOrganisation(ActionEvent event) throws IOException, ParseException {
      OrganisationService orgser;
       String ids = label.getText();
         
        int id = Integer.parseInt(ids);
       
        String nbrjours = txtnbrjours.getText();  
        int nb = Integer.parseInt(nbrjours);
        String nbrpersonne = labelnbp.getText();
        String commentaire = txtcommentaire.getText();
        String activite = (String) selectActivite.getValue();
        String lieu = (String) selectLieu.getValue();

        //Recuperation de la date ( conversion String to Date )
        String date = String.valueOf(txtdate.getValue());
        System.out.println("Date = : " + date);
        Date dd = new SimpleDateFormat("yyyy-MM-dd").parse(date);

        


        String nbrpersonnem =  txtnbrpersonne.getText(); 
        Organisation o = new Organisation(id,nb, nbrpersonnem, dd, activite, commentaire, lieu, "En attente");
        orgser = new OrganisationService();
        orgser.modifierOrganisation(o);
        JOptionPane.showMessageDialog(null,"Votre demande de programme a été modifiée");


        //Pour actualiser la page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Tfarhida/gui/Menu/Accueil.fxml"));
        Parent root = loader.load();
        tableview.getScene().setRoot(root);

    }

    @FXML
    private void selectlieu(ActionEvent event) {
    }

    @FXML
    private void selectactivite(ActionEvent event) {
    }

}
