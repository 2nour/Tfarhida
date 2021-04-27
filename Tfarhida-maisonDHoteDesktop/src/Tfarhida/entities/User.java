package Tfarhida.entities;


public class User {
    
    private int id;
    private String nom;

    public User(){
        
    }
   
    public User(int id, String nom) {
        this.id = id;
        this.nom = nom;
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

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", nom=" + nom + '}';
    }
    
    
    
}
