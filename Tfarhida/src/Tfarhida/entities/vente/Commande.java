/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.entities.vente;

/**
 *
 * @author Nour
 */
public class Commande {
    int id;
    int produit_id;
    int panier_id;
    int quantite_produit;
    Double prixcommande;

    public Commande() {
    }

    public Commande( int produit_id, int panier_id, int quantite_produit, Double prixcommande) {
      
        this.produit_id = produit_id;
        this.panier_id = panier_id;
        this.quantite_produit = quantite_produit;
        this.prixcommande = prixcommande;
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

    public int getPanier_id() {
        return panier_id;
    }

    public void setPanier_id(int panier_id) {
        this.panier_id = panier_id;
    }

    public int getQuantite_produit() {
        return quantite_produit;
    }

    public void setQuantite_produit(int quantite_produit) {
        this.quantite_produit = quantite_produit;
    }

    public Double getPrixcommande() {
        return prixcommande;
    }

    public void setPrixcommande(Double prixcommande) {
        this.prixcommande = prixcommande;
    }

    @Override
    public String toString() {
        return "Commande{" + "id=" + id + ", produit_id=" + produit_id + ", panier_id=" + panier_id + ", quantite_produit=" + quantite_produit + ", prixcommande=" + prixcommande + '}';
    }
    
    
    
}
