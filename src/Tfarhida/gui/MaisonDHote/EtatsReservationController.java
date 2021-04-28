/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui.MaisonDHote;

import Tfarhida.base.MaConnexion;
import Tfarhida.entities.Reservation;
import com.teknikindustries.bulksms.SMS;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author User
 */
public class EtatsReservationController implements Initializable {

    @FXML
    private TableView<Reservation> tabListReservation;
    @FXML
    private TableColumn<Reservation, String> idUser;
    @FXML
    private TableColumn<Reservation, String> idCh;
    @FXML
    private TableColumn<Reservation, Date> dateReser;
    @FXML
    private TableColumn<Reservation, Date> nbrJourReserv;
    @FXML
    private TableColumn<Reservation, String> etatsReserv;
    @FXML
    private Button accepterReserv;
    @FXML
    private Button refuserReserv;
    
    String sql = null;
    Connection cnx = null;
    PreparedStatement ste = null;
    ResultSet rs = null;
    Reservation reservation = null;

    ObservableList<Reservation> data = FXCollections.observableArrayList();
    
    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            idUser.setCellValueFactory(new PropertyValueFactory<>("nom"));
            idCh.setCellValueFactory(new PropertyValueFactory<>("nomCh"));
            dateReser.setCellValueFactory(new PropertyValueFactory<>("dateArrivee"));   
            dateReser.setCellFactory(column -> {
            TableCell<Reservation, Date> cell = new TableCell<Reservation, Date>() {
            

            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if(empty || item == null) {
                    setText(null);
                }
                else {
                    setText(format.format(item));

                }
            }
        };

        return cell;
            
        });


            nbrJourReserv.setCellValueFactory(new PropertyValueFactory<>("dateDepart"));
            nbrJourReserv.setCellFactory(column -> {
            TableCell<Reservation, Date> cell = new TableCell<Reservation, Date>() {
            

            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if(empty || item == null) {
                    setText(null);
                }
                else {
                    setText(format.format(item));

                }
            }
        };

        return cell;
            
        });
            
            etatsReserv.setCellValueFactory(new PropertyValueFactory<>("etats"));
        loadDate();
    }    
    
    private void loadDate() {
        try {
            cnx = MaConnexion.getinstance().getCnx();
            RefreshTabReservation();
            
            tabListReservation.setItems(data);
            
            
        } catch (ClassNotFoundException ex) {
            System.out.print(ex.getMessage());
        }
    }

    private void RefreshTabReservation() {
        try {
            data.clear();
            sql = "select r.*, u.username, ch.nom from reservation_maison r "
                    + "INNER JOIN user u ON (u.id = r.user_id)"
                    + "INNER JOIN chambre ch ON (ch.id = r.chambre_id)";
 
            ste = cnx.prepareStatement(sql);
             rs = ste.executeQuery();
            while(rs.next()){
                data.add(new Reservation(
                        rs.getInt("id"),
                        rs.getInt("chambre_id"),
                        rs.getInt("user_id"), 
                        new Date(rs.getDate("dateArrivee").getTime()), 
                        rs.getString("etats"), 
                        new Date(rs.getDate("dateDepart").getTime()) ,
                        rs.getDouble("totalPrix"), 
                        rs.getString("username"),
                        rs.getString("nom")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
    }

    

    @FXML
    private void AccepterReservation(ActionEvent event) throws SQLException, ProtocolException, IOException {
        
        reservation = tabListReservation.getSelectionModel().getSelectedItem();
        int value1 = reservation.getId();
        int value2 = reservation.getChambre_id();
        int value3 = reservation.getUser_id();
        
        reservation.setEtats("Accepter");
        String value4 = reservation.getEtats();
        
        //int value5 = reservation.getNbrJour();
        double value6 = reservation.getTotalPrix();
            
        sql = "UPDATE reservation_maison SET id= '"+value1+"',chambre_id= '"+value2+"',user_id= '"+value3+"',etats= '"+value4+"',totalPrix= '"+value6+"' WHERE id='"+reservation.getId()+"' ";
        ste= cnx.prepareStatement(sql);
        ste.executeUpdate();
        //ste.close();
        JOptionPane.showMessageDialog(null, "Envoyer");
       
        new myDownloader().start();
        
        RefreshTabReservation();
    }

    @FXML
    private void RefuserReservation(ActionEvent event) throws SQLException {
        reservation = tabListReservation.getSelectionModel().getSelectedItem();
        int value1 = reservation.getId();
        int value2 = reservation.getChambre_id();
        int value3 = reservation.getUser_id();
        
        reservation.setEtats("Refuser");
        String value4 = reservation.getEtats();
        
        //int value5 = reservation.getNbrJour();
        double value6 = reservation.getTotalPrix();
            
        sql = "UPDATE reservation_maison SET id= '"+value1+"',chambre_id= '"+value2+"',user_id= '"+value3+"',etats= '"+value4+"',totalPrix= '"+value6+"' WHERE id='"+reservation.getId()+"' ";
        ste= cnx.prepareStatement(sql);
        ste.executeUpdate();
        //ste.close();
        JOptionPane.showMessageDialog(null, "Envoyer");
        
        new NotificationRefuser().start();
        
        
        RefreshTabReservation();
    }
    
    class myDownloader extends Thread {

        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(EtatsReservationController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
               Image img = new Image("/Tfarhida/images/ok.png");
               ImageView imageView = new ImageView(img);
                imageView.setFitWidth(40); 
                imageView.setFitHeight(40); 
        
               Notifications notificationBuilder = Notifications.create()
                .title("Demande de réservation")
                .text("Votre demande de réservation a été acceptée")
                .graphic(imageView)
                .hideAfter(Duration.seconds(8))
                .position(Pos.TOP_LEFT)
                .onAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Clicked on Notification");
            }
                });
           notificationBuilder.darkStyle();
           Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    notificationBuilder.show();
                }
           });
           
        }
        
        
    }
    
    class NotificationRefuser extends Thread {

        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(EtatsReservationController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
               Image img = new Image("/Tfarhida/images/refuser.png");
               ImageView imageView = new ImageView(img);
                imageView.setFitWidth(40); 
                imageView.setFitHeight(40); 
                
               Notifications notificationBuilder = Notifications.create()
                .title("Demande de réservation")
                .text("Votre demande de réservation a été refusée")
                .graphic(imageView)
                .hideAfter(Duration.seconds(8))
                .position(Pos.TOP_LEFT)
                .onAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Clicked on Notification");
            }
                });
           notificationBuilder.darkStyle();
           Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    notificationBuilder.show();
                }
           });
           
        }
        
        
    }
    
}
