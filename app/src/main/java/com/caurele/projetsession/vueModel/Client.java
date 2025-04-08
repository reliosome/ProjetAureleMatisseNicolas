package com.caurele.projetsession.vueModel;

public class Client {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String mdp;
    private int age;
    private String telephone;
    private String adresse;

    public Client(){}
    public Client(int id_client, String nom, String prenom, String email, String mdp,
                  int age, String telephone, String adresse) {
        this.id = id_client;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.age = age;
        this.telephone = telephone;
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id + "," +
                "\"nom\":\"" + nom + "\"," +
                "\"prenom\":\"" + prenom + "\"," +
                "\"email\":\"" + email + "\"," +
                "\"mdp\":\"" + mdp + "\"," +
                "\"age\":" + age + "," +
                "\"telephone\":\"" + telephone + "\"," +
                "\"adresse\":\"" + adresse + "\"" +
                '}';
    }

    public boolean existe(String courriel){
        return courriel.equals(this.email);
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}
