/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui;

import Tfarhida.entities.Commentaire;
import Tfarhida.entities.Maison;
import Tfarhida.entities.constants.constants;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author NJR
 */
public class AfficherCommentsController implements Initializable {

    @FXML
    private Text NomUser;
    @FXML
    private Label comment;
    @FXML
    private Label dateComment;

    Commentaire commentaire;
    File file;
    @FXML
    private VBox card;
    @FXML
    private ImageView suppComment;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setComment(Commentaire commentaire){
        this.commentaire = commentaire;
        setData(commentaire);
        //voirDetailsMaison();
    }
    
     public void setData(Commentaire m){
       if(m != null) {
         
          System.out.println(m.getId());   
              
          //String nomUser = m.getUser_id().nom;
          int IdUser = m.getUser_id();
          String nomUser = Integer.toString(IdUser);
          
          Date date = m.getDate();
          Format formatter;
          formatter = new SimpleDateFormat("MM/dd/YYYY");
          String s = formatter.format(date);
          
          
          NomUser.setText(nomUser);
          comment.setText(m.getComment());
          dateComment.setText(s);
          
          System.out.println("im in 2"); 
         // lprix.setText(String.valueOf(p.getPrix()));
          System.out.println("i m alll here");  
     
       }
     
       else {
           
        System.out.println("i m empty");   
        
       }
      
        
    };

    @FXML
    private void supprimerComment(MouseEvent event) {
    }
}
