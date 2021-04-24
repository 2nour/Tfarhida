
package Tfarhida.entities;

import java.util.Date;


public class Organisation {
    
    private int id , nbrjours ;
    private String commentaire , activite, nbrpersonne,Lieu;
    private String  etat ;
    private Date date;
    private int note ;

    public Organisation(){};
    
    public Organisation(int id, int nbrjours, String nbrpersonne, Date date, String activite, String commentaire,String Lieu,String etat) {
        this.id = id;
        this.nbrjours = nbrjours;
        this.commentaire = commentaire;
this.etat=etat;
        this.activite = activite;
        this.nbrpersonne = nbrpersonne;
        this.Lieu = Lieu;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getNbrjours() {
        return nbrjours;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public String getEtat() {
        return etat;
    }

    public String getActivite() {
        return activite;
    }

    public String getNbrpersonne() {
        return nbrpersonne;
    }

    public Date getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNbrjours(int nbrjours) {
        this.nbrjours = nbrjours;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }

    public void setNbrpersonne(String nbrpersonne) {
        this.nbrpersonne = nbrpersonne;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLieu() {
        return Lieu;
    }

    public void setLieu(String Lieu) {
        this.Lieu = Lieu;
    }

 
    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Organisation{" + "id=" + id + ", nbrjours=" + nbrjours + ", commentaire=" + commentaire + ", activite=" + activite + ", nbrpersonne=" + nbrpersonne + ", Lieu=" + Lieu + ", etat=" + etat + ", date=" + date + ", note=" + note + '}';
    }
 

} 
 
