package com.caurele.projetsession.vueModel;

import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.caurele.projetsession.model.UtilitaireJSON;
import com.caurele.projetsession.vue.AccueilActivity;
import com.caurele.projetsession.vue.ConnexionActivity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientVueModel extends ViewModel {

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

        for(int i=0; i< lesClients.length; i++){
            if(lesClients[i].existe(courriel)){
                user = lesClients[i];
            }
        }

        if(user != null){
            return user.getMdp().equals(motPasse);
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
