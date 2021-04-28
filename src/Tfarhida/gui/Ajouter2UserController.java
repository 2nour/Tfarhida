/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui;

import Tfarhida.entities.User;
import Tfarhida.services.UserService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class Ajouter2UserController implements Initializable {

    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPrénom;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtConfirmPassword;

    @FXML
    private Button btnAjout;
    @FXML
    private ComboBox typeUser;
    private User user;
    @FXML
    private Label MailLabel;
    @FXML
    private Label UserLabel;
    @FXML
    private Label PwLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> list = FXCollections.observableArrayList("[\"ROLE_USER\"]", "[\"ROLE_HEBERGEUR\"]");
        typeUser.setItems(list);
    }

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    private void addUser(ActionEvent event) throws IOException {

        UserService su = new UserService();
        // User u = new User();

        String email = txtEmail.getText();
        String username = txtPrénom.getText();
        String password = txtPassword.getText();
        String confirm_password = txtConfirmPassword.getText();
        String roles = typeUser.getSelectionModel().getSelectedItem().toString();
        
        if (txtPrénom.getText().length() == 0) {

            txtPrénom.setStyle("-fx-border-color: red ;  -fx-border-width: 2px ;");
            new animatefx.animation.Shake(txtPrénom).play();
        }

        if (txtEmail.getText().length() == 0) {

            txtEmail.setStyle("-fx-border-color: red ;  -fx-border-width: 2px ;");
            new animatefx.animation.Shake(txtEmail).play();
        }
        
        if (txtPassword.getText().length() == 0) {

            txtPassword.setStyle("-fx-border-color: red ;  -fx-border-width: 2px ;");
            new animatefx.animation.Shake(txtPassword).play();
        }
        

        Boolean MailConf = ControlEmail(email);

        int err = 0; //compteur d'erreur
        // controle de saisie sur les infos inscription

        if (username.length() == 0) {
            UserLabel.setText("Veuillez saisir un nom d'utilisateur");
            err++;
        } else {
            if (su.ValidateUsername(username) == false) {
                UserLabel.setText("Nom d'utilisateur existe");
                err++;
            }
        }

        //controle de mot de passe 
        if (password.length() < 6) {
            PwLabel.setText("Mot de passe trop court");
            err++;
        }
        

//        //Controle de confirm password
//        if (txtPassword.getText() != confirm_password) {
//            PwLabel1.setText("Retapez votre password");
//            err++;
//        }

        // controle de l'email 
        //Controle de saisie de l'email (avec @ etc )        
        if (MailConf == false) {
            MailLabel.setText("Veuillez verifier votre mail");
            err++;
        } else {

            //verifier si l'email existe deja 
            if (su.ValidateEmail(email) == false) {

                MailLabel.setText("Email existe deja");
                err++;
            }

        }

        if (err > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inscription");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez verifier les champs");

            alert.showAndWait();

        } else {

            int Auth = 1;
            if (roles == "[\"ROLE_USER\"]") {
                Auth = 0;
            }
            if (roles == "[\"ROLE_HEBERGEUR\"]") {
                Auth = 2;}
                User u = new User(email, username, password, confirm_password, roles, Auth);
                UserService ps = new UserService();
                ps.ajouterUser(u);
               
                    Parent parent = FXMLLoader.load(getClass().getResource("activationCompte.fxml"));
                    Scene scene = new Scene(parent);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    //stage.initStyle(StageStyle.UTILITY);
                    stage.show();

        }

    }

    //FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficheUser.fxml"));
    // Parent root = loader.load();
    //  AfficheUserController auc = loader.getController();
    //  auc.setTxtAffiche(ps.AfficherUser().toString());
    //  txtPrénom.getScene().setRoot(root);
    //   } catch (IOException ex) {
    //    System.out.println(ex.getMessage());
    @FXML
    private void SelectRole(ActionEvent event) {

        String s = typeUser.getSelectionModel().getSelectedItem().toString();
    }

    // control de saisi de l'email 
    public boolean ControlEmail(String mail) {
        String masque = "^[a-zA-Z]+[a-zA-Z0-9\\._-]*[a-zA-Z0-9]@[a-zA-Z]+"
                + "[a-zA-Z0-9\\._-]*[a-zA-Z0-9]+\\.[a-zA-Z]{2,4}$";
        Pattern pattern = Pattern.compile(masque);
        Matcher controler = pattern.matcher(mail);
        if (controler.matches()) {
            return true;
        } else {
            return false;
        }

    }

}
