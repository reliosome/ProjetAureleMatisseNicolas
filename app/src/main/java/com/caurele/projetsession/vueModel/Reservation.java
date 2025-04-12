package com.caurele.projetsession.vueModel;

import java.io.Serializable;

public class Reservation implements Serializable {

    private int id;
    private int nbPlaces;
    private double prix;
    private int fkIdVoyage;
    private int fkIdClient;
    private int confirme; // 0 ou 1

    public Reservation(){}

    public Reservation(int id, int nbPlaces, double prix, int fkIdVoyage, int fkIdClient, int confirme) {
        this.id = id;
        this.nbPlaces = nbPlaces;
        this.prix = prix;
        this.fkIdVoyage = fkIdVoyage;
        this.fkIdClient = fkIdClient;
        this.confirme = confirme;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getFkIdVoyage() {
        return fkIdVoyage;
    }

    public void setFkIdVoyage(int fkIdVoyage) {
        this.fkIdVoyage = fkIdVoyage;
    }

    public int getFkIdClient() {
        return fkIdClient;
    }

    public void setFkIdClient(int fkIdClient) {
        this.fkIdClient = fkIdClient;
    }

    public int isConfirme() {
        return confirme;
    }

    public void setConfirme(int confirme) {
        this.confirme = confirme;
    }
}
