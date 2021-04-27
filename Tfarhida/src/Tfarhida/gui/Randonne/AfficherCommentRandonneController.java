/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui.Randonne;

import Tfarhida.base.MaConnexion;
import Tfarhida.entities.CommentaireR;
import Tfarhida.entities.Randonnee;
import Tfarhida.entities.Reservation;
import Tfarhida.entities.User;
import Tfarhida.services.RandonneService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class AfficherCommentRandonneController implements Initializable {

    @FXML
    private TableColumn<CommentaireR, Integer> NomUserComment;
    @FXML
    private TableColumn<CommentaireR, String> commentM;
    @FXML
    private TableColumn<CommentaireR, String> dateComment;
 
    @FXML
    private TableView<CommentaireR> tabListCommentaire;
    
    ObservableList<CommentaireR> data = FXCollections.observableArrayList();
    
    Randonnee randonne;
    CommentaireR comment;
    String sql = null;
    Connection cnx = null;
    PreparedStatement ste = null;
    ResultSet rs = null;
    Reservation reservation = null;
    @FXML

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
            RefreshTable();
            
            NomUserComment.setCellValueFactory(new PropertyValueFactory<>("user_id"));
            commentM.setCellValueFactory(new PropertyValueFactory<>("comment"));
            //Date myDate = dateRserv.setCellValueFactory(new PropertyValueFactory<>("date_reservation"));
            
            dateComment.setCellValueFactory(new PropertyValueFactory<>("date"));
            
            tabListCommentaire.setItems(data);
            
            
        } catch (ClassNotFoundException ex) {
            System.out.print(ex.getMessage());
        }
    }

      @FXML
    private void RefreshTable() {
        try {
            //data.clear();
            String sql = "select * from commentaire WHERE randonne_id="+randonne.getId();
            ste = cnx.prepareStatement(sql);
             rs = ste.executeQuery();
             RandonneService randonneService=new RandonneService();
             User user=new User(1,"hana@gmail.com","hana1");
             
            while(rs.next()){
                Randonnee t=randonneService.findById(rs.getInt("randonne_id"));
                data.add(new CommentaireR(rs.getInt("id"), rs.getString("comment"), rs.getString("date"),t, user));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AfficherCommentRandonneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

 //   @FXML
  //  private void DeleteComment(MouseEvent event) {
        //try {
           // comment = tabListCommentaire.getSelectionModel().getSelectedItem();
           // sql = "DELETE FROM commentaire WHERE id ="+comment.setU("u");
           // ste = cnx.prepareStatement(sql);
          //  ste.executeUpdate();
           // JOptionPane.showConfirmDialog(null, "Commentaire a été supprimer avec succés");
            //ste.close();
            //RefreshTable();
            
       // } catch (SQLException ex) {
         //   Logger.getLogger(AfficherRandonneController.class.getName()).log(Level.SEVERE, null, ex);
      //  }
   // }
//}
    
   
