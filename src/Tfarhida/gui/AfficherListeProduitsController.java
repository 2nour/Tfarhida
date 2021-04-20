/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui;

import Tfarhida.base.MaConnexion;
import Tfarhida.entities.Panier;
import Tfarhida.entities.Produit;
import Tfarhida.services.CommandeService;
import Tfarhida.services.ProduitService;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Nour
 */
public class AfficherListeProduitsController implements Initializable {
    
    int index =-1;
    ProduitService ps= new ProduitService();
    CommandeService cms=new CommandeService();
    Connection cnx;
    PreparedStatement ste;
    Statement stm;
    Panier pi =new Panier(2,1,0,0.0);

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
    private TextField txt_image_produit;
    @FXML
    private TextField txt_marque_produit;
    @FXML
    private TextField btxt_description_produit;
    @FXML
    private Button btn_Add_IMG;
    @FXML
    private TextField txt_id_prod;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
      refresh();
       
               
    }  
    void refresh(){
       colIdProd.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("id"));
       colNomProd.setCellValueFactory(new PropertyValueFactory<Produit, String>("nom"));
       colQttProd.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("quantite"));
       colDescProd.setCellValueFactory(new PropertyValueFactory<Produit, String>("description"));
       colPrixProd.setCellValueFactory(new PropertyValueFactory<Produit, Double>("prix"));
       colPrixProd.setCellValueFactory(new PropertyValueFactory<Produit, Double>("prix"));
       colMarqueProd.setCellValueFactory(new PropertyValueFactory<Produit, String>("marque"));
       ProduitService ps=new ProduitService();
       this.tableProduits.setItems(FXCollections.observableArrayList(ps.AfficherProduits()));
    }

    @FXML
    private void Addproduit(ActionEvent event) {
        // ajout du produit
            String nom =txt_nom_produit.getText();
            String desc= btxt_description_produit.getText();
            String image = txt_image_produit.getText();
            Double prix = Double.valueOf(txt_prix_produit.getText()) ;
            int quantite=Integer.valueOf(txt_quantite_produit.getText());
            String marque =txt_marque_produit.getText();
            Produit p = new Produit(nom,quantite,desc,prix,marque,image);
            ps.ajouterProduit(p);
            JOptionPane.showMessageDialog(null, "produit ajouté");

            refresh();
            
    }

    @FXML
    private void ModifierProduit(ActionEvent event) {
        try{
            
      int id=Integer.valueOf(txt_id_prod.getText());
      String nom =txt_nom_produit.getText();
      String desc= btxt_description_produit.getText();
      String image = txt_image_produit.getText();
      Double prix = Double.valueOf(txt_prix_produit.getText()) ;
      int quantite=Integer.valueOf(txt_quantite_produit.getText());
      String marque =txt_marque_produit.getText();
      
      String query = "UPDATE produit SET nom= '"+nom+"',quantite= '"+quantite+"',description='"+desc+"'"
              + ",image='"+image+"',prix='"+prix+"',marque='"+marque+"' where id='"+id+"'";
      cnx= MaConnexion.getinstance().getCnx(); 
      stm = cnx.createStatement();

        stm.executeUpdate(query);
        JOptionPane.showMessageDialog(null, "produit mis a jours");
        refresh();

      

        } 
        catch(SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE,null,ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AfficherListeProduitsController.class.getName()).log(Level.SEVERE, null, ex);
        }  
     
    }
   

    @FXML
    private void ajouterIMGProduit(ActionEvent event) {
          try {
            MaConnexion v = new MaConnexion();
            v.filen();
            String vpath= v.getP();
            if(vpath==null){
                
            }else{
                txt_image_produit.setText(vpath);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AfficherListeProduitsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void getSelected(javafx.scene.input.MouseEvent event) {
         index = tableProduits.getSelectionModel().getSelectedIndex();
        if(index<= -1){
            return;
        }
        txt_id_prod.setText(colIdProd.getCellData(index).toString());
        txt_nom_produit.setText(colNomProd.getCellData(index).toString());
        txt_prix_produit.setText(colPrixProd.getCellData(index).toString());
        txt_quantite_produit.setText(colQttProd.getCellData(index).toString());
        txt_marque_produit.setText(colMarqueProd.getCellData(index).toString());
        btxt_description_produit.setText(colDescProd.getCellData(index).toString());


    }

    @FXML
    private void supprimerProduit(ActionEvent event) {
           int id=Integer.valueOf(txt_id_prod.getText());
           ps.SupprimerProduit(id);
           JOptionPane.showMessageDialog(null, "produit supprimé");
           refresh();

    }

    @FXML
    private void ajouterPANIER(ActionEvent event) {
            int id=Integer.valueOf(txt_id_prod.getText());
            String nom =txt_nom_produit.getText();
            String desc= btxt_description_produit.getText();
            String image = txt_image_produit.getText();
            Double prix = Double.valueOf(txt_prix_produit.getText()) ;
            int quantite=Integer.valueOf(txt_quantite_produit.getText());
            String marque =txt_marque_produit.getText();
            Produit p = new Produit(id,nom,quantite,desc,prix,marque,image);
            cms.ajouterCommande(p, pi);
        
        
        
    }

    @FXML
    private void VoIRPANIER(ActionEvent event) {
        try{
         Parent parent = FXMLLoader.load(getClass().getResource("AfficherPanier.fxml"));
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
