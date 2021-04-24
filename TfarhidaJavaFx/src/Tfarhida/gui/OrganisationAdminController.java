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
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        FXMLLoader l = new FXMLLoader(getClass().getResource("OrganisationAdmin.fxml"));
        Parent root = l.load();
        tableview.getScene().setRoot(root);
    }

    @FXML
    private void refuser(ActionEvent event) throws SQLException, IOException {
        OrganisationService os = new OrganisationService();
        os.RefuserAdmin(OrganisationAdminController.idOrganisation);
        FXMLLoader l = new FXMLLoader(getClass().getResource("/Tfarhida/gui/Menu/Accueil.fxml"));

        //   FXMLLoader l=new FXMLLoader(getClass().getResource("OrganisationAdmin.fxml"));
        Parent root = l.load();
        tableview.getScene().setRoot(root);
    }
}
