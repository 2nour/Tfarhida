/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui.produit;

import Tfarhida.base.UserSession;
import Tfarhida.entities.vente.Comment;
import Tfarhida.entities.vente.Produit;
import Tfarhida.outils.Outils;
import Tfarhida.outils.constants;
import Tfarhida.services.venteproduit.CommandeService;
import Tfarhida.services.venteproduit.CommentService;
import Tfarhida.services.venteproduit.ProduitService;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javax.swing.JOptionPane;
import org.json.JSONException;

/**
 * FXML Controller class
 *
 * @author Nour
 */
public class VoirProduitController implements Initializable {

    Produit p;
    int index =-1;
    File file;
    CommentService cms=new CommentService();
    int u = UserSession.getId();
    @FXML
    private Label lnom;
    @FXML
    private Label lprix;
    @FXML
    private Label lqtt;
    @FXML
    private Label lmarque;
  
   @FXML
    private ImageView prodImage;
    @FXML
    private PieChart piechart;
    @FXML
    private TextArea commentText;
    @FXML
    private VBox vbox;
    @FXML
    private Text nbComments;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
       refresh(p);
        try {
            voirprod(p);
        } catch (MalformedURLException ex) {
            Logger.getLogger(VoirProduitController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        
        
      
    } 
    
    public void commentStatistic(Produit p){
        List<Integer>stat= cms.displayAvisStatistic(p);
        if(!stat.isEmpty()){
           
        ObservableList<PieChart.Data> piedata= FXCollections.observableArrayList(
        new PieChart.Data("avis positive", stat.get(0)),
        new PieChart.Data("avis negative", stat.get(1)),
        new PieChart.Data("neutre", stat.get(2))
              );
        piechart.setData(piedata);
        piechart.setClockwise(true);
        piechart.setStartAngle(180); 
         piedata.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " ", data.pieValueProperty(), "%"
                        )
                )
        );
        
        }
        else{
            System.out.println("stat empty");
        }
       
        
    }
  
    public void refresh(Produit p){
        ArrayList<Comment>  comments = (ArrayList<Comment>) cms.AfficherComments(p);
        commentStatistic(p); 
        nbComments.setText(String.valueOf(comments.size()));

        if (comments.size()>0){
            
           for(int i=0; i<comments.size();i++)  {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Tfarhida/gui/produit/CommentCard.fxml"));
                AnchorPane parent = loader.load();
                

                CommentCardController commentCardController = loader.getController();
                commentCardController.setData(comments.get(i));
                
                vbox.getChildren().add(parent);
                
                
                
                } catch (IOException ex) {
                Logger.getLogger(AfficherPanierController.class.getName()).log(Level.SEVERE, null, ex);
            } }
        }
           else{    
             
          
           }
        
    
     }
    public void setProduit(Produit p){
       this.p=p; 
        try {
            voirprod(p);
        } catch (MalformedURLException ex) {
            Logger.getLogger(VoirProduitController.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
        }
       refresh(p); 
    }

   
     
    
    private void voirprod(Produit p) throws MalformedURLException {
    if (p != null) {
       
        // Fill the labels with info from the person object.
        lnom.setText(p.getNom());
        lprix.setText(Double.toString(p.getPrix()));
        lqtt.setText(Integer.toString(p.getQuantite()));
        lmarque.setText(p.getMarque());
           
          file = new File(constants.getImagePath() + "produits\\" + p.getImage());
       
            Image img = new Image(file.toURI().toURL().toExternalForm());
            ImageView i = new ImageView(img);
             System.out.println(p.getNom());   

             prodImage.setImage(i.getImage());
             prodImage.setFitWidth(600);
             prodImage.setFitHeight(250);
       
        // TODO: We need a way to convert the birthday into a String!
        // birthdayLabel.setText(...);
    } else {
        // Person is null, remove all the text.
        lnom.setText("");
        lprix.setText("");
        lqtt.setText("");
        lmarque.setText("");
        
    }
}

    @FXML
    private void ajouterComment(ActionEvent event) throws IOException, JSONException {
     
        try {
            
            
        String content =commentText.getText();
        LocalDate datecomment= java.time.LocalDate.now();
        java.sql.Date sqlDate = java.sql.Date.valueOf( datecomment );
        int user_id = u;
        
         if (Outils.containsBadWords(content)) {
            JOptionPane.showMessageDialog(null, "Cet Avis ne respecte pas nos standards de la communauté en matière de contenus indésirables");
            return;
        }
        
        Comment comment= new Comment(p.getId(),content,sqlDate,user_id,UserSession.getUsername());
        cms.ajouterCommentProduit(p, comment);
        JOptionPane.showMessageDialog(null, "comment ajouté");
        vbox.getChildren().clear();
        refresh(p);
                
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
      
    }

    @FXML
    private void reload(MouseEvent event) {
        vbox.getChildren().clear();
        refresh(p);
        commentStatistic(p);
    }
    
    

    
}
