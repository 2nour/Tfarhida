/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui;

import Tfarhida.entities.Comment;
import Tfarhida.entities.Produit;
import Tfarhida.entities.Utilisateur;
import Tfarhida.outils.Outils;
import Tfarhida.services.CommandeService;
import Tfarhida.services.CommentService;
import Tfarhida.services.ProduitService;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
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
    CommentService cms=new CommentService();
    Utilisateur u = new Utilisateur(1,"jo@gmail.com","jo","12345678","ROLE_USER");
    @FXML
    private Label lnom;
    @FXML
    private Label lprix;
    @FXML
    private Label lqtt;
    @FXML
    private Label lmarque;
    @FXML
    private TextArea commentText;
    @FXML
    private JFXButton ajouterComment;
    @FXML
    private TableColumn<?, ?> coluser;
    @FXML
    private TableColumn<Comment,String> colcontenue;
    @FXML
    private TableColumn<Comment,Date> datedecomm;
    @FXML
    private TableView<Comment> commTable;
    @FXML
    private TableColumn<Comment,Integer> idcomment;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
       refresh(p); 
      
    }  
     void refresh(Produit p){
       idcomment.setCellValueFactory(new PropertyValueFactory<Comment,Integer>("id"));
       //coluser.setCellValueFactory(new PropertyValueFactory<>("nom"));
       colcontenue.setCellValueFactory(new PropertyValueFactory<Comment,String>("contenue"));
       datedecomm.setCellValueFactory(new PropertyValueFactory<Comment,Date>("datedecommentaire"));
       if(p!=null){
           this.commTable.setItems(FXCollections.observableArrayList(cms.AfficherComments(getP())));
 
       }
    }
    
    public void setProduit(Produit p){
       this.p=p; 
       voirprod(p);
       refresh(p); 
    }

    public Produit getP() {
        return p;
    }
     
    
    private void voirprod(Produit p) {
    if (p != null) {
       
        // Fill the labels with info from the person object.
        lnom.setText(p.getNom());
        lprix.setText(Double.toString(p.getPrix()));
        lqtt.setText(Integer.toString(p.getQuantite()));
        lmarque.setText(p.getMarque());
        
        
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
     
        String content =commentText.getText();
        LocalDate datecomment= java.time.LocalDate.now();
        java.sql.Date sqlDate = java.sql.Date.valueOf( datecomment );
        int user_id = u.getId();
        
         if (Outils.containsBadWords(content)) {
            JOptionPane.showMessageDialog(null, "Cet Avis ne respecte pas nos standards de la communauté en matière de contenus indésirables");
            return;
        }
        
        Comment comment= new Comment(p.getId(),content,sqlDate,user_id,u.getUsername());
        cms.ajouterCommentProduit(p, comment);
        JOptionPane.showMessageDialog(null, "comment ajouté");
        refresh(p);
             
    }
    
    private void getSelected(javafx.scene.input.MouseEvent event) {
         index = commTable.getSelectionModel().getSelectedIndex();
        if(index<= -1){
            return;
        }
        
    }

    @FXML
    private void supprimerComment(ActionEvent event) {
       Comment c = commTable.getSelectionModel().getSelectedItem();
        cms.SupprimerComment(c.getId());
        JOptionPane.showMessageDialog(null, "commentaire supprimé");
        refresh(p);
    }
    
}
