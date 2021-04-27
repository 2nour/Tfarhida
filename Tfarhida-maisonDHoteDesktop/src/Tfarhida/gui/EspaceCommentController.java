/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui;

import Tfarhida.entities.Commentaire;
import Tfarhida.entities.Maison;
import Tfarhida.services.CommentaireService;
import Tfarhida.services.MaisonService;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author NJR
 */
public class EspaceCommentController implements Initializable {

    Maison maison;
    Connection cnx;
    
    PreparedStatement ste;
    
   
    
    
    @FXML
    private ScrollPane scroll;
    @FXML
    private VBox grid; 
    
    CommentaireService ps = new CommentaireService();
    ArrayList<Commentaire>  comments = (ArrayList<Commentaire>) ps.afficherComments();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
   int column = 0;
        int row = 1; 
        if (comments.size()>0){
           for(int i=0; i<comments.size();i++)  {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Tfarhida/gui/AfficherComments.fxml"));
                Parent parent = loader.load();
                
                System.out.println("homaaaa");   

                AfficherCommentsController commentsController;
                commentsController = loader.getController();
                commentsController.setComment(comments.get(i));
                
                System.out.println("im hererrr");   
                
                
                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.getChildren().add(parent);
               
                GridPane.setMargin(parent, new Insets(10));

                } catch (IOException ex) {
                Logger.getLogger(EspaceCommentController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("hereee");
            } 
                   
        }

        
    } ;  
}
      
    
    
   
     
    

    
       
    
}