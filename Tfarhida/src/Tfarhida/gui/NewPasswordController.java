/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui;

import Tfarhida.entities.User;
import Tfarhida.services.UserService;
import com.jfoenix.controls.JFXPasswordField;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class NewPasswordController implements Initializable {

    @FXML
    private JFXPasswordField NewPwPf;
    @FXML
    private JFXPasswordField ConfirmPwPf;
    @FXML
    private Button SaveButton;
    @FXML
    private Label PassMatchLabel;
    @FXML
    private Button returnLogin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
     public void Newpw(String Email) {
        UserService SU = new UserService();
        
        SaveButton.setOnAction((ActionEvent event)->{
            
        
        if (!NewPwPf.getText().equals(ConfirmPwPf.getText())) {
            PassMatchLabel.setText("les deux mots de passe sont differents");
        }
      else {
            SU.NewPw(Email, NewPwPf.getText());
            
        }
        
        
        
        });
        
        
        
    }

    @FXML
    private void returnLogin(ActionEvent event)  throws IOException  {
        
             returnLogin.getScene().getWindow().hide();

        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("LoginUser.fxml"));
       
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Welcome!");
        primaryStage.setScene(scene);
        primaryStage.show(); 
    }
    
}
