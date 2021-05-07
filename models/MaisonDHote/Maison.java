package Tfarhida.entities;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class Maison {
    
    private int id, nbr_chambre, tel;
    private String nom, adresse, photo, description ;
    ImageView img;
   
    public Maison(){}

    public Maison(int id, String nom, String adresse, int nbr_chambre, int tel, String photo, String description) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.nbr_chambre = nbr_chambre;
        this.tel = tel;
        this.photo = photo;
        this.description = description;
       // this.reserver = new Button("Reserver");
        
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }
    
    public Maison(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbr_chambre() {
        return nbr_chambre;
    }

    public void setNbr_chambre(int nbr_chambre) {
        this.nbr_chambre = nbr_chambre;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    

    @Override
    public String toString() {
        return "Maison{" + "id=" + id + ", nbr_chambre=" + nbr_chambre + ", tel=" + tel + ", nom=" + nom + ", adresse=" + adresse + ", photo=" + photo + ", description=" + description + '}';
    }
    
    
}
