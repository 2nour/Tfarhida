/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.entities.randonne;

import java.util.Date;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author PC
 */
public class Randonnee {
 private  SimpleIntegerProperty id;   
 private  SimpleStringProperty villedepart;
 private  SimpleStringProperty villearrivee;
 private  SimpleStringProperty datedepart;
 private  SimpleStringProperty dateretour;
 private  SimpleStringProperty activite;
 private  SimpleStringProperty description;
 private  SimpleStringProperty image;
 private  SimpleIntegerProperty duree;
 private  SimpleStringProperty difficulte;
 private  SimpleIntegerProperty budget;

    public Randonnee() {
    }

    public Randonnee(int id,String villedepart, String villearrivee, String datedepart, String daterretour, String activite, String description, String image, int duree, String difficulte, int budget) {
        this.id = new SimpleIntegerProperty(id);
        this.villedepart = new SimpleStringProperty(villedepart);
        this.villearrivee = new SimpleStringProperty(villearrivee);
        this.datedepart = new SimpleStringProperty(datedepart);
        this.dateretour = new SimpleStringProperty(daterretour);
        this.activite = new SimpleStringProperty(activite);
        this.description = new SimpleStringProperty(description);
        this.image = new SimpleStringProperty(image);
        this.duree = new SimpleIntegerProperty(duree);
        this.difficulte = new SimpleStringProperty(difficulte);
        this.budget = new SimpleIntegerProperty(budget);
    }
    public Randonnee(String villedepart, String villearrivee, String datedepart, String daterretour, String activite, String description, String image, int duree, String difficulte, int budget) {
        this.villedepart = new SimpleStringProperty(villedepart);
        this.villearrivee = new SimpleStringProperty(villearrivee);
        this.datedepart = new SimpleStringProperty(datedepart);
        this.dateretour = new SimpleStringProperty(daterretour);
        this.activite = new SimpleStringProperty(activite);
        this.description = new SimpleStringProperty(description);
        this.image = new SimpleStringProperty(image);
        this.duree = new SimpleIntegerProperty(duree);
        this.difficulte = new SimpleStringProperty(difficulte);
        this.budget = new SimpleIntegerProperty(budget);
    }
    
     public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public String getVilledepart() {
        return villedepart.get();
    }

    public void setVilledepart(String villedepart) {
        this.villedepart = new SimpleStringProperty(villedepart);
    }

    public String getVillearrivee() {
        return villearrivee.get();
    }

    public void setVillearrivee(String villearrivee) {
        this.villearrivee = new SimpleStringProperty(villearrivee);
    }

    public String getDatedepart() {
        return datedepart.get();
    }

    public void setDatedepart(String datedepart) {
        this.datedepart = new SimpleStringProperty(datedepart);
    }

    public String getDateretour() {
        return dateretour.get();
    }

    public void setDateretour(String daterretour) {
        this.dateretour = new SimpleStringProperty(daterretour);
    }

    public String getActivite() {
        return activite.get();
    }

    public void setActivite(String activite) {
        this.activite = new SimpleStringProperty(activite);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description = new SimpleStringProperty(description);
    }

    public String getImage() {
        return image.get();
    }

    public void setImage(String image) {
        this.image = new SimpleStringProperty(image);
    }

    public int getDuree() {
        return duree.get();
    }

    public void setDuree(int duree) {
        this.duree = new SimpleIntegerProperty(duree);
    }

    public String getDifficulte() {
        return difficulte.get();
    }

    public void setDifficulte(String difficulte) {
        this.difficulte = new SimpleStringProperty(difficulte);
    }

    public int getBudget() {
        return budget.get();
    }

    public void setBudget(int budget) {
        this.budget = new SimpleIntegerProperty(budget);
    }

    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.villedepart);
        hash = 97 * hash + Objects.hashCode(this.villearrivee);
        hash = 97 * hash + Objects.hashCode(this.datedepart);
        hash = 97 * hash + Objects.hashCode(this.dateretour);
        hash = 97 * hash + Objects.hashCode(this.activite);
        hash = 97 * hash + Objects.hashCode(this.description);
        hash = 97 * hash + Objects.hashCode(this.image);
        hash = 97 * hash + Objects.hashCode(this.difficulte);
        return hash;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Randonnee other = (Randonnee) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.duree != other.duree) {
            return false;
        }
        if (this.budget != other.budget) {
            return false;
        }
        if (!Objects.equals(this.villedepart, other.villedepart)) {
            return false;
        }
        if (!Objects.equals(this.villearrivee, other.villearrivee)) {
            return false;
        }
        if (!Objects.equals(this.activite, other.activite)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.image, other.image)) {
            return false;
        }
        if (!Objects.equals(this.difficulte, other.difficulte)) {
            return false;
        }
        if (!Objects.equals(this.datedepart, other.datedepart)) {
            return false;
        }
        if (!Objects.equals(this.dateretour, other.dateretour)) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "Randonnee{" + "id=" + id + ", villedepart=" + villedepart + ", villearrivee=" + villearrivee + ", datedepart=" + datedepart + ", daterretour=" + dateretour + ", activite=" + activite + ", description=" + description + ", image=" + image + ", duree=" + duree + ", difficulte=" + difficulte + ", budget=" + budget + '}';
    }

   
    
}
