/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.entities.randonne;

import java.util.Objects;

/**
 *
 * @author PC
 */
public class Post_like {
    private int id;
    private Randonnee randonnee;
    private User user;

    public Post_like() {
    }

    public Post_like(int id, Randonnee randonnee, User user) {
        this.id = id;
        this.randonnee = randonnee;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Randonnee getRandonnee() {
        return randonnee;
    }

    public void setRandonnee(Randonnee randonnee) {
        this.randonnee = randonnee;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Post_like{" + "id=" + id + ", randonnee=" + randonnee + ", user=" + user + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.id;
        hash = 11 * hash + Objects.hashCode(this.randonnee);
        hash = 11 * hash + Objects.hashCode(this.user);
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
        final Post_like other = (Post_like) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.randonnee, other.randonnee)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        return true;
    }
    
    
    
}
