/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui.produit;

import Tfarhida.base.MaConnexion;
import Tfarhida.entities.vente.Categorie;
import Tfarhida.entities.vente.Panier;
import Tfarhida.entities.vente.Produit;
import Tfarhida.outils.constants;
import Tfarhida.services.venteproduit.CategorieService;
import Tfarhida.services.venteproduit.CommandeService;
import Tfarhida.services.venteproduit.PanierService;
import Tfarhida.services.venteproduit.ProduitService;
import com.jfoenix.controls.JFXComboBox;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Nour
 */
public class AfficherListeProduitsController implements Initializable {

    int index = -1;
    ProduitService ps = new ProduitService();
    CommandeService cms = new CommandeService();
    Connection cnx;
    PreparedStatement ste;
    Statement stm;
    private FileChooser fileChooser;
    private File file;
    Stage stage;
    private TextField txt_Prods;
    @FXML
    private TableColumn<Produit, String> colNomProd;
    @FXML
    private TableColumn<Produit, Integer> colQttProd;
    @FXML
    private TableColumn<Produit, String> colDescProd;
    @FXML
    private TableColumn<Produit, Double> colPrixProd;
    @FXML
    private TableColumn<Produit, String> colMarqueProd;
    @FXML
    private TableView<Produit> tableProduits;
    @FXML
    private Button AddProd;
    @FXML
    private TableColumn<Produit, Integer> colIdProd;
    @FXML
    private TextField txt_nom_produit;
    @FXML
    private TextField txt_quantite_produit;
    @FXML
    private TextField txt_prix_produit;
    @FXML
    private TextField txt_marque_produit;
    @FXML
    private TextField btxt_description_produit;
    @FXML
    private Button btn_Add_IMG;
    @FXML
    private TextField txt_id_prod;
    @FXML
    private AnchorPane paneAjout;
    @FXML
    private ImageView prodImage;
    @FXML
    private JFXComboBox<String> categories;
    CategorieService categorieService = new CategorieService();
    PanierService panierService = new PanierService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        paneAjout.setVisible(false);
        paneAjout.setManaged(false);

        refresh();
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg"));
        for (Categorie c : categorieService.AfficherCategories()) {
              categories.getItems().add(c.getNom());

        }

    }

    void refresh() {
        colIdProd.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("id"));
        colNomProd.setCellValueFactory(new PropertyValueFactory<Produit, String>("nom"));
        colQttProd.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("quantite"));
        colDescProd.setCellValueFactory(new PropertyValueFactory<Produit, String>("description"));
        colPrixProd.setCellValueFactory(new PropertyValueFactory<Produit, Double>("prix"));
        colPrixProd.setCellValueFactory(new PropertyValueFactory<Produit, Double>("prix"));
        colMarqueProd.setCellValueFactory(new PropertyValueFactory<Produit, String>("marque"));
        ProduitService ps = new ProduitService();
        this.tableProduits.setItems(FXCollections.observableArrayList(ps.AfficherProduits()));
    }

    private String moveImage() {
        if (file != null) {
            try {
                String fileName = file.getName();
                int doitIndex = fileName.lastIndexOf(".");
                String imageName = fileName.substring(0, doitIndex) + new java.util.Date().getTime() + "." + fileName.substring(doitIndex + 1);
                String imageNameTodb = constants.getImagePath() + "produits\\" + imageName;
                File dest = new File(imageNameTodb);
                Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                return imageName;

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return "";
    }

    private void loadImage(ActionEvent event) {

        file = fileChooser.showOpenDialog(stage);//ykhalini nekhtar fichier
        try {
            prodImage.setImage(new Image(file.toURI().toURL().toExternalForm()));//path image (ligne mtaa file)
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    private void Addproduit(ActionEvent event) throws SQLException {
        // ajout du produit
        String nom = txt_nom_produit.getText();
        String desc = btxt_description_produit.getText();
        Double prix = Double.valueOf(txt_prix_produit.getText());
        int quantite = Integer.valueOf(txt_quantite_produit.getText());
        String marque = txt_marque_produit.getText();
        String nomImage = moveImage();

        if (nomImage.length() == 0) {
            JOptionPane.showMessageDialog(null, "Veuillez insérer une image");
            return;
        }

        if (nom.length() == 0) {
            JOptionPane.showMessageDialog(null, "le nom est obligatoire");
            return;
        }
        
       
       
        
            Produit p = new Produit(nom, quantite, desc, prix, marque, nomImage);

            ps.ajouterProduit(p);
            
            
            refresh();
            
           
        }

        @FXML
        private void ModifierProduit (ActionEvent event) throws SQLException {

        int id = Integer.valueOf(txt_id_prod.getText());
            String nom = txt_nom_produit.getText();
            String desc = btxt_description_produit.getText();
            Double prix = Double.valueOf(txt_prix_produit.getText());
            int quantite = Integer.valueOf(txt_quantite_produit.getText());
            String marque = txt_marque_produit.getText();
            String nomImage = moveImage();

            if (nomImage.length() == 0) {
                JOptionPane.showMessageDialog(null, "Veuillez insérer une image");
                return;
            }

            Produit p = new Produit(id, nom, quantite, desc, prix, marque, nomImage);
            ps.ModifierProduit(p);
            categorieService=new CategorieService();
            Categorie c=categorieService.findbynom(categories.getValue());
            categorieService.ajouterCategorie(p.getId(),c );
            JOptionPane.showMessageDialog(null, "produit mis a jours");
            refresh();

        }

        @FXML
        private void ajouterIMGProduit
        (ActionEvent event
        
            ) {
        file = fileChooser.showOpenDialog(stage);//ykhalini nekhtar fichier
            try {
                prodImage.setImage(new Image(file.toURI().toURL().toExternalForm()));//path image (ligne mtaa file)
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        @FXML
        private void getSelected
        (javafx.scene.input.MouseEvent event
        
            ) {
        Produit p = tableProduits.getSelectionModel().getSelectedItem();
            index = tableProduits.getSelectionModel().getSelectedIndex();
            if (index <= -1) {
                return;
            }
            txt_id_prod.setText(colIdProd.getCellData(index).toString());
            txt_nom_produit.setText(colNomProd.getCellData(index).toString());
            txt_prix_produit.setText(colPrixProd.getCellData(index).toString());
            txt_quantite_produit.setText(colQttProd.getCellData(index).toString());
            txt_marque_produit.setText(colMarqueProd.getCellData(index).toString());
            btxt_description_produit.setText(colDescProd.getCellData(index).toString());
            file = new File(constants.getImagePath() + "produits\\" + p.getImage());
            try {
                Image img = new Image(file.toURI().toURL().toExternalForm());
                ImageView i = new ImageView(img);
                prodImage.setImage(i.getImage());
            } catch (MalformedURLException ex) {
                Logger.getLogger(AfficherListeProduitsController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        @FXML
        private void supprimerProduit
        (ActionEvent event) throws SQLException {
            int id = Integer.valueOf(txt_id_prod.getText());
            panierService.ProduitestSupprimer(id);
            ps.SupprimerProduit(id);
            JOptionPane.showMessageDialog(null, "produit supprimé");
            refresh();

        }

        @FXML
        private void showAddproduit
        (ActionEvent event
        
            ) {
        if (paneAjout.isVisible()) {
                paneAjout.setVisible(false);
                paneAjout.setManaged(false);
            } else {
                paneAjout.setVisible(true);
                paneAjout.setManaged(true);
            }
        }

        @FXML
        private void voirproduit
        (ActionEvent event
        
            ) {

        try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("VoirProduit.fxml"));
                Parent parent = loader.load();
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.setScene(scene);
                VoirProduitController controler = loader.getController();
                Produit p = tableProduits.getSelectionModel().getSelectedItem();
                controler.setProduit(p);
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        }

        @FXML
        private void categories
        (ActionEvent event) {
            
    }

}
