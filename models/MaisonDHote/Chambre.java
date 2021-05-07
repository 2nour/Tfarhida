package Tfarhida.entities;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class Chambre {
    
    private int id, nbr_pers, idM;
    private double prix;
    String nom, type_lit, photo;
    private final BooleanProperty reserver = new SimpleBooleanProperty();
    ImageView img;
    
    public Chambre(){
        
    }

    public Chambre(int id, int idM, String nom, String type_lit, int nbr_pers, double prix, String photo) {
        this.id = id;
        this.idM = idM;
        this.nom = nom;
        this.type_lit = type_lit;
        this.nbr_pers = nbr_pers;
        this.prix = prix;
        this.photo = photo;
       reserver.setValue(false);
 
    }
    
    public Chambre(String nom, double prix, String type_lit ){
        this.nom = nom;
        this.prix = prix;
        this.type_lit = type_lit;
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public int getIdM() {
        return idM;
    }

    public void setIdM(int idM) {
        this.idM = idM;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbr_pers() {
        return nbr_pers;
    }

    public void setNbr_pers(int nbr_pers) {
        this.nbr_pers = nbr_pers;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType_lit() {
        return type_lit;
    }

    public void setType_lit(String type_lit) {
        this.type_lit = type_lit;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isReserver() {
        return reserver.getValue();
    }

    public void setReserver(boolean reserver) {
        this.reserver.setValue(reserver);
    }

  
    

    @Override
    public String toString() {
        return "Chambre{" + "id=" + id + ", nbr_pers=" + nbr_pers + ", prix=" + prix + ", nom=" + nom + ", type_lit=" + type_lit + ", photo=" + photo + '}';
    }
    
    
    
    
}
