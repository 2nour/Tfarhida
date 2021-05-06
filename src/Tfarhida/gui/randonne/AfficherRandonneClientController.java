/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui.randonne;

import Tfarhida.base.MaConnexion;
import Tfarhida.entities.randonne.Randonnee;
import Tfarhida.entities.Reservation;
import static Tfarhida.gui.randonne.AfficherRandonneController.randonne;
import Tfarhida.services.randonne.RandonneService;
import Tfarhida.services.ReservationService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class AfficherRandonneClientController implements Initializable {

   @FXML
    private TableView<Randonnee> tableRand;
    @FXML
    private TableColumn<Randonnee, String> rand_vdepart;
    @FXML
    private TableColumn<Randonnee, String> rand_varrive;
    @FXML
    private TableColumn<Randonnee, String> rand_dated;
    @FXML
    private TableColumn<Randonnee, String> rand_dater;
    @FXML
    private TableColumn<Randonnee, String> rand_activite;
    @FXML
    private TableColumn<Randonnee, String> rand_desc;
    @FXML
    private TableColumn<Randonnee, String> rand_diff1;
    @FXML
    private TableColumn<Randonnee, Integer> rand_duree;
    @FXML
    private TableColumn<Randonnee, String> rand_diff;
    @FXML
    private TableColumn<Randonnee, Integer> rand_budget;
    
    @FXML
    private AnchorPane anchropane;
    
    @FXML
    private ImageView image_Randonnee;
    
    
    String sql = null;
    Connection cnx = null;
    PreparedStatement ste = null;
    ResultSet rs = null;
    public static Randonnee randonne ;
    
    
     ObservableList<Randonnee> data = FXCollections.observableArrayList();
    private TextField Text_villedepart;
    private TextField Text_villearrivee;
    private DatePicker Datepicker_depart;
    private DatePicker Datepicker_retour;
    private TextField Text_activite;
    private TextField Text_difficulte;
    private TextField Text_budget;
    private TextArea Text_description;
    private TextField Text_duree;
     
   
    
    
    @FXML
    private Menu voirChM;
    @FXML
    private TextField txchercher;
    @FXML
    private MenuItem ListeRandonne;
    @FXML
    private FontAwesomeIconView detailrandonne;
    @FXML
    private FontAwesomeIconView suivant;
    @FXML
    private MenuItem listeresrrvationbtn;
    @FXML
    private MenuItem ModifierReservation;
    
    /**
     * Initializes the controller class.
     */
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDate();
        FilteredList<Randonnee> filteredData = new FilteredList<>(data, b -> true);
        // filterField.textProperty().addListener((observable,old));
        txchercher.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(f -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                int s = f.getVillearrivee().toLowerCase().indexOf(lowerCaseFilter);
                int s1 = f.getVilledepart().toLowerCase().indexOf(lowerCaseFilter);
                int s2 = f.getDifficulte().toLowerCase().indexOf(lowerCaseFilter);
                int s3 = f.getActivite().toLowerCase().indexOf(lowerCaseFilter);
                int s4 = f.getDescription().toLowerCase().indexOf(lowerCaseFilter);
                // Compare first name and last name of every person with filter text.

                if (s != -1) {
                    return true; // Filter matches first name.
                } else if (s1 != -1) {
                    return true; // Filter matches last name.
                } else if (s2 != -1) {
                    return true;
                     } else if (s3 != -1) {
                    return true;
                     } else if (s4 != -1) {
                    return true;
                } else {
                    return false; // Does not match.
                }
            });
        });
        SortedList<Randonnee> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableRand.comparatorProperty());
        tableRand.setItems(sortedData);
        tableRand.setOnMouseClicked((MouseEvent event) -> {
        if(event.getButton().equals(MouseButton.PRIMARY)){
           // System.out.println(tableRand.getSelectionModel().getSelectedItem());
           randonne=tableRand.getSelectionModel().getSelectedItem();
        }
    });
       
         
         
    }    
    
     public Connection getConnection() throws SQLException{
       Connection conn;
       try{
           conn= DriverManager.getConnection("jdbc:mysql://localhost:3308/tfarhida", "root","");
           return conn;
       }catch(Exception ex){
           System.out.println("Errors:" +ex.getMessage());
           return null;
           
       }
    }
    
    
     private void loadDate() {
        try {
            cnx = MaConnexion.getinstance().getCnx();
            RefreshTable();
            
      
        rand_vdepart.setCellValueFactory(new PropertyValueFactory<>("villedepart"));
        rand_varrive.setCellValueFactory(new PropertyValueFactory<>("villearrivee"));
        rand_dated.setCellValueFactory(new PropertyValueFactory<>("datedepart"));
        rand_dater.setCellValueFactory(new PropertyValueFactory<>("dateretour"));
        rand_activite.setCellValueFactory(new PropertyValueFactory<>("activite"));
        rand_desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        rand_diff1.setCellValueFactory(new PropertyValueFactory<>("image"));
        rand_duree.setCellValueFactory(new PropertyValueFactory<>("duree"));
        rand_diff.setCellValueFactory(new PropertyValueFactory<>("difficulte"));
        rand_budget.setCellValueFactory(new PropertyValueFactory<>("budget"));
        tableRand.setItems(data);  
        } catch (ClassNotFoundException ex) {
            System.out.print(ex.getMessage());
        }
       
     }
    private void RefreshTable() {
        try {
            data.clear();
            sql = "select * from randonnee";
            ste = cnx.prepareStatement(sql);
             rs = ste.executeQuery();
            while(rs.next()){
                data.add(new Randonnee(rs.getInt("id"),rs.getString("villedepart"), rs.getString("villearrivee"), rs.getString("datedepart"), rs.getString("dateretour"), rs.getString("activite"), rs.getString("description"), rs.getString("image"), rs.getInt("duree"), rs.getString("difficulte"), rs.getInt("budget")));
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    

    @FXML
    private void ListeRandonne(ActionEvent event) {
    }


    @FXML
    private void voirDetailsRandonne(MouseEvent event) {
         randonne = tableRand.getSelectionModel().getSelectedItem();
        
       if(randonne != null){
            try{
              FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailRandonneClient.fxml"));
              Parent parent = loader.load();
              Scene scene = new Scene(parent);
              Stage stage = new Stage();
              stage.setScene(scene);
            
              stage.show();
            }catch(IOException ex){
              System.out.println(ex.getMessage());
            }
       }
    

    }

    @FXML
    private void suivdetail(MouseEvent event) {
        try{
        Parent parent = FXMLLoader.load(getClass().getResource("DetailRandonneClient.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        //stage.initStyle(StageStyle.UTILITY);
        stage.show();
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }


    @FXML
    private void ListeReservation(ActionEvent event) {
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
