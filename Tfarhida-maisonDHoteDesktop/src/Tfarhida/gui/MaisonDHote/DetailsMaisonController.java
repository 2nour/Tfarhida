/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui.MaisonDHote;

import Tfarhida.base.MaConnexion;
import Tfarhida.base.UserSession;
import Tfarhida.entities.Chambre;
import Tfarhida.entities.Commentaire;
import Tfarhida.entities.Maison;
import Tfarhida.entities.constants.constants;
import Tfarhida.services.CommentaireService;
import Tfarhida.services.MaisonService;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author User
 */
public class DetailsMaisonController implements Initializable {

    private Chambre chambre;
    
    private Maison maison=null;
    @FXML
    private Label nomMaisonD;
    @FXML
    private Label adresseMaisonD;
    @FXML
    private Label NbrChMaisonD;
    @FXML
    private Label TelMaisonD;
    @FXML
    private ImageView ImgMaisonD;
    @FXML
    private Label descMaisonD;
    @FXML
    private ImageView suppM;

    String sql = null;
    Connection cnx = null;
    PreparedStatement ste = null;
    ResultSet rs = null;
    
    File file;
    
    @FXML
    private MenuItem updateM;
    @FXML
    private Menu voirChM;
    @FXML
    private MenuItem VoirChMenuM;
    @FXML
    private GridPane detailsPaneM;
    private TableView<Chambre> ReservationChambre;
    private TableColumn<Chambre, Integer> PourCh;
    private TableColumn<Chambre, String> TypeChM;
    private TableColumn<Chambre, Double> tarifCh;
    private TableColumn <Chambre, Boolean> reserverChMD;
  
    ObservableList<Chambre> data = FXCollections.observableArrayList();
    @FXML
    private TextArea commentMaison;
    @FXML
    private Button btnAddCommentM;
    @FXML
    private MenuItem VoirCommentMenuM2;
    @FXML
    private Label pathMaison;
    @FXML
    private MenuItem AddCh;
    @FXML
    private Rating rating;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(UserSession.getAuth() == 2){
            updateM.setVisible(true);
            AddCh.setVisible(true);
        }else{
            updateM.setVisible(false);
            AddCh.setVisible(false);
        }
        
             try {
	      FXMLLoader loader = new FXMLLoader(getClass().getResource("Rating.fxml"));
              Parent parent = loader.load();
              Scene scene = new Scene(parent);
              Stage stage = new Stage();
              stage.setScene(scene);;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }    
    
    private void loadDate() {
        try {
            cnx = MaConnexion.getinstance().getCnx();
            RefreshTable();
            
            PourCh.setCellValueFactory(new PropertyValueFactory<>("nbr_pers"));
            TypeChM.setCellValueFactory(new PropertyValueFactory<>("type_lit"));
            tarifCh.setCellValueFactory(new PropertyValueFactory<>("prix"));
            reserverChMD.setCellValueFactory( new PropertyValueFactory<>( "reserver" ));
            reserverChMD.setCellFactory( tc -> new CheckBoxTableCell<>());
            reserverChMD.setEditable(true);
            ReservationChambre.setEditable(true);
            

            //reserverChMD.getOnEditStart();
            ReservationChambre.setItems(data);
            
        } catch (ClassNotFoundException ex) {
            System.out.print(ex.getMessage());
        }
                  
    }
    
    private void reserverChambre(){
        JOptionPane.showMessageDialog(null, "Voila");
    }

    public void setMaison(Maison maison){
        this.maison=maison;
        showMaisonDetails(maison);
//        loadDate();
    }

    private void RefreshTable() {
         try {
            data.clear();
            sql = "select * from chambre WHERE maison_id="+maison.getId();
            ste = cnx.prepareStatement(sql);
             rs = ste.executeQuery();
            while(rs.next()){
                data.add(new Chambre(rs.getInt("id"), rs.getInt("maison_id"), rs.getString("nom"), rs.getString("type_lit"), rs.getInt("nbr_pers"), rs.getDouble("prix"), rs.getString("photo") ));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    
    
   private void showMaisonDetails(Maison maison) {
    if (maison != null) {
        System.out.println(maison.getAdresse());
        // Fill the labels with info from the person object.
        nomMaisonD.setText(maison.getNom());
        adresseMaisonD.setText(maison.getAdresse());
        NbrChMaisonD.setText(Integer.toString(maison.getNbr_chambre()));
        TelMaisonD.setText(Integer.toString(maison.getTel()));
        try {
          file = new File(constants.getImagePath() + "maison\\" + maison.getPhoto());
       
            Image img = new Image(file.toURI().toURL().toExternalForm());
            ImageView i = new ImageView(img);

             ImgMaisonD.setImage(i.getImage());


        } catch (MalformedURLException ex) {
            Logger.getLogger(AfficherListeMaisonsController.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex);
        }   
       
        descMaisonD.setText(maison.getDescription());
        
        
        // TODO: We need a way to convert the birthday into a String!
        // birthdayLabel.setText(...);
    } else {
        // Person is null, remove all the text.
        nomMaisonD.setText("");
        adresseMaisonD.setText("");
        NbrChMaisonD.setText("");
        TelMaisonD.setText("");
        descMaisonD.setText("");
    }
}

    @FXML
    private void DeleteMaison(MouseEvent event) throws SQLException, IOException {
        try {
            sql = "DELETE FROM maison WHERE id ="+maison.getId();
            cnx = MaConnexion.getinstance().getCnx();
            ste = cnx.prepareStatement(sql);
            ste.executeUpdate();
            JOptionPane.showConfirmDialog(null, "Supprimer");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherMaison.fxml"));
              Parent parent = loader.load();
              Scene scene = new Scene(parent);
              Stage stage = new Stage();
              stage.setScene(scene);
            //RefreshTable();
            
        } catch (ClassNotFoundException ex) {
            System.out.print(ex.getMessage());
        }
    }

    @FXML
    private void ModifierMaison(ActionEvent event) {
       if(maison != null){
            try{
              FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierMaison.fxml"));
              Parent parent = loader.load();
              Scene scene = new Scene(parent);
              Stage stage = new Stage();
              stage.setScene(scene);
              ModifierMaisonController controler = loader.getController();
                      controler.setMaison(maison);
              stage.show();
            }catch(IOException ex){
              System.out.println(ex.getMessage());
            }
       }
    }

    @FXML
    private void VoirChambreM(ActionEvent event) {
         try{
              FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherChambre.fxml"));
              Parent parent = loader.load();
              Scene scene = new Scene(parent);
              Stage stage = new Stage();
              stage.setScene(scene);
              AfficherChambreController controler = loader.getController();
                    controler.setMaison(maison);
              stage.show();
            }catch(IOException ex){
              System.out.println(ex.getMessage());
            }
    }

    @FXML
    private void AjouterChM(ActionEvent event) {
    //    maison = maisontabel.getSelectionModel().getSelectedItem();
       if(maison != null){
            try{
              FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterChambre.fxml"));
              Parent parent = loader.load();
              Scene scene = new Scene(parent);
              Stage stage = new Stage();
              stage.setScene(scene);
              AjouterChambreController controler = loader.getController();
                      controler.setMaison(maison);
              stage.show();
            }catch(IOException ex){
              System.out.println(ex.getMessage());
            }
       }
    }

    private void RefreshTable(MouseEvent event) {
        showMaisonDetails(maison);
    }

    private void reserverChambres(ActionEvent event) {
        ReservationChambre.getItems()
                .filtered(ach -> ach.isReserver()== true)
                .forEach(
                chambre -> System.out.println(chambre.getNom()));
    }

    @FXML
    private void AjouterCommentMaison(ActionEvent event) throws ClassNotFoundException, IOException {
        String comment = commentMaison.getText();
        int maison_id = maison.getId();
        int idUser = UserSession.getId();
        Date dateInst = new Date();
        Instant instant = dateInst.toInstant();
        Date myDate = Date.from(instant);
        
        java.sql.Date sDate = new java.sql.Date(myDate.getTime());
        
        Commentaire m = new Commentaire(maison_id, idUser, comment, sDate);
        CommentaireService ps = new CommentaireService();
        ps.ajouterCommentaire(m);
        
        JOptionPane.showMessageDialog(null, "Votre commentaire a été ajoutée");
    }

    @FXML
    private void voirComments(ActionEvent event) {
        try{
              FXMLLoader loader = new FXMLLoader(getClass().getResource("/Tfarhida/gui/MaisonDHote/EspaceComment.fxml"));
              Parent parent = loader.load();           
              Scene scene = new Scene(parent);
              Stage stage = new Stage();
              stage.setScene(scene);
              stage.show();
            }catch(IOException ex){
              System.out.println(ex.getMessage());
              ex.printStackTrace();
            }
    }

    @FXML
    private void pathListMaison(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Tfarhida/gui/MasionDHote/TestTemplate.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
            
    }

    


    
   
    
}
