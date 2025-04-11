package com.caurele.projetsession.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ReservationsBD extends SQLiteOpenHelper {
    public static final String RESERVATION = "reservation";
    public static final String ID = "id_Reservation";
    public static final String NBPLACES = "nombre_Places";
    public static final String FKVOYAGE = "id_Voyage";
    public static final String PRIX = "prix";
    public ReservationsBD(Context context) {
        super(context, "ReservationsBD", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String requete = String.format("create table %s (%s int primary key, %s int, %s int, %s double)",
                RESERVATION,
                ID,
                NBPLACES,
                FKVOYAGE,
                PRIX);
        db.execSQL(requete);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO
    }

}
