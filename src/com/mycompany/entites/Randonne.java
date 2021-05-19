/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entites;

/**
 *
 * @author PC
 */

import java.util.Date;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Randonne {
    
 private int id;   
 private  String villedepart;
 private  String villearrivee;
 private  String datedepart;
 private  String dateretour;
 private  String activite;
 private  String description;
 private  String image;
 private  int duree;
 private  String difficulte;
 private  int budget;

    public Randonne() {
    }

    public Randonne(String villedepart, String villearrivee, String datedepart, String dateretour, String activite, String description, String image, int duree, String difficulte, int budget) {
        this.villedepart = villedepart;
        this.villearrivee = villearrivee;
        this.datedepart = datedepart;
        this.dateretour = dateretour;
        this.activite = activite;
        this.description = description;
        this.image = image;
        this.duree = duree;
        this.difficulte = difficulte;
        this.budget = budget;
    }

    public Randonne(int id, String villedepart, String villearrivee, String datedepart, String dateretour, String activite, String description, String image, int duree, String difficulte, int budget) {
        this.id = id;
        this.villedepart = villedepart;
        this.villearrivee = villearrivee;
        this.datedepart = datedepart;
        this.dateretour = dateretour;
        this.activite = activite;
        this.description = description;
        this.image = image;
        this.duree = duree;
        this.difficulte = difficulte;
        this.budget = budget;
    }

    public int getId() {
        return id;
    }

    public String getVilledepart() {
        return villedepart;
    }

    public String getVillearrivee() {
        return villearrivee;
    }

    public String getDatedepart() {
        return datedepart;
    }

    public String getDateretour() {
        return dateretour;
    }

    public String getActivite() {
        return activite;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public int getDuree() {
        return duree;
    }

    public String getDifficulte() {
        return difficulte;
    }

    public int getBudget() {
        return budget;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVilledepart(String villedepart) {
        this.villedepart = villedepart;
    }

    public void setVillearrivee(String villearrivee) {
        this.villearrivee = villearrivee;
    }

    public void setDatedepart(String datedepart) {
        this.datedepart = datedepart;
    }

    public void setDateretour(String dateretour) {
        this.dateretour = dateretour;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public void setDifficulte(String difficulte) {
        this.difficulte = difficulte;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "Randonne{" + "id=" + id + ", villedepart=" + villedepart + ", villearrivee=" + villearrivee + ", datedepart=" + datedepart + ", dateretour=" + dateretour + ", activite=" + activite + ", description=" + description + ", image=" + image + ", duree=" + duree + ", difficulte=" + difficulte + ", budget=" + budget + '}';
    }

    
}

    

