/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui.MaisonDHote;

import Tfarhida.base.MaConnexion;
import Tfarhida.entities.Chambre;
import Tfarhida.entities.Maison;
import Tfarhida.entities.Reservation;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author User
 */
public class AfficherReservationController implements Initializable {

    @FXML
    private TableView<Reservation> tabListReservation;
    @FXML
    private TableColumn<Reservation, Date> idReserv;
  //  private TableColumn<Reservation, String> NomCh;
    @FXML
    private TableColumn<Reservation, Double> prixReserv;
    @FXML
    private TableColumn<Reservation, Button> etatsReserv;
    
    ObservableList<Reservation> data = FXCollections.observableArrayList();
    @FXML
    private Button refreshTabReserv;

    String sql = null;
    Connection cnx = null;
    PreparedStatement ste = null;
    ResultSet rs = null;
    Reservation reservation = null;
    @FXML
    private TableColumn<Reservation, Date> dateReservCh;
    @FXML
    private Button suppReserv;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDate();
    }    
 
    private void loadDate() {
        try {
            cnx = MaConnexion.getinstance().getCnx();
            RefreshTabReservation();
            
            idReserv.setCellValueFactory(new PropertyValueFactory<>("dateArrivee"));
            dateReservCh.setCellValueFactory(new PropertyValueFactory<>("dateDepart"));
            //Date myDate = dateRserv.setCellValueFactory(new PropertyValueFactory<>("date_reservation"));
            
            prixReserv.setCellValueFactory(new PropertyValueFactory<>("totalPrix"));
            etatsReserv.setCellValueFactory(new PropertyValueFactory<>("etats"));
            
            tabListReservation.setItems(data);
            
            
        } catch (ClassNotFoundException ex) {
            System.out.print(ex.getMessage());
        }
    }

    @FXML
    private void RefreshTabReservation() {
        try {
            data.clear();
            sql = "select * from reservation_maison";
            ste = cnx.prepareStatement(sql);
             rs = ste.executeQuery();
            while(rs.next()){
                data.add(new Reservation(rs.getInt("id"),rs.getInt("chambre_id"), rs.getInt("user_id"), rs.getDate("dateArrivee"), rs.getString("etats"), rs.getDate("dateDepart"), rs.getDouble("totalPrix")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void SupprimerReservation(ActionEvent event) throws SQLException {
        try {
            reservation = tabListReservation.getSelectionModel().getSelectedItem();
            sql = "DELETE FROM reservation_maison WHERE id ="+reservation.getId();
            cnx = MaConnexion.getinstance().getCnx();
            ste = cnx.prepareStatement(sql);
            ste.execute();
            JOptionPane.showConfirmDialog(null, "Supprimer");
            RefreshTabReservation();
            
        } catch (ClassNotFoundException ex) {
            System.out.print(ex.getMessage());
        }
    }
    
    
    
}
