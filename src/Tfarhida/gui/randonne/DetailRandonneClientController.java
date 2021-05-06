/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui.randonne;
import Tfarhida.entities.randonne.CommentaireR;
import Tfarhida.entities.randonne.Randonnee;
import Tfarhida.entities.randonne.User;
import static Tfarhida.gui.randonne.AfficherRandonneController.randonne;
import static Tfarhida.gui.randonne.AjouterRandonneController.Path_File;
import Tfarhida.services.randonne.CommentaireService;
import Tfarhida.services.randonne.RandonneService;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class DetailRandonneClientController implements Initializable {
// public static int id=AfficherRandonneController.randonne.getId();
    @FXML
    private MenuItem ListeRandonne;
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
    private TextField Text_difficulte;
    @FXML
    private TextField Text_budget;
    @FXML
    private TextArea Text_description;
    @FXML
    private JFXButton AnnulerReser;
    @FXML
    private JFXButton ReserverRandonnee;
    @FXML
    private TextField Text_duree;
    @FXML
    private TextArea commentRandonnee;
    @FXML
    private Button btnaddcommrand;
    @FXML
    private FontAwesomeIconView retour;
    @FXML
    private Menu voirChM;
    @FXML
    private MenuItem ListeCommentairebtn;
    @FXML
    private MenuItem listeresrrvationbtn;
    public static int id=AfficherRandonneClientController.randonne.getId();
    
    @FXML
    private TextField lab_url;
    @FXML
    private Button image;
    private FileChooser fileChooser;
     private File file;
      static String name_File;
    static String Path_File;
    @FXML
    private ImageView image_Randonnee;
     
  

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       Randonnee r=AfficherRandonneClientController.randonne;
        System.out.println(r);
        
        Text_duree.setText(Integer.toString(r.getDuree()));
        Text_activite.setText(r.getActivite());
        Text_villedepart.setText(r.getVilledepart());
        Text_villearrivee.setText(r.getVillearrivee());
        Datepicker_depart.setValue(LocalDate.parse(r.getDatedepart()));
        Datepicker_retour.setValue(LocalDate.parse(r.getDateretour()));
        Text_difficulte.setText(r.getDifficulte());
        Text_budget.setText(Integer.toString(r.getBudget()));
        Text_description.setText(r.getDescription());
        String img=r.getImage();
        System.out.println(img);
        Image image1 = new Image("/images/"+img); 
     image_Randonnee.setImage(image1);
       
        
    }    

    @FXML
    private void ListeRandonne(ActionEvent event) {
        try{
        Parent parent = FXMLLoader.load(getClass().getResource("AfficherRandonneClient.fxml"));
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
    private void AnnulerReservation(ActionEvent event) {
    }

    @FXML
    private void ReserverRandonnee(ActionEvent event) {
         try{
        Parent parent = FXMLLoader.load(getClass().getResource("AjouterReservation.fxml"));
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
    private void AjouterCommentRandonne(ActionEvent event) throws ClassNotFoundException {
        String comment = commentRandonnee.getText();
        int randonnee_id = AfficherRandonneClientController.randonne.getId();
            
        Date dateInst = new Date();
        Instant instant = dateInst.toInstant();
        Date myDate = Date.from(instant);
        
        java.sql.Date sDate = new java.sql.Date(myDate.getTime());
        User user=new User(1,"hana@gmail.com","hana1");
        CommentaireR m = new CommentaireR(1, "cc", "12-03-2016",AfficherRandonneClientController.randonne,user);
        CommentaireService ps = new CommentaireService();
        ps.insert(m);
        
        JOptionPane.showMessageDialog(null, "Votre commentaire a été ajoutée");
    }

    @FXML
    private void retourlisteRand(MouseEvent event) {
          try{
        Parent parent = FXMLLoader.load(getClass().getResource("AfficherRandonneClient.fxml"));
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
    private void ListeCommentaireAction(ActionEvent event) {
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

    @FXML
    private void importerImage(ActionEvent event) {
         try {
           RandonneService randonneService=new RandonneService();
      FileChooser fc = new FileChooser();
       fc.setInitialDirectory(new File("C:\\Users\\PC\\Desktop\\copie java\\14\\TfarhidaJavaFx\\src\\images"));
       fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png","*.jpg" ));
     File f = fc.showOpenDialog(null);
     if(f != null){
         lab_url.setText(f.getAbsolutePath());
          lab_url.setText(f.getName());
         Image image = new Image(f.toURI().toString(),image_Randonnee.getFitWidth(),image_Randonnee.getFitHeight(),true,true);
      image_Randonnee.setImage(image);
       
      }
    }
         catch(Exception e){
             
         }
    }

  
    
}
