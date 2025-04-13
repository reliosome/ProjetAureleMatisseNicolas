package com.caurele.projetsession.vueModel;

import static com.caurele.projetsession.model.ReservationsBD.CONFIRME;
import static com.caurele.projetsession.model.ReservationsBD.FKCLIENT;
import static com.caurele.projetsession.model.ReservationsBD.FKVOYAGE;
import static com.caurele.projetsession.model.ReservationsBD.ID;
import static com.caurele.projetsession.model.ReservationsBD.NBPLACES;
import static com.caurele.projetsession.model.ReservationsBD.PRIX;
import static com.caurele.projetsession.model.ReservationsBD.RESERVATION;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.caurele.projetsession.model.DAO.UtilitaireJSON;
import com.caurele.projetsession.model.DAO.VoyageDAO;
import com.caurele.projetsession.model.ReservationsBD;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ReservationVueModel extends ViewModel {
    private MutableLiveData<List<Reservation>> reservations = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private ReservationsBD reservationsBD;

    public ReservationVueModel(Context context){
        reservationsBD = new ReservationsBD(context);
    }
    public LiveData<List<Reservation>> getReservations(){return reservations;}

    // Sauvegarder dans BD
    public void saveReservation(int nbPlaces, int idVoyage, int idClient){
        new Thread(() -> {
            SQLiteDatabase db = reservationsBD.getWritableDatabase();
            ContentValues valeurs = new ContentValues();
            valeurs.put(NBPLACES, nbPlaces);
            valeurs.put(FKVOYAGE, idVoyage);
            valeurs.put(FKCLIENT, idClient);

            // Calculer le prix selon le voyage choisi et nb de places
            Voyage v = VoyageDAO.chercherVoyageParId(idVoyage);
            double prixParPlace = v.getPrix();
            double prixTotal = nbPlaces * prixParPlace;

            valeurs.put(PRIX, prixTotal);
            valeurs.put(CONFIRME, 1);

            db.insertWithOnConflict(RESERVATION, null, valeurs, SQLiteDatabase.CONFLICT_REPLACE);

            db.close();
        }).start();
    }

    // Recuperer depuis BD
    @SuppressLint("Range")
    public void obtenirReservations(int idClient){
        new Thread(()->{
            List<Reservation> rese = new ArrayList<>();

            SQLiteDatabase db = reservationsBD.getReadableDatabase();

            Cursor c = db.query(RESERVATION,
                    new String[]{ID, NBPLACES, FKVOYAGE, FKCLIENT, PRIX, CONFIRME},
                    FKCLIENT + "=?",
                    new String[]{String.valueOf(idClient)},
                    null, null, null);

            if (c.moveToFirst()) {
                do {
                    rese.add(new Reservation(
                            c.getInt(c.getColumnIndex(ID)),
                            c.getInt(c.getColumnIndex(NBPLACES)),
                            c.getDouble(c.getColumnIndex(PRIX)),
                            c.getInt(c.getColumnIndex(FKVOYAGE)),
                            c.getInt(c.getColumnIndex(FKCLIENT)),
                            c.getInt(c.getColumnIndex(CONFIRME))
                    ));
                } while (c.moveToNext());
            }

            c.close();
            db.close();

            reservations.postValue(rese);
        }).start();
    }

    // Annuler une reserve
    public void annulerReservation(int idReservation){
        new Thread(() -> {
            SQLiteDatabase db = reservationsBD.getWritableDatabase();
            Cursor c = db.rawQuery("SELECT " + NBPLACES + ", " + FKVOYAGE + " FROM " + RESERVATION + " WHERE " + ID + " = ?", new String[]{String.valueOf(idReservation)});
            if (c.moveToFirst()) {
                int nbPlaces = c.getInt(0);
                int idVoyage = c.getInt(1);

                ContentValues valeurs = new ContentValues();
                valeurs.put(ReservationsBD.CONFIRME, 0);
                db.updateWithOnConflict(ReservationsBD.RESERVATION, valeurs,
                        ReservationsBD.ID + "=?",
                        new String[]{String.valueOf(idReservation)}, SQLiteDatabase.CONFLICT_NONE);

                OkHttpClient client = new OkHttpClient();

                Request getRequest = new Request.Builder()
                        .url("http://10.0.2.2:3000/voyages/" + idVoyage)
                        .build();

                try (Response response = client.newCall(getRequest).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        String body = response.body().string();
                        JSONObject voyageJson = new JSONObject(body);
                        JSONArray tripsArray = voyageJson.getJSONArray("trips");


                        JSONObject trip = tripsArray.getJSONObject(0);
                        int nbActuel = trip.getInt("nb_places_disponibles");
                        trip.put("nb_places_disponibles", nbActuel + nbPlaces);


                        JSONObject patchObject = new JSONObject();
                        patchObject.put("trips", tripsArray);

                        RequestBody patchBody = RequestBody.create(
                                patchObject.toString(),
                                MediaType.parse("application/json")
                        );

                        Request patchRequest = new Request.Builder()
                                .url("http://10.0.2.2:3000/voyages/" + idVoyage)
                                .patch(patchBody)
                                .build();

                        client.newCall(patchRequest).execute();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
                c.close();
            db.close();
        }).start();
    }

}
