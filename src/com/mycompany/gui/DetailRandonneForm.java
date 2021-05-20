/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.mycompany.entites.Randonne;
import com.mycompany.entites.ReservationRandonne;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author PC
 */
public class DetailRandonneForm extends BaseForm {

    Form current;
    
    EncodedImage ecoEncodedImage;
    ImageViewer imgViewer;
    Image img;
    String url = "http://localhost/images/";

    public DetailRandonneForm(Resources res, Randonne rec) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("container");
        setTitle("Ajouter Reservation");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        //TextField Sujetrec = new TextField(rec.getSujetRec(), "Sujet", 20, TextField.ANY);

        Label VilleDepartTxt = new Label("VilleDepart :" + rec.getVilledepart(), "NewsTopLine2");
        Label VilleArriveeTxt = new Label("VilleArrivee :" + rec.getVillearrivee(), "NewsTopLine2");
        Label DateDepartTxt = new Label("DateDepart :" + rec.getDatedepart(), "NewsTopLine2");
        Label DateRetourTxt = new Label("DateRetour :" + rec.getDateretour(), "NewsTopLine2");
        Label ActiviteTxt = new Label("Activite :" + rec.getActivite(), "NewsTopLine2");
        Label DureeTxt = new Label("Duree :" + rec.getDuree(), "NewsTopLine2");
        Label DifficulteTxt = new Label("Difficulte :" + rec.getDifficulte(), "NewsTopLine2");
        Label BudgetTxt = new Label("Budget :" + rec.getBudget(), "NewsTopLine2");
        Label DescriptionTxt = new Label("Description :" + rec.getDescription(), "NewsTopLine2");

        VilleDepartTxt.setUIID("NewsTopLine");
        VilleArriveeTxt.setUIID("NewsTopLine");
        DateDepartTxt.setUIID("NewsTopLine");
        DateRetourTxt.setUIID("NewsTopLine");
        ActiviteTxt.setUIID("NewsTopLine");
        DureeTxt.setUIID("NewsTopLine");
        DifficulteTxt.setUIID("NewsTopLine");
        BudgetTxt.setUIID("NewsTopLine");
        DescriptionTxt.setUIID("NewsTopLine");

        Button btnAnnuler = new Button("Retour");
        
        
        btnAnnuler.addActionListener(l -> {
            new ListeRandonneForm(res).show();
        });

        Label l1 = new Label();
        Label l2 = new Label();
        Label l3 = new Label();
        Label l4 = new Label();
        Label l5 = new Label();
           try {
            ecoEncodedImage=EncodedImage.create("/giphy.gif");
        } catch (IOException ex) {
            
        }
           img = URLImage.createToStorage(ecoEncodedImage,rec.getImage() , url +rec.getImage(), URLImage.RESIZE_SCALE_TO_FILL);
         img.scaled(900, 900);
            ScaleImageLabel fillLabel = new ScaleImageLabel(img);
                 imgViewer = new ImageViewer(img);
            imgViewer.setWidth(this.getWidth());
        
      Container c=new Container(new BoxLayout(BoxLayout.Y_AXIS));
      c.add(imgViewer);

        Container content = BoxLayout.encloseY(
                c,
                l1, l2,
                VilleDepartTxt,
                createLineSeparator(),
                VilleArriveeTxt,
                createLineSeparator(),
                DateDepartTxt,
                createLineSeparator(),
                DateRetourTxt,
                createLineSeparator(),
                ActiviteTxt,
                createLineSeparator(),
                DureeTxt,
                createLineSeparator(),
                DifficulteTxt,
                createLineSeparator(),
                BudgetTxt,
                createLineSeparator(),
                DescriptionTxt,
                createLineSeparator(),
                btnAnnuler
        );
        add(content);
        show();
        Button ss = new Button("Faire un screenshot");
          add(ss);
                ss.addActionListener(e1 -> {

                    Image screenshot = Image.createImage(getWidth(), getHeight());
                    revalidate();
                    setVisible(true);
                    paintComponent(screenshot.getGraphics(), true);

                    String imageFile = "file://C:/Users/PC//Desktop/v5/v5/src/com/mycompany/images/capturescreenshoot.png";

                    try (OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)) {
                        ImageIO.getImageIO().save(screenshot, os, ImageIO.FORMAT_PNG, 1);
                    } catch (IOException err) {
                        Log.e(err);
                    }
                });

      
    

    }


}
