/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui.randonne;
import Tfarhida.base.MaConnexion;
import Tfarhida.entities.randonne.Randonnee;
import static Tfarhida.gui.randonne.DetailRandonneController.id;
import Tfarhida.services.randonne.RandonneService;
import Tfarhida.services.ReservationService;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * FXML Controller class
 *
 * @author PC
 */  




    
public class AfficherRandonneController implements Initializable {
    
  
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
    private FontAwesomeIconView randload;
    @FXML
    private AnchorPane voirPlusRand;
    @FXML
    private Menu voirChM;
    @FXML
    private TextField txchercher;
    @FXML
    private MenuItem ListeRandonne;
    @FXML
    private MenuItem AjoutRandonnebtn;
    @FXML
    private MenuItem listeresrrvationbtn;
    
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
     
     
       
       
   

    private void AjoutRandonne(MouseEvent event) {
        
        try{
        Parent parent = FXMLLoader.load(getClass().getResource("AjouterRandonne.fxml"));
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
    private void voirDetailsRandonne(MouseEvent event) {
        randonne = tableRand.getSelectionModel().getSelectedItem();
        
       if(randonne != null){
            try{
              FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailRandonne.fxml"));
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

   

    private void AjoutRandonne(ActionEvent event) {
          try{
        Parent parent = FXMLLoader.load(getClass().getResource("AjouterRandonne.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        //stage.initStyle(StageStyle.UTILITY);
        stage.show();
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void ModifierRandonne(ActionEvent event) {
         try{
        Parent parent = FXMLLoader.load(getClass().getResource("DetailRandonne.fxml"));
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
    private void ListeRandonne(ActionEvent event) {
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

    private void UpdateRandonne(ActionEvent event) {
        try {
           RandonneService randonneService=new RandonneService();
      FileChooser fc = new FileChooser();
       fc.setInitialDirectory(new File("C:\\Users\\PC\\Desktop\\copie java\\14\\TfarhidaJavaFx\\src\\images"));
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
        Path dest = Paths.get("C:/Users/PC/Desktop/copie java/14/TfarhidaJavaFx/src/images/img/"+namePhoto);
          try {
              Files.copy(src,dest);
              //System.out.println(str.substring(startIndex+1, endIndex));
          } catch (IOException ex) {
              Logger.getLogger(AjouterRandonneController.class.getName()).log(Level.SEVERE, null, ex);
          }
       
            
          Randonnee r=new Randonnee(id,Text_villedepart.getText(), Text_villearrivee.getText(),Datepicker_depart.getValue().toString(), Datepicker_retour.getValue().toString(),Text_activite.getText(), Text_description.getText(), name_File,Integer.parseInt(Text_duree.getText()), Text_difficulte.getText(), Integer.parseInt(Text_budget.getText()));
   randonneService.update(r);
          
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(DetailRandonneController.class.getName()).log(Level.SEVERE, null, ex);
        
              Alert a = new Alert(Alert.AlertType.INFORMATION);
           a.setContentText("Randonnee a été modifier avec success");
          a.show();      
          
       
         
    }

}

    @FXML
    private void ListeReservation(ActionEvent event) {
         try{
        Parent parent = FXMLLoader.load(getClass().getResource("EtatReservationRandonne.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        //stage.initStyle(StageStyle.UTILITY);
        stage.show();
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        
    }


    private void suivdetail(MouseEvent event) {
         try{
        Parent parent = FXMLLoader.load(getClass().getResource("DetailRandonne.fxml"));
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
    private void AddRandonnee(ActionEvent event) {
         try{
        Parent parent = FXMLLoader.load(getClass().getResource("AjouterRandonne.fxml"));
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









   

