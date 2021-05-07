/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.MaisonDHote;

import Tfarhida.entities.Maison;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.InfiniteContainer;
import com.codename1.ui.Label;
import com.codename1.ui.List;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.table.Table;
import com.codename1.ui.table.TableLayout;

import gui.Accueil;
import java.util.ArrayList;
import services.MaisonDHote.MaisonServices;

/**
 *
 * @author NJR
 */
public  class ListMaisons extends Form{
    
    public ListMaisons(){
        
        //this.setTitle("Afficher Maison");   
  
         this.setLayout(BoxLayout.y());
        
         
         ArrayList<Maison> maisons = MaisonServices.getInstance().getMaisons();
         
   //     Form hi = new Form("Liste des Maisons", new FlowLayout(CENTER, LEFT));         
     
         Container list = new Container(BoxLayout.y());
       
          for(int i = 0; i < maisons.size(); i++){
             Maison maison = maisons.get(i);
             MultiButton mb = new MultiButton(maison.getNom());
             mb.setTextLine2(maison.getDescription());
             Button btn = new Button("Voir Plus");
             btn.addActionListener((evt) -> new DetailsMaison(maison).show());
             list.addAll(mb,btn);
             }
           
            int nbr = list.getComponentCount();
            System.out.println(nbr);
            int i=0;
            if(i <= nbr){ 
              add(list);
            }
            
         getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, ev -> new Accueil().showBack());
    }
    
}
