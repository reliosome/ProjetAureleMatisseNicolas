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

import com.caurele.projetsession.model.DAO.VoyageDAO;
import com.caurele.projetsession.model.ReservationsBD;

import java.util.ArrayList;

public class ReservationRepository {

    private ReservationsBD reservationsBD;
    private Context context;

    public ReservationRepository(Context context){
        reservationsBD = new ReservationsBD(context);
        this.context = context;
    }

    // Sauvegarder dans BD
    public void sauvegarderReservation(int nbPlaces, int idVoyage, int idClient){
        SQLiteDatabase db = reservationsBD.getWritableDatabase();
        ContentValues valeurs = new ContentValues();
        valeurs.put(NBPLACES, nbPlaces);
        valeurs.put(FKVOYAGE, idVoyage);
        valeurs.put(FKCLIENT, idClient);

        // Calculer le prix selon le voyage choisi et nb de places
        Voyage v = VoyageDAO.chercherVoyageParId(context, idVoyage);
        double prixParPlace = v.getPrix();
        double prixTotal = nbPlaces * prixParPlace;

        valeurs.put(PRIX, prixTotal);

        db.insertWithOnConflict(RESERVATION, null, valeurs, SQLiteDatabase.CONFLICT_REPLACE);

        db.close();
    }

    // Récupérer depuis BD

    @SuppressLint("Range")
    public ArrayList<Reservation> recupererReservations(int idClient){
        ArrayList<Reservation> reservations = new ArrayList<>();

        SQLiteDatabase db = reservationsBD.getReadableDatabase();

        Cursor c = db.query(RESERVATION, new String[]{NBPLACES,FKVOYAGE,FKCLIENT,PRIX},
                            FKCLIENT + "=?", new String[]{String.valueOf(idClient)},
                            null, null, null);

        if(c.moveToFirst()){
            do{
                reservations.add(new Reservation(c.getInt(c.getColumnIndex(ID)),
                                                c.getInt(c.getColumnIndex(NBPLACES)),
                                                c.getDouble(c.getColumnIndex(PRIX)),
                                                c.getInt(c.getColumnIndex(FKVOYAGE)),
                                                idClient,
                                                c.getInt(c.getColumnIndex(CONFIRME))
                                                )
                                );
            }
            while(c.moveToNext());

            c.close();
            db.close();
            return reservations;
        }
        db.close();
        return null; // pas trouvé de réservations dans table
    }



}
