/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entites;

import com.codename1.ui.TextField;



/**
 *
 * @author yassin
 */

public class ReservationRandonne {
    
    private int id;
    private int numreservation;
    private String datereservation;
    private String observation;
    private double montant;
     private int nombrepersonne;
    private String etat;
    private int idr;

    public ReservationRandonne() {
    }

    public int getIdr() {
        return idr;
    }

    public void setIdr(int idr) {
        this.idr = idr;
    }

    public ReservationRandonne(int numreservation, String datereservation, String observation, double montant, int nombrepersonne, String etat, int idr) {
        this.numreservation = numreservation;
        this.datereservation = datereservation;
        this.observation = observation;
        this.montant = montant;
        this.nombrepersonne = nombrepersonne;
        this.etat = etat;
        this.idr = idr;
    }

    public ReservationRandonne(int numreservation, String datereservation, String observation, double montant, int nombrepersonne, String etat) {
        this.numreservation = numreservation;
        this.datereservation = datereservation;
        this.observation = observation;
        this.montant = montant;
        this.nombrepersonne = nombrepersonne;
        this.etat = etat;
    }

    public ReservationRandonne(int id, int numreservation, String datereservation, String observation, double montant, int nombrepersonne, String etat) {
        this.id = id;
        this.numreservation = numreservation;
        this.datereservation = datereservation;
        this.observation = observation;
        this.montant = montant;
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
    public String toString() {
        return "ReservationRandonne{" + "id=" + id + ", numreservation=" + numreservation + ", datereservation=" + datereservation + ", observation=" + observation + ", montant=" + montant + ", nombrepersonne=" + nombrepersonne + ", etat=" + etat + '}';
    }

  
}