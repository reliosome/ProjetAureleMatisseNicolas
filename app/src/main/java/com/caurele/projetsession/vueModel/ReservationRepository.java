package com.caurele.projetsession.vueModel;

import static com.caurele.projetsession.model.ReservationsBD.FKVOYAGE;
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

public class ReservationRepository {

    private ReservationsBD reservationsBD;
    private Context context;

    public ReservationRepository(Context context){
        reservationsBD = new ReservationsBD(context);
        this.context = context;
    }

    // Sauvegarder dans BD
    public void sauvegarderReservation(int nbPlaces, int idVoyage){
        SQLiteDatabase db = reservationsBD.getWritableDatabase();
        ContentValues valeurs = new ContentValues();
        valeurs.put(NBPLACES, nbPlaces);
        valeurs.put(FKVOYAGE, idVoyage);

        // Calculer le prix selon le voyage choisi et nb de places
        Voyage v = VoyageDAO.chercherVoyageParId(context, idVoyage);
        double prixParPlace = v.getPrix();
        double prixTotal = nbPlaces * prixParPlace;

        valeurs.put(PRIX, prixTotal);

        db.insertWithOnConflict(RESERVATION, null, valeurs, SQLiteDatabase.CONFLICT_REPLACE);

        db.close();
    }

    // Récupérer depuis BD
    /*
    public Reservation recupererValeurs(int id){
        SQLiteDatabase db = reservationsBD.getReadableDatabase();
        Cursor c = db.query(RESERVATION, new String[]{VALEUR},
                ID + "=?", new String[]{String.valueOf(id)},
                null,null,null);

        if(c != null && c.moveToFirst()){
            @SuppressLint("Range") int valeur = c.getInt(c.getColumnIndex(VALEUR));
            c.close();
            db.close();
            return valeur;
        }
        db.close();
        return null; // pas trouvé de valeur dans table
    }

     */

}
