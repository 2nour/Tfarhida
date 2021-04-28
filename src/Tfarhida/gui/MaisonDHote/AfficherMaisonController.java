/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui.MaisonDHote;

import Tfarhida.base.MaConnexion;
import Tfarhida.entities.Maison;
import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;
import java.io.IOException;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author User
 */
public class AfficherMaisonController implements Initializable {
    
    @FXML
    private TableView<Maison> maisontabel;
      
    @FXML
    private TableColumn<Maison, String> NomMaison;
    @FXML
    private TableColumn<Maison, String> AdresseMaison;
    @FXML
    private TableColumn<Maison, Integer> NbrMaison;
    @FXML
    private TableColumn<Maison, Integer> TelMaison;
    @FXML
    private TableColumn<Maison, String> PhotoMaison;
    @FXML
    private TableColumn<Maison, String> DescMaison;  
    
    String sql = null;
    Connection cnx = null;
    PreparedStatement ste = null;
    ResultSet rs = null;
    Maison maison = null;
    
    ObservableList<Maison> data = FXCollections.observableArrayList();
   
    private TextField val5;
    private TextField val4;
    private TextField val3;
    private TextArea val6;
    private TextField val1;
    private TextField val2;
    @FXML
    private ImageView maisonAj;
    @FXML
    private ImageView refreshM;
    @FXML
    private ImageView voirPlusM;
   
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       loadDate();
    }    

 
    private void loadDate() {
        try {
            cnx = MaConnexion.getinstance().getCnx();
            RefreshTable();
            
            NomMaison.setCellValueFactory(new PropertyValueFactory<>("nom"));
            AdresseMaison.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            NbrMaison.setCellValueFactory(new PropertyValueFactory<>("nbr_chambre"));
            TelMaison.setCellValueFactory(new PropertyValueFactory<>("tel"));
            PhotoMaison.setCellValueFactory(new PropertyValueFactory<>("photo"));
            DescMaison.setCellValueFactory(new PropertyValueFactory<>("description"));
            maisontabel.setItems(data);
            
            
        } catch (ClassNotFoundException ex) {
            System.out.print(ex.getMessage());
        }
       
        
                  
    }

    @FXML
    private void RefreshTable() {
        try {
            data.clear();
            sql = "select * from maison";
            ste = cnx.prepareStatement(sql);
             rs = ste.executeQuery();
            while(rs.next()){
                data.add(new Maison(rs.getInt("id"),rs.getString("nom"), rs.getString("adresse"), rs.getInt("nbr_chambre"), rs.getInt("tel"),rs.getString("photo"), rs.getString("description")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
    }

    private void AddMaisonBtn(MouseEvent event) {
        try{
        Parent parent = FXMLLoader.load(getClass().getResource("AjouterMaison.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        //stage.initStyle(StageStyle.UTILITY);
        stage.show();
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void EditMaison(ActionEvent event) throws SQLException {
        try {
            maison = maisontabel.getSelectionModel().getSelectedItem();
            cnx = MaConnexion.getinstance().getCnx();
            
            String value1 = val1.getText();
            String value2 = val2.getText();
            String value3 = val3.getText();
           // int valueNbr = Integer.parseInt(value3);
            String value4 = val4.getText();
           // int valueTel = Integer.parseInt(value4);
            String value5 = val5.getText();
            String value6 = val6.getText();
        
            sql = "UPDATE maison SET nom= '"+value1+"',adresse= '"+value2+"',nbr_chambre= '"+value3+"',tel= '"+value4+"',photo= '"+value5+"',description= '"+value6+"' WHERE id='"+maison.getId()+"' ";
            ste= cnx.prepareStatement(sql);
            ste.executeUpdate();
            ste.close();
            JOptionPane.showMessageDialog(null, "Update");
            RefreshTable();
           
        } catch (ClassNotFoundException ex) {
            System.out.print(ex.getMessage());
        }
       
    }

    @FXML
    private void SelectMaison(MouseEvent event) {
        int index = maisontabel.getSelectionModel().getSelectedIndex();
        if(index <= 1){
            return;
        }
        val1.setText(NomMaison.getCellData(index).toString());
        val2.setText(AdresseMaison.getCellData(index).toString());
        val3.setText(NbrMaison.getCellData(index).toString());
        val4.setText(TelMaison.getCellData(index).toString());
        val5.setText(PhotoMaison.getCellData(index).toString());
        val6.setText(DescMaison.getCellData(index).toString());
    }


    //}

    @FXML
    private void AjoutMaison(MouseEvent event) {
        try{
        Parent parent = FXMLLoader.load(getClass().getResource("AjouterMaison.fxml"));
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
    private void voirDetailsMaison(MouseEvent event) {
         maison = maisontabel.getSelectionModel().getSelectedItem();
       if(maison != null){
            try{
              FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailsMaison.fxml"));
              Parent parent = loader.load();
              Scene scene = new Scene(parent);
              Stage stage = new Stage();
              stage.setScene(scene);
              DetailsMaisonController controler = loader.getController();
                      controler.setMaison(maison);
              stage.show();
            }catch(IOException ex){
              System.out.println(ex.getMessage());
            }
       }
    }

    
    
}
