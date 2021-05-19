/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui.Menu;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author User
 */
public class AccueilController implements Initializable {


    @FXML
    private Pane content;

    @FXML
    private JFXDrawer slider;

    @FXML
    private JFXHamburger icon;
    @FXML
    private AnchorPane accueilPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
        VBox v = FXMLLoader.load(getClass().getResource("/Tfarhida/gui/Menu/Menu.fxml"));
        slider.setSidePane(v);
        
        for (Node n: v.getChildren()){
            if(n.getAccessibleText() !=null){
                n.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
                    
                    try {
                      switch(n.getAccessibleText()){ 
                        case "accueil":
                            Node accueil = FXMLLoader.load(getClass().getResource("/Tfarhida/gui/UserPage.fxml"));
                            content.getChildren().setAll(accueil);                    
                            break;
                            
                    case "BestProgram":
                            Node BestProgram = FXMLLoader.load(getClass().getResource("/Tfarhida/gui/Organisation/AfficheBestOrg.fxml"));
                            content.getChildren().setAll(BestProgram);                    
                            break;
                            
                      
                            
                    
                        case "organiserEve": 
                            Node organiserEve = FXMLLoader.load(getClass().getResource("/Tfarhida/gui/Organisation/AfficheOrganisations.fxml"));
                            content.getChildren().setAll(organiserEve);
                             break;  
                              
                        
                        
                        case "maison":
                            Node maison = FXMLLoader.load(getClass().getResource("/Tfarhida/gui/MaisonDHote/TestTemplate.fxml"));
                            content.getChildren().setAll(maison);
                            break;  
                            
                        case "randonnée":
                            Node randonnée = FXMLLoader.load(getClass().getResource("/Tfarhida/gui/randonne/AfficherRandonneClient.fxml"));

                            content.getChildren().setAll(randonnée);
                            break;   
                            
                        case "produit": 
                            Node produit = FXMLLoader.load(getClass().getResource("/Tfarhida/gui/produit/testTemplateproduit.fxml"));
                            content.getChildren().setAll(produit);
                             break;  
                             
                        
                             
                        
          
                        case "connexion": 
                            Node connexion = FXMLLoader.load(getClass().getResource("/Tfarhida/gui/LoginUser.fxml"));
                            content.getChildren().setAll(connexion);
                             break;  
                             
                        case "Exit":  Platform.exit();
                             break; 

               
                    
                    }   
                    } catch (Exception ee) {
                    }
                    
                 
                    
                });
            }
        
        }
        
        HamburgerBasicCloseTransition hbct = new HamburgerBasicCloseTransition(icon);
        hbct.setRate(-1);
        icon.addEventFilter(MouseEvent.MOUSE_CLICKED, e->{
        
            hbct.setRate(hbct.getRate()*-1);
            hbct.play();
            
        if(slider.isOpened()){
            slider.close();
            
            }else{
            slider.open();
            }   
            

        });
             
        } catch (Exception e) {
        }
        
    }    
    
}