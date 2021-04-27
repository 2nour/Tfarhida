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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

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
    @FXML
    private TextField NbrJCh;
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
   
        int idCh = chambre.getId(); 
        String nbr = NbrJCh.getText();
        int nbrJour= Integer.parseInt(nbr);
        double prixTotal = chambre.getPrix()*nbrJour;
        String etats = "En cours";
        int idUser = UserSession.getId();
        if(sDate.before(myDate)){
            
          //  System.out.println("Choisir une date valide");
            JOptionPane.showMessageDialog(null,"Choisir une date valide");
        }else{
            ReservationService ps = new ReservationService();
            if(ps.testDate(sDate) == false){
             Reservation ch = new Reservation(idCh, idUser, sDate, etats, nbrJour, prixTotal );
             ps.ajouterReservation(ch);
            JOptionPane.showMessageDialog(null,"Votre demande de réservation a été envoyer");
            }else{
                JOptionPane.showMessageDialog(null,"Choisir une autre date");
            }
            //ReservationService ps = new ReservationService();
           
            
            
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
    
}
