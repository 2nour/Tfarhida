/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.entities;

/**
 *
 * @author Nour
 */
public class Panier {
    int id;
    int user_id;
    int nbproduit;
    Double somme;

    public Panier() {
    }

    public Panier(int id, int user_id, int nbproduit, Double somme) {
        this.id = id;
        this.user_id = user_id;
        this.nbproduit = nbproduit;
        this.somme = somme;
    }

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getNbproduit() {
        return nbproduit;
    }

    public void setNbproduit(int nbproduit) {
        this.nbproduit = nbproduit;
    }

    public Double getSomme() {
        return somme;
    }

    public void setSomme(Double somme) {
        this.somme = somme;
    }

    @Override
    public String toString() {
        return "Panier{" + "id=" + id + ", user_id=" + user_id + ", nbproduit=" + nbproduit + ", somme=" + somme + '}';
    }
    
    
    
    
}
