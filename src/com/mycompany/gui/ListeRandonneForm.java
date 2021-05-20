/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.charts.util.ColorUtil;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
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
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC
 */
public class ListeRandonneForm extends BaseForm {

    Form current;
    List<Randonne> listRandonne = new ArrayList<>();

    public ListeRandonneForm(Resources res) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("container");
        setTitle("Ajouter reservation");
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
        RadioButton mesListes = RadioButton.createToggle("Liste Randonnee", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton liste = RadioButton.createToggle("Autres", barGroup);
        liste.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle(" Randonne", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        mesListes.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();

            //  ListReclamationForm a = new ListReclamationForm(res);
            //  a.show();
            refreshTheme();
            ArrayList<Randonne> fs = ServiceRandonne.getInstance().getAllrandonnee();
            listRandonne.addAll(fs);
            new ListeRandonneForm(res).show();
        });

        partage.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();

            //  ListReclamationForm a = new ListReclamationForm(res);
            //  a.show();
            refreshTheme();
            //new AddReservationRandonneForm(res).show();
        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, mesListes, liste, partage),
                FlowLayout.encloseBottom(arrow)
        ));

        partage.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(mesListes, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(liste, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });

        ArrayList<Randonne> list = ServiceRandonne.getInstance().getAllrandonnee();
        for (Randonne rec : list) {
            String urlImage = "back-logo.jpeg";
            Image placeHolder = Image.createImage(120, 90);
            EncodedImage enc = EncodedImage.createFromImage(placeHolder, false);
            URLImage urlim = URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);

            addButton(urlim, rec, res);

            ScaleImageLabel image = new ScaleImageLabel(urlim);
            Container containerImg = new Container();
            image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        }

        //Lenna
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

    private void addButton(Image img, Randonne rec, Resources res) {
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);

        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);

        Label VilleDepartTxt = new Label("VilleDepart :" + rec.getVilledepart(), "NewsTopLine2");
        Label VilleArriveeTxt = new Label("VilleArrivee :" + rec.getVillearrivee(), "NewsTopLine2");
        Label DateDepartTxt = new Label("DateDepart :" + rec.getDatedepart(), "NewsTopLine2");
      //  Label DateRetourTxt = new Label("DateRetour :" + rec.getDateretour(), "NewsTopLine2");
        Label ActiviteTxt = new Label("Activite :" + rec.getActivite(), "NewsTopLine2");
      //  Label DureeTxt = new Label("Duree :" + rec.getDuree(), "NewsTopLine2");
      //  Label DifficulteTxt = new Label("Difficulte :" + rec.getDifficulte(), "NewsTopLine2");
      //  Label BudgetTxt = new Label("Budget :" + rec.getBudget(), "NewsTopLine2");
       // Label DescriptionTxt = new Label("Description :" + rec.getDescription(), "NewsTopLine2");

        createLineSeparator();

       
        //Details button
        Label Deltails = new Label(" ");
        Deltails.setUIID("NewsTopLine");
        Style DeltailsStyle = new Style(Deltails.getUnselectedStyle());
        DeltailsStyle.setFgColor(0xf21f1f);
        FontImage DeltailsStyleIcon = FontImage.createMaterial(FontImage.MATERIAL_READ_MORE, DeltailsStyle);
        Deltails.setIcon(DeltailsStyleIcon);
        Deltails.setTextPosition(RIGHT);

        
        Label Reserver = new Label(" ");
        Reserver.setUIID("NewsTopLine");
        Style ReserverStyle = new Style(Reserver.getUnselectedStyle());
        ReserverStyle.setFgColor(0xf21f1f);
        FontImage ReserverStyleIcon = FontImage.createMaterial(FontImage.MATERIAL_ADD, ReserverStyle);
        Reserver.setIcon(ReserverStyleIcon);
        Reserver.setTextPosition(RIGHT);
       
        

        Deltails.addPointerPressedListener(l -> {
             System.out.println("Hello Details !");
               new DetailRandonneForm(res, rec).show();
        });
        
        Reserver.addPointerPressedListener(l -> {
             System.out.println("Hello Reserver !");
               new AddReservationRandonneForm(res).show();
        });

        cnt.add(BorderLayout.CENTER, BoxLayout.encloseY(
                BoxLayout.encloseX(VilleDepartTxt),
                BoxLayout.encloseX(VilleArriveeTxt),
                BoxLayout.encloseX(DateDepartTxt),
              //  BoxLayout.encloseX(DateRetourTxt),
                BoxLayout.encloseX(ActiviteTxt),
             //   BoxLayout.encloseX(DureeTxt),
              //  BoxLayout.encloseX(DifficulteTxt),
              //  BoxLayout.encloseX(BudgetTxt),
             //   BoxLayout.encloseX(DescriptionTxt),
                BoxLayout.encloseX(Deltails,Reserver)
        ));

        add(cnt);
    }

}
