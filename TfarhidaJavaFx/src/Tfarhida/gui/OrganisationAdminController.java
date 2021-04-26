/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui;

import Tfarhida.entities.Organisation;
import static Tfarhida.gui.Mail.sendMail;
import Tfarhida.services.OrganisationService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.mail.MessagingException;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TextField;
/**
 * FXML Controller class
 *
 * @author 21651
 */
public class OrganisationAdminController implements Initializable {

    public static int idOrganisation;

    @FXML
    private TableView<Organisation> tableview;
    @FXML
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
    @FXML
    private TableColumn<?, ?> clnEtat;
    @FXML
    private Button Approuver;
    @FXML
    private Button Refuser;
    @FXML
    private TextField filtreField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

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
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void ligneselectionner(MouseEvent event) {
        Organisation o = tableview.getSelectionModel().getSelectedItem();

        OrganisationAdminController.idOrganisation = o.getId();

    }

    @FXML
    private void approuver(ActionEvent event) throws SQLException, IOException {
        OrganisationService os = new OrganisationService();
        os.ApprouverAdmin(OrganisationAdminController.idOrganisation);
       
        try {
            sendMail("sourour.chelbi@esprit.tn");
        } catch (MessagingException ex) {
            Logger.getLogger(OrganisationAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        FXMLLoader l = new FXMLLoader(getClass().getResource("OrganisationAdmin.fxml"));
        Parent root = l.load();
        tableview.getScene().setRoot(root);
    }

    @FXML
    private void refuser(ActionEvent event) throws SQLException, IOException {
        OrganisationService os = new OrganisationService();
        os.RefuserAdmin(OrganisationAdminController.idOrganisation);
         try {
            sendMail("sourour.chelbi@esprit.tn");
        } catch (MessagingException ex) {
            Logger.getLogger(OrganisationAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        FXMLLoader l = new FXMLLoader(getClass().getResource("/Tfarhida/gui/Menu/Accueil.fxml"));

        //   FXMLLoader l=new FXMLLoader(getClass().getResource("OrganisationAdmin.fxml"));
        Parent root = l.load();
        tableview.getScene().setRoot(root);
    }
}
