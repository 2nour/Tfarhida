package Tfarhida.entities;

import java.util.Date;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;


public class Reservation {
    private int id, chambre_id, user_id, nbrJour;
    private Date date;
    private String etats, nom, nomCh;
    private double totalPrix;
   
    public Reservation(){
        
    }

    public Reservation(int id, int chambre_id, int user_id, Date date, String etats, int nbrJour, double totalPrix) {
        this.id = id;
        this.chambre_id = chambre_id;
        this.user_id = user_id;
        this.date = date;
        this.etats = etats;
        this.nbrJour = nbrJour;
        this.totalPrix = totalPrix;
    }
    
     public Reservation(int id, int chambre_id, int user_id, Date date, String etats, int nbrJour, double totalPrix, String nom, String nomCh) {
        this.id = id;
        this.chambre_id = chambre_id;
        this.user_id = user_id;
        this.date = date;
        this.etats = etats;
        this.nbrJour = nbrJour;
        this.totalPrix = totalPrix;
        this.nom = nom;
        this.nomCh = nomCh;
    }
    
    public Reservation(int chambre_id, int user_id, Date date, String etats, int nbrJour, double totalPrix) {
         this(0, chambre_id, user_id, date, etats, nbrJour, totalPrix, "", "");
    }

   

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    

    public int getNbrJour() {
        return nbrJour;
    }

    public void setNbrJour(int nbrJour) {
        this.nbrJour = nbrJour;
    }

    public double getTotalPrix() {
        return totalPrix;
    }

    public void setTotalPrix(double totalPrix) {
        this.totalPrix = totalPrix;
    }

  

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEtats() {
        return etats;
    }

    public void setEtats(String etats) {
        this.etats = etats;
    }

    public int getChambre_id() {
        return chambre_id;
    }

    public void setChambre_id(int chambre_id) {
        this.chambre_id = chambre_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Reservation{" + "id=" + id + ", chambre_id=" + chambre_id + ", user_id=" + user_id + ", date=" + date + ", etats=" + etats + '}';
    }

   

    
    
    
}
