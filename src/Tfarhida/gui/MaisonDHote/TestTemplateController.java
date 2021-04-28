/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tfarhida.gui.MaisonDHote;

import Tfarhida.base.UserSession;
import Tfarhida.entities.Maison;
import Tfarhida.services.MaisonService;

import javafx.geometry.Insets;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author NJR
 */
public class TestTemplateController implements Initializable {
   
    MaisonService ps = new MaisonService();
    ArrayList<Maison>  maisons = (ArrayList<Maison>) ps.afficherMaison();
    
    //private MyListener myListener;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;
    @FXML
    private TextField serachM;
    @FXML
    private MenuItem btnAjoutM;

    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(UserSession.getAuth() == 2){
            btnAjoutM.setVisible(true);
        }else{
            btnAjoutM.setVisible(false);
        }
       serachM.textProperty().addListener((observable, oldValue, newValue) -> {
			List <Maison> filteredData = maisons.stream().filter(maison -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				return (maison.getNom().toLowerCase().contains(lowerCaseFilter) ||
                                        maison.getAdresse().toLowerCase().contains(lowerCaseFilter)) 
					; 
				
			}).collect(Collectors.toList());
                        rafraichirGrid(filteredData);
		});
       rafraichirGrid(maisons);
    }
    
    private void rafraichirGrid(List<Maison> pMaisons){
        int column = 0;
        int row = 1; 
        if(grid.getChildren() != null){
            grid.getChildren().clear();
        }
        if (pMaisons.size()>0){
           for(int i=0; i<pMaisons.size();i++)  {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Tfarhida/gui/MaisonDHote/Card.fxml"));
                Parent parent = loader.load();

                CardController cardController;
                cardController = loader.getController();
                cardController.setMaison(pMaisons.get(i));
                
                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(parent, column++, row);
               
                GridPane.setMargin(parent, new Insets(10));

                } catch (IOException ex) {
                Logger.getLogger(TestTemplateController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("hereee");
            } 
                   
        }
  
    } 
    
   
}

    @FXML
    private void AjouterMaison(ActionEvent event) {
        try{
              FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterMaison.fxml"));
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
