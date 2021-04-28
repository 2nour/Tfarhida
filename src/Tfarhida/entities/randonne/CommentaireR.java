
package Tfarhida.entities.randonne;

import java.util.Objects;

/**
 *
 * @author PC
 */
public class CommentaireR {
    private int id;
    private String contenue;
    private String datedecommentaire;
    private Randonnee r;
    private User u;

    public CommentaireR(String contenue, String datedecommentaire, Randonnee r, User u) {
        this.contenue = contenue;
        this.datedecommentaire = datedecommentaire;
        this.r = r;
        this.u = u;
    }

    public CommentaireR(int id, String contenue, String datedecommentaire, Randonnee r, User u) {
        this.id = id;
        this.contenue = contenue;
        this.datedecommentaire = datedecommentaire;
        this.r = r;
        this.u = u;
    }

    public Randonnee getR() {
        return r;
    }

    public void setR(Randonnee r) {
        this.r = r;
    }

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }

    public CommentaireR() {
    }

    public CommentaireR(String contenue, String datedecommentaire) {
        this.contenue = contenue;
        this.datedecommentaire = datedecommentaire;
    }

    public String getContenue() {
        return contenue;
    }

    public void setContenue(String contenue) {
        this.contenue = contenue;
    }

    public String getDatedecommentaire() {
        return datedecommentaire;
    }

    public void setDatedecommentaire(String datedecommentaire) {
        this.datedecommentaire = datedecommentaire;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.contenue);
        hash = 37 * hash + Objects.hashCode(this.datedecommentaire);
        return hash;
    }

    @Override
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
        final CommentaireR other = (CommentaireR) obj;
        if (!Objects.equals(this.contenue, other.contenue)) {
            return false;
        }
        if (!Objects.equals(this.datedecommentaire, other.datedecommentaire)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CommentaireR{" + "contenue=" + contenue + ", datedecommentaire=" + datedecommentaire + '}';
    }
    
    
    
}
