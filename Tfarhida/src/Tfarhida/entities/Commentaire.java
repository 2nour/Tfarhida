package Tfarhida.entities;

import java.sql.Date;


public class Commentaire {
    private int id, maison_id, user_id;
    private String comment;
    private Date date;

    public Commentaire(){
        
    }
    public Commentaire(int id, int maison_id, int user_id, String comment, Date date) {
        this.id = id;
        this.maison_id = maison_id;
        this.user_id = user_id;
        this.comment = comment;
        this.date = date;
    }
    
    public Commentaire(int maison_id, int user_id, String comment, Date date) {
        this.maison_id = maison_id;
        this.user_id = user_id;
        this.comment = comment;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaison_id() {
        return maison_id;
    }

    public void setMaison_id(int maison_id) {
        this.maison_id = maison_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Commentaire{" + "id=" + id + ", maison_id=" + maison_id + ", user_id=" + user_id + ", comment=" + comment + ", date=" + date + '}';
    }
    
    
    
}
