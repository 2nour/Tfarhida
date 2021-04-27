/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tfarhida.gui;

import Tfarhida.entities.constants.constants;

import Tfarhida.entities.Maison;
import Tfarhida.services.MaisonService;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author NJR
 */
public class CardController {
   
    File file;
    Maison maison;
    
    @FXML
    private VBox card;
    @FXML
    private ImageView prodImage;
    @FXML
    private Text lnom;
    @FXML
    private Text ldesc;
    @FXML
    private Button btnVoirPlus;

  
    //private MyListener myListener;

    public void setMaison(Maison maison){
        this.maison = maison;
        setData(maison);
        //voirDetailsMaison();
    }
    
     public void setData(Maison m){
       if(m != null) {
         
          System.out.println(m.getNom());   
              try {
          file = new File(constants.getImagePath() + "maison\\" + m.getPhoto());
       
            Image img = new Image(file.toURI().toURL().toExternalForm());
            ImageView i = new ImageView(img);
             System.out.println(m.getNom());   

             prodImage.setImage(i.getImage());


        } catch (MalformedURLException ex) {
            Logger.getLogger(AfficherListeMaisonsController.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex);
        }   
          lnom.setText(m.getNom());
          ldesc.setText(m.getDescription());
          System.out.println("im in 2"); 
         // lprix.setText(String.valueOf(p.getPrix()));
          System.out.println("i m alll here");  
     
       }
     
       else {
           
        System.out.println("i m empty");   
        
       }

        
    };


    @FXML
    private void voirDetailsMaison(ActionEvent event) {
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

