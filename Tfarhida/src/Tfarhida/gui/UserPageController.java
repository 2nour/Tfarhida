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
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class UserPageController implements Initializable {

    @FXML
    private Button btnlogout;
    @FXML
    private Button btnAffiche;
    @FXML
    private Button btnModif;
    private Button btnDesac;
    @FXML
    private Label UsernameHOME;
    @FXML
    private Button AdminButton;
    @FXML
    private Button HébergeurButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        UsernameHOME.setText(String.valueOf(UserSession.getId()));
        if (UserSession.getAuth() == 0) {
            AdminButton.setVisible(false);
            HébergeurButton.setVisible(false);
        } else if (UserSession.getAuth() == 2){
            AdminButton.setVisible(false);
        }
  
    }

    @FXML
    private void AdminButtonOnAction(ActionEvent event) {

        // verifier si c'est un admin 
        UserService SU = new UserService();
        int UserAuth = UserSession.getAuth();

        if (UserAuth == 0) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Espace Admin");
            alert.setHeaderText("Acces Interdit");
            alert.setContentText("Desolé , cet espace est reservé aux admins");

            alert.showAndWait();
        } else {

            // direction interface admin 
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Dashbord.fxml"));
            try {
                loader.load();

            } catch (IOException ex) {
                Logger.getLogger(UserPageController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("failed to load");
                System.out.println(ex);
            }
            Parent parent = loader.getRoot();
            stage.setScene(new Scene(parent));
            stage.show();
        }
    }
    
     @FXML
    private void HébergeurButtonOnAction(ActionEvent event) {
    }


    @FXML
    private void logOut(ActionEvent event) throws IOException {

        btnlogout.getScene().getWindow().hide();
        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("LoginUser.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Tfarhida!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void Affiche(ActionEvent event) throws IOException {

        btnAffiche.getScene().getWindow().hide();

        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("AfficheUser.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Welcome!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void Modif(ActionEvent event) throws IOException {
        btnModif.getScene().getWindow().hide();

        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("reclamation/FirstPageRec.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Welcome!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

   
}
