/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui.produit;

import Tfarhida.entities.vente.Comment;
import Tfarhida.entities.vente.Produit;
import Tfarhida.outils.constants;
import Tfarhida.services.venteproduit.CommentService;
import Tfarhida.services.venteproduit.ProduitService;
import com.jfoenix.controls.JFXTextArea;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import sun.java2d.cmm.CMSManager;

/**
 * FXML Controller class
 *
 * @author Nour
 */
public class CommentCardController {

    Comment comment ;
    Produit produit;
    File file;
    @FXML
    private ImageView userimg;
    @FXML
    private Text username;
    @FXML
    private JFXTextArea content;
    @FXML
    private Text datecomment;
    @FXML
    private Text sentiment;
    CommentService cms =new CommentService();
    ProduitService ps =new ProduitService();
    /**
     * Initializes the controller class.
     */
   
        
    
     public void setData(Comment p){
        this.comment=p;
       produit= ps.findProd(comment.getProduit_id());
        if(p != null) {
            
           
          content.setText(p.getContenue());
          datecomment.setText(String.valueOf(p.getDatedecommentaire()));
          sentiment.setText(p.getSentiment());
          
     
       }
         
        
    }

    @FXML
    private void deletecomment(MouseEvent event) {
     
     cms.SupprimerComment(comment.getId());
     
    }

   
    
}
