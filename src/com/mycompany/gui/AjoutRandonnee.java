/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entites.Randonne;
import com.mycompany.entites.ReservationRandonne;
import com.mycompany.services.ServiceRandonne;
import com.mycompany.services.ServiceReservationRandonne;

/**
 *
 * @author PC
 */
public class AjoutRandonnee extends BaseForm {
    Form current;
    public  AjoutRandonnee(Resources res){
           super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("container");
        setTitle("Ajouter Reservation");
        getContentPane().setScrollVisible(false);

        tb.addSearchCommand(e -> {

        });
        Tabs swipe = new Tabs();
        Label s1 = new Label();
        Label s2 = new Label();
        addTab(swipe, s1, res.getImage("back-logo.jpeg"), "", "", res);

        /////////////////////////////////////////////////
        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });
        
        
         Component.setSameSize(radioContainer, s1, s2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("Mes Reservation", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton liste = RadioButton.createToggle("Autres", barGroup);
        liste.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Add Reservation", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        mesListes.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();
            //  ListReclamationForm a = new ListReclamationForm(res);
            //  a.show();
            refreshTheme();
            new ListeReservationForm(res).show();
        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, mesListes, liste, partage),
                FlowLayout.encloseBottom(arrow)
        ));

        partage.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(partage, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(liste, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        
        
        /////////////////////////////////////////////////
        TextField tfVilleDepart = new TextField("", "villedepart");
        tfVilleDepart.setUIID("textFieldBack");
        addStringValue("tfVilleDepart", tfVilleDepart);

        TextField tfVillearrivee = new TextField("", "Villearrivee");
        tfVillearrivee.setUIID("textFieldBack");
        addStringValue("tfVillearrivee", tfVillearrivee);
        
        TextField tfDateDepart = new TextField("", "Datedepart");
        tfDateDepart.setUIID("textFieldBack");
        addStringValue("tfDateDepart", tfDateDepart);
        
         TextField tfDateRetour = new TextField("", "Dateretourt");
        tfDateRetour.setUIID("textFieldBack");
        addStringValue("tfDateRetour", tfDateRetour);

        TextField tfActivite = new TextField("", "Activite");
        tfActivite.setUIID("textFieldBack");
        addStringValue("tfActivite", tfActivite);

        TextField tfDuree = new TextField("", "Duree");
        tfDuree.setUIID("textFieldBack");
        addStringValue("tfDuree", tfDuree);

        TextField tfDifficulte = new TextField("", "difficulte");
        tfDifficulte.setUIID("textFieldBack");
        addStringValue("tfDifficulte", tfDifficulte);

        TextField tfBudget = new TextField("", "budget");
        tfBudget.setUIID("textFieldBack");
        addStringValue("tfBudget", tfBudget);
        
        TextArea tfDescription = new TextField("", "description");
        tfDescription.setUIID("textFieldBack");
        addStringValue("tfDescription", tfDescription);

        Button btnAjouterRec = new Button("Ajouter");
        addStringValue("", btnAjouterRec);
        
        
         //On click button event 
        btnAjouterRec.addActionListener((e) -> {

            if (tfVilleDepart.getText().equals("")) {
                Dialog.show(" Champ Vide !!", "", "Annuler", "ok");
            } //            else if( tfNumreservation.getText().length() < 3) {
            //                Dialog.show("Tapez Plus de deux mots ! ", "", "Annuler", "ok");
            //            }
            else {
                try {
                    InfiniteProgress ip = new InfiniteProgress(); //Loading after insert Data
                    final Dialog iDialog = ip.showInfiniteBlocking();
                    // Randonne r =new Randonne(tfVilleDepart.getText(), tfVillearrivee.getText(),dated,dater,tfActivite.getText(),randonnePhoto.getText(),tfDescription.getText(),Integer.parseInt(tfDuree.getText()),tfDifficulte.getText(),Integer.parseInt(tfBudget.getText()));

                    //System.out.println("Data ==> " + r);

                  //  ServiceRandonne.getInstance().AddRandonne(r);
                    iDialog.dispose();
                    refreshTheme();

                    //be3ed ajout net3adaw lel affichage
                    new ListeReservationForm(res).show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        });

    }
 private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "FaddedLabel")).add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }

    private void addTab(Tabs swipe, Label spacer, Image image, String string, String text, Resources res) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if (image.getHeight() < size) {
            image = image.scaledHeight(size);
        }
        if (image.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            image = image.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }

        ScaleImageLabel imageScale = new ScaleImageLabel(image);
        imageScale.setUIID("Container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overLay = new Label("", "ImageOverLay");

        Container page1 = LayeredLayout.encloseIn(
                imageScale,
                overLay,
                BorderLayout.south(
                        BoxLayout.encloseY(
                                new SpanLabel(text, "LargeWhiteText"),
                                spacer
                        )
                )
        );

        swipe.addTab("", res.getImage("back-logo.jpeg"), page1);
    }

    public void bindButtonSelection(Button btn, Label l) {
        btn.addActionListener(e -> {
            if (btn.isSelected()) {
                updateArrowPosition(btn, l);
            }
        });
    }

    private void updateArrowPosition(Button btn, Label l) {
        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth() / 2 - l.getWidth() / 2); //b.getx()+ btn.getWidh()/ 2 - l.getWidth() / +
        l.getParent().repaint();
    }

        
        
        
    }
    
     
