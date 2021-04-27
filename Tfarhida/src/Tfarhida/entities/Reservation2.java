/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.entities;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author PC
 */
public class Reservation2 {
    private int id;
    private int numreservation;
    private String datereservation;
    private String observation;
    private double montant;
    private Randonnee randonnee;

    public Reservation2(int numreservation, String datereservation, String observation, double montant, Randonnee randonnee, int nombrepersonne, String etat) {
        this.numreservation = numreservation;
        this.datereservation = datereservation;
        this.observation = observation;
        this.montant = montant;
        this.randonnee = randonnee;
        this.nombrepersonne = nombrepersonne;
        this.etat = etat;
    }

    public Randonnee getRandonnee() {
        return randonnee;
    }

    public void setRandonnee(Randonnee randonnee) {
        this.randonnee = randonnee;
    }
    private int nombrepersonne;
    private String etat;

    public Reservation2() {
    }

    public Reservation2(int id, int numreservation, String datereservation, String observation, double montant, Randonnee randonnee, int nombrepersonne, String etat) {
        this.id = id;
        this.numreservation = numreservation;
        this.datereservation = datereservation;
        this.observation = observation;
        this.montant = montant;
        this.randonnee = randonnee;
        this.nombrepersonne = nombrepersonne;
        this.etat = etat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumreservation() {
        return numreservation;
    }

    public void setNumreservation(int numreservation) {
        this.numreservation = numreservation;
    }

    public String getDatereservation() {
        return datereservation;
    }

    public void setDatereservation(String datereservation) {
        this.datereservation = datereservation;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    

    public int getNombrepersonne() {
        return nombrepersonne;
    }

    public void setNombrepersonne(int nombrepersonne) {
        this.nombrepersonne = nombrepersonne;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + this.id;
        hash = 13 * hash + this.numreservation;
        hash = 13 * hash + Objects.hashCode(this.datereservation);
        hash = 13 * hash + Objects.hashCode(this.observation);
        hash = 13 * hash + (int) (Double.doubleToLongBits(this.montant) ^ (Double.doubleToLongBits(this.montant) >>> 32));
        hash = 13 * hash + Objects.hashCode(this.randonnee);
        hash = 13 * hash + this.nombrepersonne;
        hash = 13 * hash + Objects.hashCode(this.etat);
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
        final Reservation2 other = (Reservation2) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.numreservation != other.numreservation) {
            return false;
        }
        if (Double.doubleToLongBits(this.montant) != Double.doubleToLongBits(other.montant)) {
            return false;
        }
        if (this.nombrepersonne != other.nombrepersonne) {
            return false;
        }
        if (!Objects.equals(this.observation, other.observation)) {
            return false;
        }
        if (!Objects.equals(this.randonnee, other.randonnee)) {
            return false;
        }
        if (!Objects.equals(this.etat, other.etat)) {
            return false;
        }
        if (!Objects.equals(this.datereservation, other.datereservation)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Reservation{" + "id=" + id + ", numreservation=" + numreservation + ", datereservation=" + datereservation + ", observation=" + observation + ", montant=" + montant + ", randonnee=" + randonnee + ", nombrepersonne=" + nombrepersonne + ", etat=" + etat + '}';
    }
  
    
}
