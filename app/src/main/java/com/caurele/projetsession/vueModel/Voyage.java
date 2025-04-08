package com.caurele.projetsession.vueModel;

public class Voyage {
    private int id_voyage;
    private String nom_voyage;
    private String description;
    private double prix;
    private String destination;
    private String image_url;
    private int duree_jours;
    public class Trip {
        public String date;
        public int nb_places_disponibles;
        public Trip(){}
        public Trip(String date, int nb_places_disponibles) {
            this.date = date;
            this.nb_places_disponibles = nb_places_disponibles;
        }
    }
    private Trip[] trips;
    private String type_de_voyage;
    private String activites_incluses;

    public Voyage(){}
    public Voyage(int id_voyage, String nom_voyage, String description, double prix,
                  String destination, String image_url, int duree_jours, Trip[] trips,
                  String type_de_voyage, String activites_incluses) {
        this.id_voyage = id_voyage;
        this.nom_voyage = nom_voyage;
        this.description = description;
        this.prix = prix;
        this.destination = destination;
        this.image_url = image_url;
        this.duree_jours = duree_jours;
        this.trips = trips;
        this.type_de_voyage = type_de_voyage;
        this.activites_incluses = activites_incluses;
    }

    public int getId_voyage() {
        return id_voyage;
    }

    public void setId_voyage(int id_voyage) {
        this.id_voyage = id_voyage;
    }

    public String getNom_voyage() {
        return nom_voyage;
    }

    public void setNom_voyage(String nom_voyage) {
        this.nom_voyage = nom_voyage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getDuree_jours() {
        return duree_jours;
    }

    public void setDuree_jours(int duree_jours) {
        this.duree_jours = duree_jours;
    }

    public Trip[] getTrips() {
        return trips;
    }

    public void setTrips(Trip[] trips) {
        this.trips = trips;
    }

    public String getType_de_voyage() {
        return type_de_voyage;
    }

    public void setType_de_voyage(String type_de_voyage) {
        this.type_de_voyage = type_de_voyage;
    }

    public String getActivites_incluses() {
        return activites_incluses;
    }

    public void setActivites_incluses(String activites_incluses) {
        this.activites_incluses = activites_incluses;
    }
}
