package com.caurele.projetsession.vueModel;

import androidx.lifecycle.ViewModel;

import com.caurele.projetsession.model.DAO.UtilitaireJSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientVueModel extends ViewModel {

    public static int idClientActuel;
    public ClientVueModel(){}

    public boolean connexion(String txtCourriel, String txtMotPasse){
        UtilitaireJSON utilJson = new UtilitaireJSON();

        String clients = utilJson.getClients();

        String courriel = String.valueOf(txtCourriel);
        String motPasse = String.valueOf(txtMotPasse);

        ObjectMapper mapperClient = new ObjectMapper();
        Client[] lesClients;
        try {
            lesClients = mapperClient.readValue(clients, Client[].class);
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }

        Client user = null;

        for (Client lesClient : lesClients) {
            if (lesClient.existe(courriel)) {
                user = lesClient;
            }
        }

        if(user != null){
            if(user.getMdp().equals(motPasse)){
                idClientActuel = user.getId();
                return true;
            }
        }
        return false;
    }

    public boolean inscription(String nom, String prenom, String email, String mdp,
                               int age, String telephone, String adresse){
        UtilitaireJSON utilJson = new UtilitaireJSON();

        Client nouvClient = new Client(utilJson.getLastId()+1, nom, prenom, email, mdp, age, telephone, adresse);

        return utilJson.creerClient(nouvClient);
    }
}
