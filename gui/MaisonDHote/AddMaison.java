/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.MaisonDHote;

import Tfarhida.entities.Maison;
import com.codename1.io.File;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.MyApplication;
import com.sun.webkit.ThemeClient;
import gui.Accueil;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.MaisonDHote.MaisonServices;

/**
 *
 * @author NJR
 */
public class AddMaison extends Form{
    
  
    
    public AddMaison(){
        
        this.setTitle("Ajoutr Maison");
        this.setLayout(BoxLayout.y());
        
        TextField maisonNom = new TextField("", "Tapez le nom de la maison");
        TextField maisonAdresse = new TextField("", "Tapez l'adresse de la maison");
        TextField maisonNbrCh = new TextField("", "Nombre des chambres");
        TextField maisonTel = new TextField("", "Tapez le num de la maison");
       // this.setLayout(BoxLayout.x());
        TextField maisonPhoto = new TextField("", " Photo");
      
        Button ajoutBtn = new Button("Ajouter");
        
        
        TextField maisonDesc = new TextField("", "Description");
        
        Button submitBtn = new Button("Envoyer");
        
        submitBtn.addActionListener((evt) -> {
            
            if(maisonNom.getText().length() == 0 || maisonAdresse.getText().length() == 0 || maisonNbrCh.getText().length() == 0 || maisonTel.getText().length() == 0 || maisonPhoto.getText().length() == 0 || maisonDesc.getText().length() == 0 ){
                Dialog.show("Alert", "TextFileds cannot be empty", null, "OK");
            }else{
        
                try {
                Maison m = new Maison(12, maisonNom.getText(), maisonAdresse.getText(), Integer.parseInt(maisonNbrCh.getText()), Integer.parseInt(maisonTel.getText()), maisonPhoto.getText(), maisonDesc.getText());
             
                
                if(MaisonServices.getInstance().addMaisonAction(m)){
                    Dialog.show("Success", "Maison ajoutée avec succès", null, "OK");
                    new ListMaisons().showBack();
                }
                } catch (NumberFormatException e) {
                    Dialog.show("Alert", "saisir un entier", null, "OK");
                }
            }
                
        });
        this.addAll(maisonNom, maisonAdresse, maisonNbrCh, maisonTel, maisonPhoto, maisonDesc, submitBtn);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, ev -> new Accueil());
    }
    
}
