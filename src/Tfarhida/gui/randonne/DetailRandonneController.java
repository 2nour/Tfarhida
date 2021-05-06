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
import Tfarhida.services.randonne.CommentaireService;

import Tfarhida.services.randonne.RandonneService;
import Tfarhida.services.randonne.ReservationService;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
public class DetailRandonneController implements Initializable {

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
    private TextField Text_duree;
  public static int id=AfficherRandonneController.randonne.getId();
    @FXML
    private Button modifrandonnee;
    @FXML
    private JFXButton supprimerRandonne;
    @FXML
    private MenuItem ListeRandonne;
    @FXML
    private MenuItem AjoutRandonne;
    @FXML
    private TextArea commentRandonnee;
    @FXML
    private Button btnaddcommrand;
    @FXML
    private Menu voirChM;
    @FXML
    private MenuItem listeRestbtn;
    @FXML
    private FontAwesomeIconView retour;
    private ImageView imagee;
    @FXML
    private TextField lab_url;
    @FXML
    private Button image;
    @FXML
    private ImageView image_Randonnee;
    
    private FileChooser fileChooser;
     private File file;
      static String name_File;
    static String Path_File;
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Randonnee r=AfficherRandonneController.randonne;
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
        Image image1 = new Image("/images/img/"+img); 
        image_Randonnee.setImage(image1);
       
        // TODO
    }    

    
    
    
      public void setRandonnee(Randonnee randonne){
       
    }
    

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

    @FXML
    public void UpdateRandonne(ActionEvent event) {
               
        
    try {
           RandonneService randonneService=new RandonneService();
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
              
              
          Randonnee r=new Randonnee(id,Text_villedepart.getText(), Text_villearrivee.getText(),Datepicker_depart.getValue().toString(), Datepicker_retour.getValue().toString(),Text_activite.getText(), Text_description.getText(), "aaa",Integer.parseInt(Text_duree.getText()), Text_difficulte.getText(), Integer.parseInt(Text_budget.getText()));
   randonneService.update(r);
          
            
              Alert a = new Alert(Alert.AlertType.INFORMATION);
           a.setContentText("Randonnee a été modifier avec success");
          a.show();
          
       } catch (ClassNotFoundException ex) {
            Logger.getLogger(DetailRandonneController.class.getName()).log(Level.SEVERE, null, ex);
        
         
    }     
    }
    @FXML
    public  void importerImage(ActionEvent event){
        FileChooser fc = new FileChooser();
      
       fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png","*.jpg" ));
       File f = fc.showOpenDialog(null);
       if(f != null){
         String img="C:\\Users\\Ibrahim\\Desktop\\img\\";
          Image image = new Image(f.toURI().toString(),imagee.getFitWidth(),imagee.getFitHeight(),true,true);
      imagee.setImage(image);
       
       }
       
       
   }

    @FXML
    private void DeleteRandonne(ActionEvent event) {
        ReservationService reservationService=null;
        RandonneService randonneService=null;
     try {
         randonneService=new RandonneService();
            reservationService=new ReservationService();
      randonneService.delete(id);
      
      Text_activite.setText("");
      Text_villedepart.setText("");
      Text_description.setText("");
      Text_budget.setText("");
      Text_difficulte.setText("");
      Text_villedepart.setText("");
      Text_villearrivee.setText("");
      Datepicker_depart.setValue(null);
      Datepicker_retour.setValue(null);
      Text_duree.setText("");
      
     
        
              Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Randonnee a été supprimer avec success");
            a.show();  
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AfficherRandonneController.class.getName()).log(Level.SEVERE, null, ex);
        
    
          
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

    @FXML
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

    
    
    @FXML
    private void AjouterCommentRandonne(ActionEvent event) throws ClassNotFoundException {
        String comment = commentRandonnee.getText();
        int randonnee_id = randonne.getId();
            
        Date dateInst = new Date();
        Instant instant = dateInst.toInstant();
        Date myDate = Date.from(instant);
        
        java.sql.Date sDate = new java.sql.Date(myDate.getTime());
        User user=new User(1,"hana@gmail.com","hana1");
        CommentaireR m = new CommentaireR(1, "cc", "12-03-2016",randonne,user);
        CommentaireService ps = new CommentaireService();
        ps.insert(m);
        
        JOptionPane.showMessageDialog(null, "Votre commentaire a été ajoutée avec succés");
    }

    private void ListeCommentaire(ActionEvent event) {
         try{
              FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherCommentRandonne.fxml"));
              Parent parent = loader.load();
              Scene scene = new Scene(parent);
              Stage stage = new Stage();
              stage.setScene(scene);
              AfficherCommentRandonneController controler = loader.getController();
                  
              stage.show();
            }catch(IOException ex){
              System.out.println(ex.getMessage());
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

    @FXML
    private void retourlisteRand(MouseEvent event) {
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
}
    