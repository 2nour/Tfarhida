 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui;

import Tfarhida.base.MaConnexion;
import Tfarhida.entities.User;
import com.jfoenix.controls.JFXTextField;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class AfficheUserController implements Initializable {

    private JFXTextField txtAffiche;
    @FXML
    private Label ListeUser;
    @FXML
    private TableView<User> tvUser;
    private TableColumn<User, String> colID;
    @FXML
    private TableColumn<User, String> colNOM;
    @FXML
    private TableColumn<User, String> colEMAIL;
    @FXML
    private TableColumn<User, String> colROLE;
    
     
 
    String sql = null;
    Connection cnx = null;
    PreparedStatement ste = null;
    ResultSet rs = null;
    User user = null;
    @FXML
    
    private Button btnreturn;
    
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          
      
        try {
            showUser();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AfficheUserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

 

    public void setTxtAffiche(String txtAffiche) {
        this.txtAffiche.setText(txtAffiche);
    }
    
    public ObservableList<User> getUserList() throws ClassNotFoundException {
      ObservableList<User> UserList = FXCollections.observableArrayList();
      //cnx= MaConnexion.getinstance().getCnx();
     // Connection cnx = getMaConnexion();
      cnx = MaConnexion.getinstance().getCnx();
      String query = "SELECT * FROM user";
      Statement st;
      ResultSet rs;
      
      try{
          st = cnx.createStatement();
          rs = st.executeQuery(query);
          User User;
          while(rs.next()){
            User = new User(rs.getInt("id") ,rs.getString("email"), rs.getString("username"), rs.getString("password"), rs.getString("confirm_password"), rs.getString("roles"));
            UserList.add(User);
            
          }
      
      }catch(Exception ex){
        ex.printStackTrace();
      }
      return UserList;
    }
    public void showUser() throws ClassNotFoundException  {
        
      ObservableList<User> list = getUserList();
      
      //colID.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
      colNOM.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
      colEMAIL.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
      colROLE.setCellValueFactory(new PropertyValueFactory<User, String>("roles"));
      
      tvUser.setItems(list);
             
      
    }
    

    @FXML
  public void returnPage(ActionEvent event) throws IOException{
      
      btnreturn.getScene().getWindow().hide();

        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("UserPage.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Welcome!");
        primaryStage.setScene(scene);
        primaryStage.show();
}
   
       @FXML
    private void supp(User u) throws ClassNotFoundException, SQLException {
   
        try {
            user = tvUser.getSelectionModel().getSelectedItem();
            sql = "DELETE FROM maison WHERE email ="+user.getEmail();
            cnx = MaConnexion.getinstance().getCnx();
            ste = cnx.prepareStatement(sql);
            ste.execute();
            JOptionPane.showConfirmDialog(null, "Delete");
          
            
        } catch (SQLException ex) {
            System.out.print(ex.getMessage());
        }
    }
    
}
