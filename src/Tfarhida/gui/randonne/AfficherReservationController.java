/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui.randonne;

import Tfarhida.base.MaConnexion;
import Tfarhida.entities.randonne.Randonnee;
import Tfarhida.entities.randonne.Reservation;
import Tfarhida.services.randonne.RandonneService;
import Tfarhida.services.randonne.ReservationService;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class AfficherReservationController implements Initializable {
    
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
    Reservation reservation = null;
    @FXML
    private Button suppReservation;
    @FXML
    private Button ImprimerReservation;
    @FXML
    private Menu voirChM;
    @FXML
    private MenuItem ListeRandonne;
    @FXML
    private MenuItem listeresrrvationbtn;

    public static List<Reservation> mylist=new ArrayList<>();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            showReservation();
        } catch (SQLException ex) {
            Logger.getLogger(AfficherReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
   
    
    // private void RefreshTabReservation() throws ClassNotFoundException {
          // Randonnee rand=null;
         // int id=DetailRandonneController.id;
         // RandonneService randonneService=new RandonneService();
           //  ReservationService reservationService=new ReservationService();
         //try {
           
         //    sql = "select * from reservation";
          //   ste = cnx.prepareStatement(sql);
          //    rs = ste.executeQuery();
         //    while(rs.next()){
          //     Reservation r=new Reservation(Integer.parseInt(colnumres.getText()),coldateres.getText(),colobs.getText(),Double.parseDouble(colmontant.getText()),rand,Integer.parseInt(colnbpersonne.getText()),coletat.getText());
          //   }
       //  } catch (SQLException ex) {
         //    System.out.println(ex.getMessage());
        // }
   //  }
    
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
                mylist.add(reservation);
                System.out.println(mylist);
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
    private void DeleteReservation(ActionEvent event)  {
         reservation = tbreservation.getSelectionModel().getSelectedItem();
           ReservationService reservationService=null;
     try {
            reservationService=new ReservationService();
      reservationService.delete(reservation.getId());
             try {
                 showReservation();
             } catch (SQLException ex) {
                 Logger.getLogger(AfficherReservationController.class.getName()).log(Level.SEVERE, null, ex);
             }
              Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Reservation a été supprimer avec success");
            a.show(); 
      
       
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AfficherReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
       
    }

    @FXML
    private void ImprimerReservation(ActionEvent event) throws DocumentException {
               Document doc = new Document();
       
            
                try {
                    PdfWriter.getInstance(doc, new FileOutputStream("Liste reservation.pdf")); 
            doc.open();
            String format ="dd/mm/yy hh:mm";
            SimpleDateFormat formater = new SimpleDateFormat(format);
            com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance("C:\\Users\\PC\\Documents\\NetBeansProjects\\Tfarhida\\src\\images\\reservation.jpg");
            img.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
            doc.add(img);
            doc.add(new Paragraph("Demande de réservations"));
            
           
            
            doc.close();
            Desktop.getDesktop().open(new File("reservation.pdf"));
             } catch (FileNotFoundException ex) {
                    Logger.getLogger(AfficherReservationController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BadElementException ex) {
            Logger.getLogger(AfficherReservationController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AfficherReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
        }

    @FXML
    private void ListeRandonne(ActionEvent event) {
         try{
        Parent parent = FXMLLoader.load(getClass().getResource("AfficherRandonneClient.fxml"));
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
        Parent parent = FXMLLoader.load(getClass().getResource("AfficherReservation.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        //stage.initStyle(StageStyle.UTILITY);
        stage.show();
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        
    }
    

}
