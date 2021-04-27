/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui.MaisonDHote;

import Tfarhida.base.MaConnexion;
import Tfarhida.entities.Commentaire;
import Tfarhida.entities.Maison;
import Tfarhida.entities.Reservation;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author User
 */
public class AfficherCommentController implements Initializable {

    Maison maison;
    @FXML
    private TableView<Commentaire> tabListReservation;
    @FXML
    private TableColumn<Commentaire, Integer> NomUserComment;
    @FXML
    private TableColumn<Commentaire, String> commentM;
    @FXML
    private TableColumn<Commentaire, Date> dateComment;
    
    ObservableList<Commentaire> data = FXCollections.observableArrayList();
    
    Commentaire comment;
    String sql = null;
    Connection cnx = null;
    PreparedStatement ste = null;
    ResultSet rs = null;
    Reservation reservation = null;
    @FXML
    private ImageView refreshComment;
    @FXML
    private ImageView suppComment;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setMaison(Maison maison){
        this.maison=maison;
        loadDate();
    }

    private void loadDate() {
        try {
            cnx = MaConnexion.getinstance().getCnx();
            RefreshTable();
            
            NomUserComment.setCellValueFactory(new PropertyValueFactory<>("user_id"));
            commentM.setCellValueFactory(new PropertyValueFactory<>("comment"));
            //Date myDate = dateRserv.setCellValueFactory(new PropertyValueFactory<>("date_reservation"));
            
            dateComment.setCellValueFactory(new PropertyValueFactory<>("date"));
            
            tabListReservation.setItems(data);
            
            
        } catch (ClassNotFoundException ex) {
            System.out.print(ex.getMessage());
        }
    }

    @FXML
    private void RefreshTable() {
        try {
            //data.clear();
            String sql = "select * from commentaire WHERE maison_id="+maison.getId();
            ste = cnx.prepareStatement(sql);
             rs = ste.executeQuery();
            while(rs.next()){
                data.add(new Commentaire(rs.getInt("id"),rs.getInt("maison_id"), rs.getInt("user_id"), rs.getString("comment"), rs.getDate("date")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void DeleteComment(MouseEvent event) {
         try {
            comment = tabListReservation.getSelectionModel().getSelectedItem();
            sql = "DELETE FROM commentaire WHERE id ="+comment.getId();
            ste = cnx.prepareStatement(sql);
            ste.executeUpdate();
            JOptionPane.showConfirmDialog(null, "Supprimer");
            //ste.close();
            //RefreshTable();
            
        } catch (SQLException ex) {
            Logger.getLogger(AfficherChambreController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
    

