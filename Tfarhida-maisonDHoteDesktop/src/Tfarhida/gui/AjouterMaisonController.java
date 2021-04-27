/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui;

import Tfarhida.base.MaConnexion;
import Tfarhida.entities.Maison;
import Tfarhida.entities.constants.constants;
import Tfarhida.services.MaisonService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
public class AjouterMaisonController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private TextField maisonTxtNom;
    @FXML
    private TextField MaisonTxtAdresse;
    @FXML
    private TextField MaisonTxtNbChambre;
    @FXML
    private TextArea MaisonTxtDesc;
    @FXML
    private Button maisonBtnAjout;
    private TextField MaisonTxtPhoto;
    @FXML
    private TextField MaisonTxtTel;
    private TextField MaisonTxtPath;
    @FXML
    private Button MaisonBtnPath;

    File file;
    private FileChooser fileChooser;
    Stage stage;
    @FXML
    private ImageView MaisonImage;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg"));
    }     

    @FXML
    private void addMaison(ActionEvent event) throws ClassNotFoundException {
       
            String nom = maisonTxtNom.getText();
            String adresse = MaisonTxtAdresse.getText();
            String nbrChambre = MaisonTxtNbChambre.getText();
            int nbr_chambre = Integer.parseInt(nbrChambre);
            String telephone = MaisonTxtTel.getText();
            int tel = Integer.parseInt(telephone);
            //String photo = MaisonTxtPath.getText();
            String description = MaisonTxtDesc.getText();
            String photo = moveImage();
 

          if (photo.length() == 0) {
            JOptionPane.showMessageDialog(null, "Veuillez insérer une image");
            return;
        }
            Maison m = new Maison(25, nom, adresse, nbr_chambre, tel, photo, description);
            MaisonService ps = new MaisonService();
            ps.ajouterMaison(m);
            JOptionPane.showMessageDialog(null, "Votre maison a été ajoutée");
            
    }   

    private String moveImage() {
        if (file != null) {
            try {
                String fileName = file.getName();
                int doitIndex = fileName.lastIndexOf(".");
                String imageName = fileName.substring(0, doitIndex) + new java.util.Date().getTime() + "." + fileName.substring(doitIndex + 1);
                String imageNameTodb = constants.getImagePath() + "maison\\" + imageName;
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
    private void addPhoto(ActionEvent event){
       file = fileChooser.showOpenDialog(stage);//ykhalini nekhtar fichier
        try {
            MaisonImage.setImage(new Image(file.toURI().toURL().toExternalForm()));//path image (ligne mtaa file)
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
