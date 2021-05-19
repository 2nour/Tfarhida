/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entites.ReservationRandonne;
import com.mycompany.services.ServiceReservationRandonne;

/**
 *
 * @author yassin
 */
public class ModifierReservationForm extends BaseForm {

    Form current;

    public ModifierReservationForm(Resources res, ReservationRandonne rec) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("container");
        setTitle("Ajouter Reservation");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        //TextField Sujetrec = new TextField(rec.getSujetRec(), "Sujet", 20, TextField.ANY);

//        TextField Numero = new TextField(String.valueOf(rec.getNumreservation()), "Description", 20, TextField.ANY);
//        TextField Date = new TextField(String.valueOf(rec.getDatereservation()), "Sujet", 20, TextField.ANY);
        
        TextField Observation = new TextField(String.valueOf(rec.getObservation()), "Sujet", 20, TextField.ANY);

        TextField Montant = new TextField(String.valueOf(rec.getMontant()), "Sujet", 20, TextField.ANY);
        TextField Nombre = new TextField(String.valueOf(rec.getNombrepersonne()), "Sujet", 20, TextField.ANY);
//        TextField Etat = new TextField(String.valueOf(rec.getEtat()), "Sujet", 20, TextField.ANY);

//        Numero.setUIID("NewsTopLine");
//        Numero.setSingleLineTextArea(true);
//
//        Date.setUIID("NewsTopLine");
//        Date.setSingleLineTextArea(true);


        Observation.setUIID("NewsTopLine");
        Observation.setSingleLineTextArea(true);

        Montant.setUIID("NewsTopLine");
        Montant.setSingleLineTextArea(true);

        Nombre.setUIID("NewsTopLine");
        Nombre.setSingleLineTextArea(true);

//        Etat.setUIID("NewsTopLine");
//        Etat.setSingleLineTextArea(true);
        Button btnModifier = new Button("Modifier");

        //Evenet onClick btnModifier
        btnModifier.addPointerPressedListener(l -> {
            // rec.setSujetRec(Sujetrec.getText());

            rec.setMontant(Double.parseDouble(Montant.getText()));
            rec.setNombrepersonne(Integer.parseInt(Nombre.getText()));
            rec.setObservation(Observation.getText());

            if (ServiceReservationRandonne.getInstance().ModifierReservation(rec)) {
                new ListeReservationForm(res).show();
            }

        });

        Button btnAnnuler = new Button("Annuler");
        btnAnnuler.addActionListener(l -> {
            new ListeReservationForm(res).show();
        });

        Label l1 = new Label();
        Label l2 = new Label();
        Label l3 = new Label();
        Label l4 = new Label();
        Label l5 = new Label();

        Container content = BoxLayout.encloseY(
                l1, l2,
                createLineSeparator(),
                Observation,
                createLineSeparator(),
                Montant,
                createLineSeparator(),
                Nombre,
                createLineSeparator(),
                btnModifier,
                btnAnnuler
        );
        add(content);
        show();

    }

}
