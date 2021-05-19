/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui;

import Tfarhida.base.MaConnexion;
import Tfarhida.base.UserSession;
import Tfarhida.entities.User;
import Tfarhida.services.UserService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class LoginUserController implements Initializable {

    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXButton btnCnnx;
    @FXML
    private JFXButton btnForget;
    @FXML
    private JFXButton btnAjout;
    @FXML
    private JFXTextField txtmail;
    @FXML
    private AnchorPane txtEmail;
    @FXML
    private Label Erreur;
    @FXML
    private Label loginMessageLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public LoginUserController() throws ClassNotFoundException  {
        
            Connection cnx = MaConnexion.getinstance().getCnx();
      
    }

//    Connection cnx = null;
//    PreparedStatement preparedStatement = null;
//    ResultSet resultSet = null;


    @FXML
    private void Forgetpasswd(ActionEvent event) throws IOException {
        
        btnForget.getScene().getWindow().hide();

        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("forgettenPassword.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Welcome!");
        primaryStage.setScene(scene);
        primaryStage.show(); 

    }

    @FXML
    private void addUtilisateur(ActionEvent event) throws IOException {

        btnAjout.getScene().getWindow().hide();

        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("ajouterUser.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Welcome!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void loginButtonOnAction(ActionEvent event) throws IOException {
        
        UserService su = new UserService();
        
        
        String user = txtmail.getText();
        String password = txtPassword.getText();
        
         if (user.length()==0 || password.length()==0) {
        System.out.println("Veuillez saisir vos informations");
    }
        else
        {
            boolean exist = su.ValidateLogin(user,password);
            if (exist==true) {
                loginMessageLabel.setText("Veuillez verifier les champs");
                
                // recursivité
              
                loginButtonOnAction(event);
            }
            else {
           
                // recuperer les infos du user
                
                 //user session info 
                 
                 User u = su.SessionDetails(user);
        
            
               UserSession US = UserSession.getInstance(u.getId(),u.getUsername(),u.getPassword(),u.getConfirm_password(),u.getRoles(),u.getEmail(),u.getAuth());
                System.out.println("bienvenue");
               
               
                // redirection vers la page d'accueil 
                if(u.getAuth()==0 || u.getAuth()==2)
                {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader ();
                loader.setLocation(getClass().getResource("/Tfarhida/gui/Menu/Accueil.fxml"));
                try {
                        loader.load();
                        
                        
                    } catch (IOException ex) {
                        Logger.getLogger(LoginUserController.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("failed to load");
                        System.out.println(ex);
                    }
                    Parent parent = loader.getRoot();
                    stage.setScene(new Scene(parent));
                    stage.show(); 
                    
}
             else{
                  
        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Dashbord.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Admin Dashobrd!");
        primaryStage.setScene(scene);
        primaryStage.show();           
        }
        
        
    }

}
    }}
