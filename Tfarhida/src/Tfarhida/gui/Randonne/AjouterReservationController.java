/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui.Randonne;

import Tfarhida.entities.Randonnee;
import Tfarhida.entities.Reservation2;
import Tfarhida.services.RandonneService;
import Tfarhida.services.ReservationService2;
import java.io.IOException;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class AjouterReservationController implements Initializable {

    @FXML
    private Button AjouterReservation;
    @FXML
    private TextField tfnumres;
    @FXML
    private DatePicker datepickeresr;
    @FXML
    private TextField tfmont;
    @FXML
    private TextField tfobs;
    @FXML
    private TextField tfnbperrsonne2;
    
    @FXML
    private AnchorPane anchropane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void AjouterReservation(ActionEvent event) {
        
         Randonnee rand=null;
                     ReservationService2 ReservationService2=null;

         try {
             ReservationService2 =new ReservationService2();
         RandonneService randonneService=new RandonneService();
         int id =DetailRandonneClientController.id;
            rand=randonneService.findById(id);
             } catch (ClassNotFoundException ex) {
            Logger.getLogger(AjouterReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         
          if((tfnumres.getText().isEmpty())){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Num Reservation field is empty ");
            a.setHeaderText("Invalid Value");
            a.show();
        }
          
           if((datepickeresr.getValue().toString().isEmpty())){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Date Reservation field is empty ");
            a.setHeaderText("Invalid Value");
            a.show();
        }
            
             
               if((tfobs.getText().isEmpty())){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Obseration field is empty ");
            a.setHeaderText("Invalid Value");
            a.show();
        }
               if((tfmont.getText().isEmpty())){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Montant field is empty ");
            a.setHeaderText("Invalid Value");
            a.show();
        }
            if((tfnbperrsonne2.getText().isEmpty())){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Duree field is empty ");
            a.setHeaderText("Invalid Value");
            a.show();
        }
            Date dateInst = new Date();
        Instant instant = dateInst.toInstant();
        Date myDate = Date.from(instant);
        //LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        
        LocalDate ld = datepickeresr.getValue();
        Calendar c =  Calendar.getInstance();
        c.set(ld.getYear(), ld.getMonthValue() - 1, ld.getDayOfMonth());
         Date date = (Date) c.getTime();
        java.sql.Date sDate = new java.sql.Date(date.getTime());
   
         if(sDate.before(myDate)){
          //  System.out.println("Choisir une date valide");
            JOptionPane.showMessageDialog(null,"Choisir une date valide");
        }else{
            
              
              
            Reservation2 r=new Reservation2(Integer.parseInt(tfnumres.getText()),datepickeresr.getValue().toString(),tfobs.getText(),Double.parseDouble(tfmont.getText()),rand,Integer.parseInt(tfnbperrsonne2.getText()),"en cours");
              ReservationService2.insert(r);
                 
    Notifications.create()
            .title("Nouvelle Reservation2")
            .text("Demande de Réservation a été bien ajouter avec succés!")
            .showWarning();
              send(r.getNumreservation());
              
                 Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Reservation2 ajouter avec success");
            a.show();
         
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
    private void send(int num) {
System.out.println("Preparing to send mail .......");
Properties props = new Properties();
props.put("mail.smtp.auth", "true");
props.put("mail.smtp.starttls.enable","true");
props.put("mail.smtp.host","smtp.gmail.com");
props.put("mail.smtp.port","587");
props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
 String username = "teesst.2021@gmail.com";
 String password = "Test_123";
Session session = Session.getInstance(props,new Authenticator() {
    @Override
protected PasswordAuthentication getPasswordAuthentication() {
return new PasswordAuthentication(username, password);
}
});
 Message message = new MimeMessage(session);
    try {
        message.setFrom(new InternetAddress(username));
        message.setRecipient(Message.RecipientType.TO,new InternetAddress("hanajenhaani@gmail.com"));
        message.setSubject("Nouvelle Reservation2");
        message.setText("Bonjour, et bienvenue sur notre site ! Nous te remercions de ton réservation , Votre Reservation2 est bien enregistrer sous le num "+num);
       Transport.send(message);
       
       // FXMLRecuperPassController.email=null;

//System.out.println("Message_envoye");  
    } catch (Exception ex) {
  
    }
    
    }

    @FXML
    private void notifier(MouseEvent event) {

   
    }
  }
    
    
    
    
    
        
        
        
        
        
        
    
    
    

       
   
       
           
