/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui;

import Tfarhida.base.MaConnexion;
import Tfarhida.entities.User;
import Tfarhida.services.UserService;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class GererUserController implements Initializable {

    @FXML
    private TableView<User> tvuser;

    ObservableList<User> data = FXCollections.observableArrayList();

    @FXML
    private TableColumn<User, String> colnom;
    @FXML
    private TableColumn<User, String> colemail;
    @FXML
    private TableColumn<User, String> colrole;
    @FXML
    private JFXTextField tfnom;
    @FXML
    private JFXTextField tfemail;
    @FXML
    private JFXPasswordField pfpassword;
    @FXML
    private ComboBox boxrole;
    @FXML
    private Button btninsert;
    @FXML
    private Button btnupdate;
    @FXML
    private Button btndelete;

    String sql = null;
    Connection cnx = null;
    PreparedStatement ste = null;
    ResultSet rs = null;
    User user = null;
    @FXML
    private JFXPasswordField confirmpasswd;
    @FXML
    private JFXTextField tid;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            
            ObservableList<String> list = FXCollections.observableArrayList("Utilisateur", "Hébérgeur");
            boxrole.setItems(list);
            
            showUser();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GererUserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    public ObservableList<User> getUserList() throws ClassNotFoundException {
        ObservableList<User> UserList = FXCollections.observableArrayList();
        //cnx= MaConnexion.getinstance().getCnx();
        // Connection cnx = getMaConnexion();
        cnx = MaConnexion.getinstance().getCnx();
        String query = "SELECT * FROM user";
        Statement st;
        ResultSet rs;

        try {
            st = cnx.createStatement();
            rs = st.executeQuery(query);
            User User;
            while (rs.next()) {
                User = new User(rs.getInt("id"), rs.getString("email"), rs.getString("username"), rs.getString("password"), rs.getString("confirm_password"), rs.getString("roles"));
                UserList.add(User);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return UserList;
    }

    public void showUser() throws ClassNotFoundException {

        ObservableList<User> list = getUserList();

        //colID.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
        colnom.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        colemail.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        colrole.setCellValueFactory(new PropertyValueFactory<User, String>("roles"));

        tvuser.setItems(list);

    }


    private void SelectRole(ActionEvent event) {

        String s = boxrole.getSelectionModel().getSelectedItem().toString();
    }

    @FXML
    private void Afficher_tab(MouseEvent event) {
        User u = tvuser.getSelectionModel().getSelectedItem();
        String s = String.valueOf(u.getId());
        tid.setText(s);
        tfnom.setText(u.getUsername());
        tfemail.setText(u.getEmail());
        boxrole.setValue(u.getRoles());
        pfpassword.setText(u.getPassword());
        confirmpasswd.setText(u.getConfirm_password());

    }

    @FXML
    private void AjouterUser(ActionEvent event) throws ClassNotFoundException {
        UserService cr = new UserService();
        User u = new User();
        u.setUsername(tfnom.getText());
        u.setEmail(tfemail.getText());
        u.setPassword(pfpassword.getText());
        u.setConfirm_password(confirmpasswd.getText());
        u.setRoles(boxrole.getSelectionModel().getSelectedItem().toString());
        cr.ajouterUser(u);
        showUser();

    }

    @FXML
    private void ModifierUser(ActionEvent event) throws ClassNotFoundException {
        UserService cr = new UserService();
        User u = new User();

        int IDUSER = Integer.parseInt(tid.getText());

        u.setId(IDUSER);
        u.setUsername(tfnom.getText());
        u.setEmail(tfemail.getText());
        u.setPassword(pfpassword.getText());
        u.setConfirm_password(confirmpasswd.getText());
        u.setRoles(boxrole.getSelectionModel().getSelectedItem().toString());
        cr.ModifierUser(u);
        showUser();

    }

    @FXML
    private void SupprimerButton(ActionEvent event) throws ClassNotFoundException {
        UserService cr = new UserService();
        User u = new User();
        int IDUSER = Integer.parseInt(tid.getText());
        u.setId(IDUSER);
        cr.SupprimerUser(u);
        showUser();
        
    }

}
