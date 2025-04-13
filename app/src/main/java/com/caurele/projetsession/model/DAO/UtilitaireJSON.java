package com.caurele.projetsession.model.DAO;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.caurele.projetsession.vueModel.Client;
import com.caurele.projetsession.vueModel.Voyage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class UtilitaireJSON {
    // partir le serveur JSON :
    // npx json-server .\app\src\main\assets\voyages.json port 3000
    private final String URL_POINT_ENTREE = "http://10.0.2.2:3000";
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

    public boolean modifierVoyage(Voyage voyageModif){
        String voyageJSON = voyageModif.toString();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody corps = RequestBody.create(voyageJSON, JSON);

        Request requete = new Request.Builder().url(URL_POINT_ENTREE+CLIENTS)
                .put(corps)
                .build();
        try {
            Response response = okHttpClient.newCall(requete).execute();
            return response.code() == 200 || response.code() == 204;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Voyage> rechercherVoyages(String destination, String type,
                                          String date, Double prixMax){
        List<Voyage> voyages = lireVoyagesDepuisJson();
        List<Voyage> resultats = new ArrayList<>();

        for (Voyage v : voyages) {
            boolean match = true;

            if (!destination.isEmpty() && !stripAccents(v.getDestination().toLowerCase()).contains(stripAccents(destination.toLowerCase())))
                match = false;

            if (!type.isEmpty() && !v.getType_de_voyage().toLowerCase().contains(type.toLowerCase()))
                match = false;

            if (!date.isEmpty()) {
                boolean dateOk = false;
                for (Voyage.Trip trip : v.getTrips()) {
                    if (trip.date.contains(date)) {
                        dateOk = true;
                        break;
                    }
                }
                if (!dateOk) match = false;
            }

            if (prixMax != Double.MAX_VALUE && v.getPrix() > prixMax) match = false;

            if (match) resultats.add(v);
        }

        return resultats;
    }

    public Voyage chercherVoyageParId(int idVoyage) {
        List<Voyage> voyages = lireVoyagesDepuisJson();
        Voyage resultat = null;

        for (Voyage v : voyages) {
            if(v.getId_voyage() == (idVoyage)){
                resultat = v;
                break;
            }
        }

        return resultat;
    }

    private List<Voyage> lireVoyagesDepuisJson() {
        List<Voyage> voyages = new ArrayList<>();
        (new Thread(()->{
            try {

                String json = getVoyages();

                JSONArray voyageArray = new JSONArray(json);

                for (int i = 0; i < voyageArray.length(); i++) {
                    JSONObject obj = voyageArray.getJSONObject(i);
                    Voyage voyage = new Voyage();

                    voyage.setId_voyage(Integer.parseInt(obj.getString("id")));
                    voyage.setNom_voyage(obj.getString("nom_voyage"));
                    voyage.setDescription(obj.getString("description"));
                    voyage.setPrix(obj.getDouble("prix"));
                    voyage.setDestination(obj.getString("destination"));
                    voyage.setImage_url(obj.getString("image_url"));
                    voyage.setDuree_jours(obj.getInt("duree_jours"));
                    voyage.setType_de_voyage(obj.getString("type_de_voyage"));
                    voyage.setActivites_incluses(obj.getString("activites_incluses"));

                    JSONArray tripsArray = obj.getJSONArray("trips");
                    Voyage.Trip[] trips = new Voyage.Trip[tripsArray.length()];
                    for (int j = 0; j < tripsArray.length(); j++) {
                        JSONObject tripObj = tripsArray.getJSONObject(j);
                        Voyage.Trip trip = new Voyage().new Trip(
                                tripObj.getString("date"),
                                tripObj.getInt("nb_places_disponibles")
                        );
                        trips[j] = trip;
                    }

                    voyage.setTrips(trips);
                    voyages.add(voyage);
                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        })).start();

        return voyages;
    }

    private String stripAccents(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}
