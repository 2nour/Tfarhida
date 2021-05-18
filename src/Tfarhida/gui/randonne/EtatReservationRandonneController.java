/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui.randonne;

import Tfarhida.base.MaConnexion;
import Tfarhida.entities.randonne.Randonnee;
import Tfarhida.entities.randonne.Reservation;
import static Tfarhida.gui.randonne.AfficherRandonneClientController.randonne;
import static Tfarhida.gui.randonne.AfficherReservationController.list;
import Tfarhida.services.randonne.RandonneService;
import Tfarhida.services.randonne.ReservationService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.scene.layout.AnchorPane;



/**
 * FXML Controller class
 *
 * @author PC
 */
public class EtatReservationRandonneController implements Initializable {
    
    private TableView<Reservation> tabListReservation;
    private TableColumn<Reservation, String> dateReser;
    private TableColumn<Reservation, Double> Montant;
    private TableColumn<Reservation, String> etatsReserv;
    
    private TableColumn<Reservation, String> Observation;
    
    

    
    
   

      public static ObservableList<Reservation> list;
    
     @FXML
    private TableView<Reservation> tbreservation;
    @FXML
    private TableColumn<Reservation, Integer> colnumres;
    @FXML
    private TableColumn<Reservation, String> coldateres;
    @FXML
    private TableColumn<Reservation, String> colobs;
    @FXML
    private TableColumn<Reservation, Double> colmontant;
    @FXML
    private TableColumn<Reservation, Integer> colnbpersonne;
    @FXML
    private TableColumn<Reservation, String> coletat;
    
    @FXML
    private AnchorPane anchropane;
    
    
     ObservableList<Reservation> data = FXCollections.observableArrayList();
     
      String sql = null;
    Connection cnx = null;
    PreparedStatement ste = null;
    ResultSet rs = null;
   public static Reservation reservation;
    @FXML
    private Button accepterReserv;
    @FXML
    private Button refuserReserv;
    @FXML
    private Menu voirChM;
    @FXML
    private MenuItem ListeRandonne;
    @FXML
    private MenuItem AjoutRandonne;
    @FXML
    private MenuItem listecommentrand;
    @FXML
    private Button excelbuttonn;
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        try {
            showReservation();
             
       

        } catch (SQLException ex) {
            Logger.getLogger(EtatReservationRandonneController.class.getName()).log(Level.SEVERE, null, ex);
        }
          
       
           
    }    
    
    public Connection getConnection() throws SQLException{
       Connection conn;
       try{
           conn= DriverManager.getConnection("jdbc:mysql://localhost:3308/tfarhida", "root","");
           return conn;
       }catch(Exception ex){
           System.out.println("Errors:" +ex.getMessage());
           return null;
           
       }
    }
    
     public ObservableList<Reservation> getReservationList() throws SQLException{
        ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM reservation";
        Statement st;
        ResultSet rs;
        
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
             RandonneService randonneService=null;
            Reservation reservation;
            randonneService=new RandonneService();
            while(rs.next()){
                    Randonnee   rand=randonneService.findById(rs.getInt("id_randonnee"));

                reservation = new Reservation(rs.getInt("id"),rs.getInt("numreservation"),rs.getString("datereservation"),rs.getString("observation"),rs.getFloat("montant"),rand,rs.getInt("nombrepersonne"), rs.getString("etat"));
                reservationList.add(reservation);
            }
        }catch(Exception ex){
        ex.printStackTrace();
    }
        return reservationList;
    
    }
    
    public void showReservation() throws SQLException{
         list = getReservationList();
       
        colnumres.setCellValueFactory(new PropertyValueFactory<Reservation,Integer>("numreservation"));
        coldateres.setCellValueFactory(new PropertyValueFactory<Reservation,String>("datereservation"));
        colobs.setCellValueFactory(new PropertyValueFactory<Reservation,String>("observation"));
        colmontant.setCellValueFactory(new PropertyValueFactory<Reservation,Double>("montant"));
        colnbpersonne.setCellValueFactory(new PropertyValueFactory<Reservation,Integer>("nombrepersonne"));
        coletat.setCellValueFactory(new PropertyValueFactory<Reservation,String>("etat"));
        
        tbreservation.setItems(list);
        
    }

   

    @FXML
    private void AccepterReservation(ActionEvent event) { 
                
        try {
          Reservation  re=tbreservation.getSelectionModel().getSelectedItem();

     re.setEtat("accepter");
        ReservationService reservationService;
            reservationService = new ReservationService();
            reservationService.update(re);
            System.out.println("aaaaaa tbdalt ahh");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EtatReservationRandonneController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }

    @FXML
    private void RefuserReservation(ActionEvent event) {

        ReservationService reservationService;
        try {
                     reservation = tbreservation.getSelectionModel().getSelectedItem();
         
        reservation.setEtat("refuser");
            reservationService = new ReservationService();
            reservationService.delete(reservation.getId());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EtatReservationRandonneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ListeRandonne(ActionEvent event) {
         try{
        Parent parent = FXMLLoader.load(getClass().getResource("AfficherRandonne.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        //stage.initStyle(StageStyle.UTILITY);
        stage.show();
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void AjoutRandonne(ActionEvent event) {
         try{
        Parent parent = FXMLLoader.load(getClass().getResource("AjouterRandonne.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        //stage.initStyle(StageStyle.UTILITY);
        stage.show();
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void ListeReservation(ActionEvent event) {
          try{
        Parent parent = FXMLLoader.load(getClass().getResource("AfficherRandonne.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        //stage.initStyle(StageStyle.UTILITY);
        stage.show();
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void test(MouseEvent event) {
        reservation=tbreservation.getSelectionModel().getSelectedItem();
        System.out.println(reservation);
    }

    
   
}
