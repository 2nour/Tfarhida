/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui;

import Tfarhida.base.MaConnexion;
import Tfarhida.entities.Chambre;
import Tfarhida.entities.Maison;
import Tfarhida.entities.constants.constants;
import Tfarhida.services.ChambreService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author User
 */
public class AjouterChambreController implements Initializable {

    @FXML
    private TextField chambreTxtNom;
    @FXML
    private TextField nbrPersCh;
    @FXML
    private Button chambreBtnAjout;
    private TextField chambreTxtPath;
    @FXML
    private TextField prixCh;
    @FXML
    private Button MaisonBtnPath;
    @FXML
    private ComboBox typeLitCh;
    
    Maison maison;
    @FXML
    private ImageView ChambreImage;
    
    File file;
    private FileChooser fileChooser;
    Stage stage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> list = FXCollections.observableArrayList("Simple","Double");
        typeLitCh.setItems(list);
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg"));
    }    

    public void setMaison(Maison maison){
        this.maison=maison;
    }
    
    @FXML
    private void addMaison(ActionEvent event) throws ClassNotFoundException {
            String nom = chambreTxtNom.getText();
            String nbr = nbrPersCh.getText();
            int nbr_pers= Integer.parseInt(nbr);
            String type_lit = typeLitCh.getSelectionModel().getSelectedItem().toString();
            String p = prixCh.getText();
            int prix = Integer.parseInt(p);
            
            String photo = moveImage();
 

          if (photo.length() == 0) {
            JOptionPane.showMessageDialog(null, "Veuillez insérer une image");
            return;
          }
          
            int idM = maison.getId();
           
            Chambre ch = new Chambre(27, idM, nom, type_lit, nbr_pers, prix, photo);
            ChambreService ps = new ChambreService();
            ps.ajouterChambre(ch);
            JOptionPane.showMessageDialog(null, "Votre chambre a été ajoutée");
            
    }
    
    private String moveImage() {
        if (file != null) {
            try {
                String fileName = file.getName();
                int doitIndex = fileName.lastIndexOf(".");
                String imageName = fileName.substring(0, doitIndex) + new java.util.Date().getTime() + "." + fileName.substring(doitIndex + 1);
                String imageNameTodb = constants.getImagePath() + "chambres\\" + imageName;
                File dest = new File(imageNameTodb);
                Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                return imageName;

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return "";
    }

    @FXML
    private void addPhoto(ActionEvent event) throws ClassNotFoundException {
        file = fileChooser.showOpenDialog(stage);//ykhalini nekhtar fichier
        try {
            ChambreImage.setImage(new Image(file.toURI().toURL().toExternalForm()));//path image (ligne mtaa file)
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    

    @FXML
    private void select(ActionEvent event) {
       
    }
    
}
