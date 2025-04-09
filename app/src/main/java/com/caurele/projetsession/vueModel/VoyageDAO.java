package com.caurele.projetsession.vueModel;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class VoyageDAO {
    private static String stripAccents(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }


    public static List<Voyage> rechercherVoyages(Context context, String destination, String type,
                                                 String date, double prixMax) {
        List<Voyage> voyages = lireVoyagesDepuisJson(context);
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

    private static List<Voyage> lireVoyagesDepuisJson(Context context) {
        List<Voyage> voyages = new ArrayList<>();

        try {
            Log.d("DEBUG_JSON", ">> Ouverture fichier JSON...");
            InputStream is = context.getAssets().open("voyages.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, StandardCharsets.UTF_8);
            Log.d("DEBUG_JSON", ">> Contenu JSON brut : " + json);

            JSONObject root = new JSONObject(json);
            JSONArray voyageArray = root.getJSONArray("voyages");

            Log.d("DEBUG_JSON", ">> Nombre de voyages dans le JSON : " + voyageArray.length());

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
                Log.d("DEBUG_JSON", ">> Voyage chargé : " + voyage.getNom_voyage());
            }

        } catch (Exception e) {
            Log.e("DEBUG_JSON", "ERREUR DE LECTURE JSON", e);
        }

        return voyages;
    }
}