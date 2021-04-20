/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui;

import Tfarhida.entities.Randonnee;
import static Tfarhida.gui.AfficheRandonneeController.list;
import Tfarhida.services.RandonneService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class AfficheRandonneeClientController implements Initializable {

     
     public static Randonnee fo;

    @FXML
    private TableView<Randonnee> tableRand;
    public static ObservableList<Randonnee> list;

  
    @FXML
    private TableColumn<Randonnee, Integer> rand_id;
    
    @FXML
    private TableColumn<Randonnee, String> rand_vdepart;
    @FXML
    private TableColumn<Randonnee, String> rand_varrive;

    @FXML
    private TableColumn<Randonnee, String> rand_dated;

    @FXML
    private TableColumn<Randonnee, String> rand_dater;
    
    @FXML
    private TableColumn<Randonnee, String> rand_desc;
    
    @FXML
    private TableColumn<Randonnee, String> rand_activite;
    
    @FXML
    private TableColumn<Randonnee, String> rand_diff1;
    
    public static Randonnee r;
    
    @FXML
    private TableColumn<Randonnee, Integer> rand_duree;

    @FXML
    private TableColumn<Randonnee, String> rand_diff;

    @FXML
    private TableColumn<Randonnee, Integer> rand_budget;
    
    @FXML
    private TextField Text_id;

    @FXML
    private TextField Text_villedepart;

    @FXML
    private TextField Text_villearrivee;

    @FXML
    private DatePicker Datepicker_depart;

    @FXML
    private DatePicker Datepicker_retour;

    @FXML
    private TextField Text_activite;

    @FXML
    private TextArea Text_description;

    @FXML
    private JFXButton image_R;

    @FXML
    private TextField Text_duree;

    @FXML
    private TextField Text_difficulte;

    @FXML
    private TextField Text_budget;
    
     @FXML
    private TextField txchercher;


    @FXML
    private Button DetailsRandonnee;

    @FXML
    private JFXTextField filterField;
     @FXML
    private AnchorPane anchropane;
    
    ObservableList<Randonnee> listR;
    ObservableList<Randonnee> dataList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         try {
            showRandonnee();
              FilteredList<Randonnee> filteredData = new FilteredList<>(list, b -> true);
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
				// Compare first name and last name of every person with filter text.

                if (s != -1) {
                    return true; // Filter matches first name.
                } else if (s1 != -1) {
                    return true; // Filter matches last name.
                } else if (s2 != -1) {
                    return true;
                } else {
                    return false; // Does not match.
                }
            });
        });
        SortedList<Randonnee> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableRand.comparatorProperty());
        tableRand.setItems(sortedData);
        
         } catch (SQLException ex) {
            Logger.getLogger(AfficheRandonneeController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
    
     public ObservableList<Randonnee> getRandonneeList() throws SQLException{
        ObservableList<Randonnee> randonneeList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM randonnee";
        Statement st;
        ResultSet rs;
        
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
             RandonneService randonneService=null;
            Randonnee randonnee;
            randonneService=new RandonneService();
            while(rs.next()){
                randonnee=new Randonnee(rs.getInt("id"),rs.getString("villedepart"), rs.getString("villearrivee"), rs.getString("datedepart"), rs.getString("dateretour"), rs.getString("activite"), rs.getString("description"), rs.getString("image"), rs.getInt("duree"), rs.getString("difficulte"), rs.getInt("budget"));
                
                randonneeList.add(randonnee);
            }
        }catch(Exception ex){
        ex.printStackTrace();
    }
        return randonneeList;
    
    }
     
      public void showRandonnee() throws SQLException{
         list = getRandonneeList();
 
        rand_id.setCellValueFactory(new PropertyValueFactory<>("id"));
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
        tableRand.setItems(list);
        
    }
      
      
      public  void DetailsRandonnee() {
        Randonnee r=tableRand.getSelectionModel().getSelectedItem();
        Text_id.setText(Integer.toString(r.getId()));
        Text_budget.setText(Integer.toString(r.getBudget()));
        Text_activite.setText(r.getActivite());
        Text_description.setText(r.getDescription());
        Text_difficulte.setText(r.getDifficulte());
        Text_duree.setText(Integer.toString(r.getDuree()));
        Text_villearrivee.setText(r.getVillearrivee());
        Text_villedepart.setText(r.getVilledepart());
        System.out.println(r.getDatedepart()+" "+r.getDateretour());
        Datepicker_depart.setValue(LocalDate.parse(r.getDatedepart()));
        Datepicker_retour.setValue(LocalDate.parse(r.getDateretour()));
        
    }
      
     
        
        
         @FXML
    void handleButtonAction(ActionEvent event) {

    }
        
        
        
      
      
      
      
      
      @FXML
    void handleButtonAction1(ActionEvent event){
         try {
             r=tableRand.getSelectionModel().getSelectedItem();
           AnchorPane root = FXMLLoader.load(getClass().getResource("DetailRandonneeClient.fxml"));
           anchropane.getChildren().setAll(root);
     } catch (IOException ex) {
            Logger.getLogger(DetailRandonneeClientController.class.getName()).log(Level.SEVERE, null, ex);
        }

     
    
    
    
    }
}
