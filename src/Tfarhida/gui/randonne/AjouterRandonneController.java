/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui.randonne;

import Tfarhida.entities.randonne.Randonnee;
import Tfarhida.services.randonne.RandonneService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.scene.layout.AnchorPane;


/**
 * FXML Controller class
 *
 * @author PC
 */
public class AjouterRandonneController implements Initializable {
      Stage stage;

    @FXML
    private TextField Text_budget;
    @FXML
    private TextField Text_difficulte;
    @FXML
    private TextField Text_duree;
    @FXML
    private TextArea Text_description;
    @FXML
    private TextField Text_activite;
    @FXML
    private DatePicker Datepicker_retour;
    @FXML
    private DatePicker Datepicker_depart;
    @FXML
    private TextField Text_villearrivee;
    @FXML
    private TextField Text_villedepart;
    @FXML
    private Button AjouterRandonnee;
      private FileChooser fileChooser;
     private File file;
    public  static String name_File;
    static String Path_File;
     
    
    private TextField textimg;
    @FXML
    private ImageView image_Randonnee;
    @FXML
    private Button image;
    @FXML
    private TextField lab_url;
    
    @FXML
    private AnchorPane anchropane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
           
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
     
     
      
    @FXML
  public  void importerImage(ActionEvent event){
     
      
     FileChooser fc = new FileChooser();
       fc.setInitialDirectory(new File("C:\\Users\\NJR\\Downloads\\Tfarhida-TfarhidaDesktop2\\Tfarhida-TfarhidaDesktopFinal\\Tfarhida\\src\\Tfarhida\\images\\"));
       fc.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png","*.jpg" ));
     File f = fc.showOpenDialog(null);
     if(f != null){
         lab_url.setText(f.getAbsolutePath());
          lab_url.setText(f.getName());
         Image image = new Image(f.toURI().toString(),image_Randonnee.getFitWidth(),image_Randonnee.getFitHeight(),true,true);
      image_Randonnee.setImage(image);
       
      }
       Path_File=f.getAbsolutePath();
       name_File=f.getName();
        String path=Path_File;
        int startIndex = path.indexOf("");
        int endIndex = path.lastIndexOf("\\");
        String replacement = "";
        String toBeReplaced = path.substring(startIndex, endIndex+1);
        System.out.println(path.replace(toBeReplaced, replacement));
        String namePhoto=path.replace(toBeReplaced, replacement);
        Path src = Paths.get(path); 
        Path dest = Paths.get("C:\\Users\\NJR\\Downloads\\Tfarhida-TfarhidaDesktop2\\Tfarhida-TfarhidaDesktopFinal\\Tfarhida\\src\\Tfarhida\\images\\img\\"+namePhoto);
          try {
              Files.copy(src,dest);
              //System.out.println(str.substring(startIndex+1, endIndex));
          } catch (IOException ex) {
              Logger.getLogger(AjouterRandonneController.class.getName()).log(Level.SEVERE, null, ex);
          }
       
       
   
     
       
       
   }

    @FXML
    private void AddRandonnee(ActionEvent event) {
        RandonneService randonneeService=null;
        try {
            randonneeService=new RandonneService();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AjouterRandonneController.class.getName()).log(Level.SEVERE, null, ex);
        }
         if((Text_villedepart.getText().isEmpty())){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Villedepart field is empty ");
            a.setHeaderText("Invalid Value");
            a.show();
        }
         
          if((Text_villearrivee.getText().isEmpty())){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Villearrive field is empty ");
            a.setHeaderText("Invalid Value");
            a.show();
        }
          
           if((Datepicker_depart.getValue().toString().isEmpty())){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Date depart field is empty ");
            a.setHeaderText("Invalid Value");
            a.show();
        }
            if((Datepicker_retour.getValue().toString().isEmpty())){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Date retour field is empty ");
            a.setHeaderText("Invalid Value");
            a.show();
        }

             
               if((Text_activite.getText().isEmpty())){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Activite field is empty ");
            a.setHeaderText("Invalid Value");
            a.show();
        }
               if((Text_description.getText().isEmpty())){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Description field is empty ");
            a.setHeaderText("Invalid Value");
            a.show();
        }
            if((Text_duree.getText().isEmpty())){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Duree field is empty ");
            a.setHeaderText("Invalid Value");
            a.show();
        }
            
              if((Text_difficulte.getText().isEmpty())){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Difficulte field is empty ");
            a.setHeaderText("Invalid Value");
            a.show();
        }
              if((Text_budget.getText().isEmpty())){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Budget field is empty ");
            a.setHeaderText("Invalid Value");
            a.show();
        }
              
              
            
               
            Randonnee r=new Randonnee(Text_villedepart.getText(), Text_villearrivee.getText(),Datepicker_depart.getValue().toString(), Datepicker_retour.getValue().toString(),Text_activite.getText(), Text_description.getText(), name_File,Integer.parseInt(Text_duree.getText()), Text_difficulte.getText(), Integer.parseInt(Text_budget.getText()));
            randonneeService.insert(r);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Randonnee ajouter avec success");
            a.show();
            
             try{
              Parent parent = FXMLLoader.load(getClass().getResource("AfficherRandonne.fxml"));
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
    private void importerImage(MouseEvent event) {
          
     FileChooser fc = new FileChooser();
       fc.setInitialDirectory(new File("C:\\Users\\PC\\Documents\\Youcam"));
       fc.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png","*.jpg" ));
     File f = fc.showOpenDialog(null);
     if(f != null){
         lab_url.setText(f.getAbsolutePath());
          lab_url.setText(f.getName());
         Image image = new Image(f.toURI().toString(),image_Randonnee.getFitWidth(),image_Randonnee.getFitHeight(),true,true);
      image_Randonnee.setImage(image);
       
      }
       
        
    }
    
   
   

  
    
    
   
}
