package Tfarhida.gui.MaisonDHote;

import Tfarhida.base.MaConnexion;
import Tfarhida.entities.Chambre;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;


public class ModifierChambreController implements Initializable {

    Chambre chambre;
    @FXML
    private TextField chambreTxtNomEdit;
    @FXML
    private TextField nbrPersChEdit;
    @FXML
    private Button chambreBtnAjout;
    @FXML
    private ImageView chambreTxtPathEdit;
    @FXML
    private TextField prixChEdit;
    @FXML
    private Button MaisonBtnPath;
    @FXML
    private ComboBox typeLitChEdit;
    
    String sql = null;
    Connection cnx = null;
    PreparedStatement ste = null;
    ResultSet rs = null;
    
    File file;
    private FileChooser fileChooser;
    Stage stage;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> list = FXCollections.observableArrayList("Simple","Double");
        typeLitChEdit.setItems(list);
             //chambreTxtNomEdit.setText(chambre.getNom());
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg"));
      
    }    
    
     public void setChambre(Chambre chambre){
        this.chambre=chambre;
        showDetails(chambre);
    }
     
     private void showDetails(Chambre chambre) {
      if (chambre != null) {
       
        // Fill the labels with info from the person object.
        chambreTxtNomEdit.setText(chambre.getNom());
        typeLitChEdit.setValue(chambre.getType_lit());
        nbrPersChEdit.setText(Integer.toString(chambre.getNbr_pers()));
        
        try {
          file = new File(constants.getImagePath() + "chambres\\" + chambre.getPhoto());
       
            Image img = new Image(file.toURI().toURL().toExternalForm());
            ImageView i = new ImageView(img);

             chambreTxtPathEdit.setImage(i.getImage());


        } catch (MalformedURLException ex) {
            Logger.getLogger(AfficherListeMaisonsController.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex);
        }  
        prixChEdit.setText(Integer.toString((int) chambre.getPrix()));
        
        
        
        // TODO: We need a way to convert the birthday into a String!
        // birthdayLabel.setText(...);
    } else {
        // Person is null, remove all the text.
        chambreTxtNomEdit.setText("");
        typeLitChEdit.setValue("");
        nbrPersChEdit.setText("");
        prixChEdit.setText("");
    }
}


    @FXML
    private void addPhoto(ActionEvent event) throws ClassNotFoundException {
        file = fileChooser.showOpenDialog(stage);//ykhalini nekhtar fichier
        try {
            chambreTxtPathEdit.setImage(new Image(file.toURI().toURL().toExternalForm()));//path image (ligne mtaa file)
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void select(ActionEvent event) {
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
    private void EditChambre(ActionEvent event) throws SQLException {
        try {
            //chambre = maisontabel.getSelectionModel().getSelectedItem();
            cnx = MaConnexion.getinstance().getCnx();
            
            String value1 = chambreTxtNomEdit.getText();
            //String value2 = typeLitChEdit.getText();
            String value2 = typeLitChEdit.getSelectionModel().getSelectedItem().toString();
            String value3 = nbrPersChEdit.getText();
           // int valueNbr = Integer.parseInt(value3);
            String value4 = prixChEdit.getText();
           // int valueTel = Integer.parseInt(value4);
            //String value5 = chambreTxtPathEdit.getText();
            String  value5 = moveImage();

            if (value5.length() == 0) {
                JOptionPane.showMessageDialog(null, "Veuillez insérer une image");
                return;
            } else {
            }
            sql = "UPDATE chambre SET nom= '"+value1+"',type_lit= '"+value2+"',nbr_pers= '"+value3+"',prix= '"+value4+"',photo= '"+value5+"' WHERE id='"+chambre.getId()+"' ";
            ste= cnx.prepareStatement(sql);
            ste.executeUpdate();
            ste.close();
            JOptionPane.showMessageDialog(null, "chambre mis a jours");
            //RefreshTable();
           
        } catch (ClassNotFoundException ex) {
            System.out.print(ex.getMessage());
        }
    }
}
