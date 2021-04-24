/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.entities;

import javafx.scene.image.ImageView;

/**
 *
 * @author Nour
 */
public class Produit {
    int id;
    String nom;
    int quantite;
    String description;
    double prix;
    String marque;
    String image;
    ImageView img;

    public Produit() {
    };

    public Produit(String nom, int quantite, String description, double prix, String marque, String image) {
        this.nom = nom;
        this.quantite = quantite;
        this.description = description;
        this.prix = prix;
        this.marque = marque;
        this.image = image;
    }

    public Produit(int id, String nom, int quantite, String description, double prix, String marque, String image) {
        this.id = id;
        this.nom = nom;
        this.quantite = quantite;
        this.description = description;
        this.prix = prix;
        this.marque = marque;
        this.image = image;
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }
    
    

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Produit{nom=" + nom + ", quantite=" + quantite + ", description=" + description + ", prix=" + prix + ", marque=" + marque + ", image=" + image + '}';
    }
    
    
    
}
    
 