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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> list = FXCollections.observableArrayList("Utilisateur","Hébérgeur");
        typeUser.setItems(list);
    } 
    
        public void setUser(User user){
        this.user=user;
    }

    @FXML
    private void addUser(ActionEvent event)  {
        
            String email= txtEmail.getText();
            String username= txtPrénom.getText();
            String password= txtPassword.getText();
            String confirm_password= txtConfirmPassword.getText();
            String roles= typeUser.getSelectionModel().getSelectedItem().toString();
           
       
            
            User u = new User();
            UserService ps = new UserService();
            ps.ajouterUser(u);
            try{
            Parent parent = FXMLLoader.load(getClass().getResource("activationCompte.fxml"));
              Scene scene = new Scene(parent);
              Stage stage = new Stage();
              stage.setScene(scene);
               //stage.initStyle(StageStyle.UTILITY);
              stage.show();
            }catch(IOException ex){
              System.out.println(ex.getMessage());
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
    }
    


