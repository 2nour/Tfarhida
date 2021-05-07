/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.MaisonDHote;

import Tfarhida.entities.Maison;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import gui.Accueil;


import services.MaisonDHote.MaisonServices;

/**
 *
 * @author NJR
 */
public class DetailsMaison extends Form{
    
    public DetailsMaison(Maison maison){

       
        this.setTitle("Details Maison");   
        this.setLayout(BoxLayout.y());
        System.out.println(maison.getAdresse());
        Container detail = new Container(BoxLayout.y());
        MultiButton mb = new MultiButton(maison.getNom());
        
           
       // TextField adresseM = new TextField(maison.getAdresse()+ " ");
       // mb.setTextLine4(maison.getAdresse()+"  ");
        mb.setTextLine3(maison.getNbr_chambre()+"    "+maison.getAdresse()+"    "+maison.getTel()); 
        mb.setTextLine2(maison.getDescription());
        Button supprimer = new Button("Supprimer");
        supprimer.addActionListener((evt) -> {
            System.out.println(maison.getId());
         
             if(MaisonServices.getInstance().deleteMaison(maison.getId())){
                    Dialog.show("Success", "Maison supprimée avec succès", null, "OK");
                }
               
             new Accueil().showBack();
        });
        detail.addAll(mb, supprimer);
        add(detail);

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, ev -> new ListMaisons().showBack());
    }
    
}
