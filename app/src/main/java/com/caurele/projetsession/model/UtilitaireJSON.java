package com.caurele.projetsession.model;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;

public class UtilitaireJSON {
    private final String URL_POINT_ENTREE = "http://10.0.2.2:8082";
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
