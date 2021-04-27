/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui;

import Tfarhida.base.MaConnexion;
import Tfarhida.entities.Maison;
import Tfarhida.entities.constants.constants;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class ModifierMaisonController implements Initializable {

    @FXML
    private TextField maisonTxtNom;
    @FXML
    private TextField MaisonTxtAdresse;
    @FXML
    private TextField MaisonTxtNbChambre;
    @FXML
    private TextArea MaisonTxtDesc;
    @FXML
    private Button maisonBtnUpdate;
    private TextField MaisonTxtPath;
    @FXML
    private TextField MaisonTxtTel;

    String sql = null;
    Connection cnx = null;
    PreparedStatement ste = null;
    ResultSet rs = null;
    Maison maison=null;
    @FXML
    private Button MaisonBtnPathUpdate;
    @FXML
    private ImageView MaisonImg;
    
    File file;
    private FileChooser fileChooser;
    Stage stage;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg"));
    }    
    
    
    public void setMaison(Maison maison){
        this.maison=maison;
        showDetails(maison);
    }
     
     private void showDetails(Maison maison) {
      if (maison != null) {
       
        // Fill the labels with info from the person object.
        maisonTxtNom.setText(maison.getNom());
        MaisonTxtAdresse.setText(maison.getAdresse());
        MaisonTxtNbChambre.setText(Integer.toString(maison.getNbr_chambre()));
        MaisonTxtTel.setText(Integer.toString(maison.getTel()));
         try {
          file = new File(constants.getImagePath() + "maison\\" + maison.getPhoto());
       
            Image img = new Image(file.toURI().toURL().toExternalForm());
            ImageView i = new ImageView(img);

             MaisonImg.setImage(i.getImage());


        } catch (MalformedURLException ex) {
            Logger.getLogger(AfficherListeMaisonsController.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex);
        }  
        MaisonTxtDesc.setText(maison.getDescription());
        

        // TODO: We need a way to convert the birthday into a String!
        // birthdayLabel.setText(...);
    } else {
        // Person is null, remove all the text.
        maisonTxtNom.setText("");
        MaisonTxtAdresse.setText("");
        MaisonTxtNbChambre.setText("");
        MaisonTxtTel.setText("");
        MaisonTxtDesc.setText("");
       
    }
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
    private void UpdateMaison(ActionEvent event) throws SQLException, IOException {
        try {
            //chambre = maisontabel.getSelectionModel().getSelectedItem();
            cnx = MaConnexion.getinstance().getCnx();
            
            String value1 = maisonTxtNom.getText();
            //String value2 = typeLitChEdit.getText();
            String value2 = MaisonTxtAdresse.getText();
            String value3 = MaisonTxtNbChambre.getText();
           // int valueNbr = Integer.parseInt(value3);
            //String value5 = MaisonTxtPath.getText();
           
            String  value5 = moveImage();

            if (value5.length() == 0) {
                JOptionPane.showMessageDialog(null, "Veuillez insérer une image");
                return;
            } else {
            }
           // int valueTel = Integer.parseInt(value4);
            String value6 = MaisonTxtDesc.getText();
            String value4 = MaisonTxtTel.getText();
            
            sql = "UPDATE maison SET nom= '"+value1+"',adresse= '"+value2+"',nbr_chambre= '"+value3+"',tel= '"+value4+"',photo= '"+value5+"',description= '"+value6+"' WHERE id='"+maison.getId()+"' ";
            ste= cnx.prepareStatement(sql);
            ste.executeUpdate();
            ste.close();
            JOptionPane.showMessageDialog(null, "maison mis a jours");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailsMaison.fxml"));
              Parent parent = loader.load();
              Scene scene = new Scene(parent);
              Stage stage = new Stage();
              stage.setScene(scene);

           
        } catch (ClassNotFoundException ex) {
            System.out.print(ex.getMessage());
        }
    }

    @FXML
    private void updatePhoto(ActionEvent event){
        file = fileChooser.showOpenDialog(stage);//ykhalini nekhtar fichier
        try {
            MaisonImg.setImage(new Image(file.toURI().toURL().toExternalForm()));//path image (ligne mtaa file)
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    }

    