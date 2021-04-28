/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui.Organisation;

import Tfarhida.base.UserSession;
import Tfarhida.entities.Organisation;
import Tfarhida.services.OrganisationService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author 21651
 */
public class AfficheOrganisationsController implements Initializable {

    @FXML
    private TextField filtreField;
    @FXML
    private TableView<Organisation> tableview;
    @FXML
    private TableColumn<?, ?> clnid;
    @FXML
    private TableColumn<?, ?> clnbpersonne;
    @FXML
    private TableColumn<?, ?> clnnbJours;
    @FXML
    private TableColumn<?, ?> clndate;
    @FXML
    private TableColumn<?, ?> clnactivite;
    @FXML
    private TableColumn<?, ?> clnlieu;
    @FXML
    private TableColumn<?, ?> clnCommentaire;
    @FXML
    private TableColumn<?, ?> clnEtat;
    @FXML
    private Menu Suivi;
    @FXML
    private Menu Evaluation;
    @FXML
    private Menu Ajout;
    @FXML
    private Menu Admin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         if(UserSession.getAuth() == 0){
            Ajout.setVisible(true);
            Evaluation.setVisible(true);
            Suivi.setVisible(true);
        }else{
            Ajout.setVisible(false);
            Evaluation.setVisible(false);
            Suivi.setVisible(false);
        }  
         if(UserSession.getAuth() == 1){
            Admin.setVisible(true);
        }else{
            Admin.setVisible(false);
        }  
         
           try {

            OrganisationService os = new OrganisationService();
            List<Organisation> lo = os.afficherOrganisation();
            ObservableList<Organisation> data = FXCollections.observableArrayList(lo);
            clnid.setCellValueFactory(new PropertyValueFactory<>("id"));

            clnbpersonne.setCellValueFactory(new PropertyValueFactory<>("nbrpersonne"));
            clnCommentaire.setCellValueFactory(new PropertyValueFactory<>("commentaire"));
            clnEtat.setCellValueFactory(new PropertyValueFactory<>("etat"));
            clnnbJours.setCellValueFactory(new PropertyValueFactory<>("nbrjours"));
            clndate.setCellValueFactory(new PropertyValueFactory<>("date"));
            clnactivite.setCellValueFactory(new PropertyValueFactory<>("activite"));
            clnlieu.setCellValueFactory(new PropertyValueFactory<>("Lieu"));

            tableview.setItems(data);
            
            
            // } catch (SQLException ex) {
            //   System.out.println(ex.getMessage());
            //}
            
            FilteredList<Organisation> filteredData = new FilteredList<>(data, b -> true);
            
            // 2. Set the filter Predicate whenever the filter changes.
            filtreField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(pharm -> {
                    // If filter text is empty, display all persons.
                    
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    
                    // Compare first name and last name of every person with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();
                    
                    if (pharm.getActivite().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                        return true; // Filter matches first name.
                    } else if (pharm.getLieu().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true; // Filter matches last name.
                    }
                    else if (String.valueOf(pharm.getId()).indexOf(lowerCaseFilter)!=-1)
                        return true;
                    
                    else
                        return false; // Does not match.
                });
            });
            
         
            SortedList<Organisation> sortedData = new SortedList<>(filteredData);
            
        
            sortedData.comparatorProperty().bind(tableview.comparatorProperty());
         
            tableview.setItems(sortedData);
        } catch (SQLException ex) {  
            Logger.getLogger(AfficherOrganisationController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

      

    @FXML
 
    private void SuiviOrganisation(ActionEvent event) {
        try{
              FXMLLoader loader = new FXMLLoader(getClass().getResource("/Tfarhida/gui/Organisation/AfficherOrganisation.fxml"));
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
    private void EvaluationOrganisation(ActionEvent event) {
         try{
              FXMLLoader loader = new FXMLLoader(getClass().getResource("/Tfarhida/gui/Organisation/EvaluerOrganisation.fxml"));
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
    private void AjoutOrganisation(ActionEvent event) {
           try{
              FXMLLoader loader = new FXMLLoader(getClass().getResource("/Tfarhida/gui/Organisation/AjouterOrganisation.fxml"));
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
    private void AdminOrganisation(ActionEvent event) {
           try{
              FXMLLoader loader = new FXMLLoader(getClass().getResource("/Tfarhida/gui/Organisation/OrganisationAdmin.fxml"));
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
    
}
