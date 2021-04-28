/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui.MaisonDHote;

import Tfarhida.base.UserSession;
import Tfarhida.entities.Chambre;
import Tfarhida.entities.Maison;
import Tfarhida.entities.Reservation;
import Tfarhida.services.ReservationService;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author User
 */
public class AjouterReservationController implements Initializable {

    Chambre chambre;
    @FXML
    private DatePicker dateReservation;
    @FXML
    private Button BtnReservation;
    private TextField NbrJCh;
    @FXML
    private DatePicker DateDepart;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setReservation(Chambre chambre){
        this.chambre = chambre;
    }
    
   
   // ArrayList<Reservation>  data = (ArrayList<Reservation>) ps.afficherReservations();
    //ObservableList<Reservation> data = FXCollections.observableArrayList();

    @FXML
    private void AjouterReservation(ActionEvent event) throws ClassNotFoundException {
        
        Date dateInst = new Date();
        Instant instant = dateInst.toInstant();
        Date myDate = Date.from(instant);
        //LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        
        LocalDate ld = dateReservation.getValue();
        Calendar c =  Calendar.getInstance();
        c.set(ld.getYear(), ld.getMonthValue() - 1, ld.getDayOfMonth());
        Date date = (Date) c.getTime();
        java.sql.Date sDate = new java.sql.Date(date.getTime());
   
        LocalDate localD = DateDepart.getValue();
        Calendar cal =  Calendar.getInstance();
        cal.set(localD.getYear(), localD.getMonthValue() - 1, localD.getDayOfMonth());
        Date dateDepart = (Date) cal.getTime();
        java.sql.Date dateD = new java.sql.Date(dateDepart.getTime());
        
        int nbrJour = dateD.compareTo(sDate);
        int idCh = chambre.getId(); 
      
        double prixTotal = chambre.getPrix()*nbrJour;
        String etats = "En cours";
        int idUser = UserSession.getId();
        if(sDate.before(myDate) && sDate.after(dateD)){
            
          //  System.out.println("Choisir une date valide");
            JOptionPane.showMessageDialog(null,"Choisir une date valide");
        }else{
            ReservationService ps = new ReservationService();
            if(ps.testDate(sDate, idCh) == false){
             Reservation ch = new Reservation(idCh, idUser, sDate, etats, dateD, prixTotal );
             ps.ajouterReservation(ch);
            JOptionPane.showMessageDialog(null,"Votre demande de réservation a été envoyer");
            }else{
                new DateRefuser().start();
          
                //JOptionPane.showMessageDialog(null,"Choisir une autre date");
            }

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
    
    class DateRefuser extends Thread {

        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(EtatsReservationController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
               Image img = new Image("/Tfarhida/images/refuser.png");
               ImageView imageView = new ImageView(img);
                imageView.setFitWidth(40); 
                imageView.setFitHeight(40); 
                
               Notifications notificationBuilder = Notifications.create()
                .title("Demande de réservation")
                .text("Cette chambre déja réservée pour cette date !!")
                .graphic(imageView)
                .hideAfter(Duration.seconds(8))
                .position(Pos.CENTER_LEFT)
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
