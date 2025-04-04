package com.caurele.projetsession.model;

import com.caurele.projetsession.vueModel.Client;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;

public class UtilitaireJSON {
    // partir le serveur JSON :
    // npx json-server voyages.json
    private final String URL_POINT_ENTREE = "http://10.0.2.2:3000"; // Ça se peut qu'il faille ajuster le port selon votre serveur JSON
    private final String CLIENTS = "/clients";
    private final String VOYAGES = "/voyages";
    private OkHttpClient okHttpClient;

    public UtilitaireJSON(){
        okHttpClient = new OkHttpClient();
    }
    /**
     * Renvoie la liste des clients en string json
     * */
    public String getClients() {
        Request requete = new Request.Builder().url(URL_POINT_ENTREE+CLIENTS).build();

        try {
            Response reponse = okHttpClient.newCall(requete).execute();
            ResponseBody reponseBody = reponse.body();
            if(reponseBody!=null)
                return reponseBody.string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
    /**
     *  Rajoute un client à voyages.json
     */
    public boolean creerClient(Client nouvClient){
        String clientJson = nouvClient.toString();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody corps = RequestBody.create(clientJson, JSON);

        Request requete = new Request.Builder().url(URL_POINT_ENTREE+CLIENTS)
                .post(corps)
                .build();
        try {
            Response response = okHttpClient.newCall(requete).execute();
            return response.code() == 201;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getLastId(){
        Request requete = new Request.Builder().url(URL_POINT_ENTREE+CLIENTS).build();

        try {
            Response reponse = okHttpClient.newCall(requete).execute();
            ResponseBody reponseBody = reponse.body();
            if(reponseBody!=null){
                String clients = reponseBody.string();
                int idx = clients.lastIndexOf("id");
                String dernierId = clients.substring(idx,idx+10);
                String intValue = dernierId.replaceAll("[^0-9]", "");
                int lastId = Integer.parseInt(intValue);
                return lastId;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return -1;
    }

    /**
     * Renvoie la liste des voyages en string json
     * */
    public String getVoyages() {
        Request requete = new Request.Builder().url(URL_POINT_ENTREE+VOYAGES).build();

        try {
            Response reponse = okHttpClient.newCall(requete).execute();
            ResponseBody reponseBody = reponse.body();
            if(reponseBody!=null)
                return reponseBody.string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
