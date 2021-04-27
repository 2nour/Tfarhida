/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui;

import Tfarhida.entities.User;
import Tfarhida.services.ServiceMail;
import Tfarhida.services.UserService;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class ForgettenPasswordController implements Initializable {

    @FXML
    private JFXTextField EmailLabel;
    @FXML
    private JFXTextField CodeLabel;
    @FXML
    private Button EnvoiMailButton;
    @FXML
    private Button VerifButton;
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       ForgotPw(); 
    } 
    
     private void ForgotPw() {
        
        UserService SU = new UserService();
        ServiceMail SM = new ServiceMail();
        
        EnvoiMailButton.setOnAction((ActionEvent e)-> {
            
            //verifier si l'email existe 
            if (SU.ValidateEmail(EmailLabel.getText())== true) {
                
                //  ErreurLabel.setText("Email n'existe pas ");
                System.out.print("Email n'existe pas ");
           ForgotPw();
            }
            else 
            {
        User u = SU.CodeConf(EmailLabel.getText());
       int Code = u.getCode();
       
       String User = u.getUsername();
        String Msg = "Bienvenue Mr/Mme "+User+" , votre code de confirmation est  "+Code;
       
            SM.sendmailfunc(EmailLabel.getText(),Msg);
               
     //   });
        
            VerifButton.setOnAction((ActionEvent event)-> {
              
                int Codesaisi = Integer.parseInt(CodeLabel.getText());
              
                 
              
             boolean  verif = SU.VerifCode(EmailLabel.getText(), Codesaisi);
             
             
             if(verif==false ) {
                 
                 // ErreurLabel.setText("Code incorrect");
                 System.out.print("Code incorrect");
                  ForgotPw();
             }
             else {
                 
             
              
              
                  Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    
                    FXMLLoader loader = new FXMLLoader ();
                    loader.setLocation(getClass().getResource("newPassword.fxml"));
                    try {
                        loader.load();
                      
                    } catch (IOException ex) {
                        Logger.getLogger(ForgettenPasswordController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                NewPasswordController NPC = loader.getController();
                    NPC.Newpw(EmailLabel.getText());
                    
                    Parent parent = loader.getRoot();
                    stage.setScene(new Scene(parent));
                    stage.show();
             } 
             });
                    
           } 
    });
    
    
    }

    
    
}
