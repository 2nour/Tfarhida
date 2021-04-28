 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui.MaisonDHote;

import Tfarhida.base.MaConnexion;
import Tfarhida.base.UserSession;
import Tfarhida.entities.Chambre;
import Tfarhida.entities.Maison;
import Tfarhida.entities.constants.constants;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author User
 */
public class AfficherChambreController implements Initializable {

    @FXML
    private TableColumn<Chambre, String> tabNomCh;
    @FXML
    private TableColumn<Chambre, Double> tabPrixCh;
   
    @FXML
    private TableView<Chambre> chambreTab;
    
    String sql = null;
    Connection cnx = null;
    PreparedStatement ste = null;
    ResultSet rs = null;
    Chambre chambre;
    
    ObservableList<Chambre> data = FXCollections.observableArrayList();
    @FXML
    private ImageView refTabCh;
    @FXML
    private Label TypeLitChD;
    @FXML
    private Label NbrPersChD;
    @FXML
    private Label PrixChD;
    @FXML
    private ImageView ImgChD;
    @FXML
    private Label nomChD;
    @FXML
    private TableColumn<Chambre, String> tabTypeCh;
    @FXML
    private TableColumn<Chambre, Integer> tabNbrPersCh;
    @FXML
    private TableColumn<Chambre, String> tabImgCh;
    @FXML
    private Button SuppCh;
    @FXML
    private MenuItem EditCh1;
    @FXML
    private Button ReserverChBtn;
    
    Maison maison;
    @FXML
    private MenuItem listReservbtn1;
    @FXML
    private MenuItem demandeReservbtn1;
    
    File file;
    @FXML
    private Label PrixChD1;
    @FXML
    private Menu voirChM1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       if(UserSession.getAuth() == 2){
            SuppCh.setVisible(true);
            ReserverChBtn.setVisible(false);
            demandeReservbtn1.setVisible(true);
            listReservbtn1.setVisible(false);
            EditCh1.setVisible(true);
        }else{
            SuppCh.setVisible(false);
            ReserverChBtn.setVisible(true);
            demandeReservbtn1.setVisible(false);
            listReservbtn1.setVisible(true);
            EditCh1.setVisible(false);
        }
        //showChambreDetails(chambre);
        
        // Clear chambre details.
        showChambreDetails(null);
        //Listen for selection changes and show the chambre details when changed.
        chambreTab.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldValue, newValue) -> showChambreDetails(newValue));
    }    
    
    public void setMaison(Maison maison){
        this.maison=maison;
        AfficheCh();
    }
    
    private void AfficheCh() {
        try {
            cnx = MaConnexion.getinstance().getCnx();
            RefreshTabCh();
            tabNomCh.setCellValueFactory(new PropertyValueFactory<>("nom"));
            tabPrixCh.setCellValueFactory(new PropertyValueFactory<>("prix"));
            tabTypeCh.setCellValueFactory(new PropertyValueFactory<>("type_lit"));
            tabNbrPersCh.setCellValueFactory(new PropertyValueFactory<>("nbr_pers"));
            tabImgCh.setCellValueFactory(new PropertyValueFactory<>("photo"));
            chambreTab.setItems(data);

        } catch (ClassNotFoundException ex) {
            System.out.print(ex.getMessage());
        }
       
        
                  
    }

    @FXML
    private void RefreshTabCh() {
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
    
    private void showChambreDetails(Chambre chambre) {
    if (chambre != null) {
        // Fill the labels with info from the person object.
        nomChD.setText(chambre.getNom());
        TypeLitChD.setText(chambre.getType_lit());  
        NbrPersChD.setText(Integer.toString(chambre.getNbr_pers()));
        PrixChD.setText(Integer.toString((int) chambre.getPrix()));
          
              try {
          file = new File(constants.getImagePath() + "chambres\\" + chambre.getPhoto());
       
            Image img = new Image(file.toURI().toURL().toExternalForm());
            ImageView i = new ImageView(img);
           

             ImgChD.setImage(i.getImage());


        } catch (MalformedURLException ex) {
            Logger.getLogger(AfficherListeMaisonsController.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex);
        }   
        
        // TODO: We need a way to convert the birthday into a String!
        // birthdayLabel.setText(...);
    } else {
        // Person is null, remove all the text.
        nomChD.setText("");
        TypeLitChD.setText("");
        NbrPersChD.setText("");
        PrixChD.setText("");
       
    }
}


    @FXML
    private void ModifierChambre(ActionEvent event) {
        chambre = chambreTab.getSelectionModel().getSelectedItem();
       if(chambre != null){
            try{
              FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierChambre.fxml"));
              Parent parent = loader.load();
              Scene scene = new Scene(parent);
              Stage stage = new Stage();
              stage.setScene(scene);
              ModifierChambreController controler = loader.getController();
                      controler.setChambre(chambre);
              stage.show();
            }catch(IOException ex){
              System.out.println(ex.getMessage());
            }
       }
    }

    @FXML
    private void ReserverChambre(ActionEvent event) {
        chambre = chambreTab.getSelectionModel().getSelectedItem();
       if(chambre != null){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterReservation.fxml"));
              Parent parent = loader.load();
              Scene scene = new Scene(parent);
              Stage stage = new Stage();
              stage.setScene(scene);
              AjouterReservationController controler = loader.getController();
                      controler.setReservation(chambre);
              stage.show();
            }catch(IOException ex){
              System.out.println(ex.getMessage());
            }
       }
    }

    @FXML
    private void voirListReserv(ActionEvent event) {
        try{
              FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherReservation.fxml"));
              Parent parent = loader.load();
              Scene scene = new Scene(parent);
              Stage stage = new Stage();
              stage.setScene(scene);
              stage.show();
            }catch(IOException ex){
              System.out.println(ex.getMessage());
            }
    }


    @FXML
    private void DeleteChambre(ActionEvent event) {
        try {
            chambre = chambreTab.getSelectionModel().getSelectedItem();
            sql = "DELETE FROM chambre WHERE id="+chambre.getId();
            ste = cnx.prepareStatement(sql);
            ste.executeUpdate();
            JOptionPane.showConfirmDialog(null, "Supprimer");
            //ste.close();
            RefreshTabCh();
        } catch (SQLException ex) {
            Logger.getLogger(AfficherChambreController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void voirDemandeReservation(ActionEvent event) {
        try{
              FXMLLoader loader = new FXMLLoader(getClass().getResource("EtatsReservation.fxml"));
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
