/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.entities.vente;

import java.sql.Date;

/**
 *
 * @author Nour
 */
public class Comment {
    
    int id;
    int produit_id;
    String contenue; 
    Date datedecommentaire;
    int user_id;
    String sentiment;
    

    public Comment() {
    }

  
    public Comment(int produit_id, String contenue, Date datedecommentaire, int user_id ) {
       
        this.produit_id = produit_id;
        this.contenue = contenue;
        this.datedecommentaire = datedecommentaire;
        this.user_id = user_id;
        
       
    }

   
    
    
    
    
    
    

   

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduit_id() {
        return produit_id;
    }

    public void setProduit_id(int produit_id) {
        this.produit_id = produit_id;
    }

    public String getContenue() {
        return contenue;
    }

    public void setContenue(String contenue) {
        this.contenue = contenue;
    }

    public Date getDatedecommentaire() {
        return datedecommentaire;
    }

    public void setDatedecommentaire(Date datedecommentaire) {
        this.datedecommentaire = datedecommentaire;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Commentarie{" + "id=" + id + ", produit_id=" + produit_id + ", contenue=" + contenue + ", datedecommentaire=" + datedecommentaire + ", user_id=" + user_id + '}';
    }
    
    
    
    
    
}
