package com.caurele.projetsession.model.DAO;

import static com.caurele.projetsession.model.ReservationsBD.CONFIRME;
import static com.caurele.projetsession.model.ReservationsBD.FKCLIENT;
import static com.caurele.projetsession.model.ReservationsBD.FKVOYAGE;
import static com.caurele.projetsession.model.ReservationsBD.ID;
import static com.caurele.projetsession.model.ReservationsBD.NBPLACES;
import static com.caurele.projetsession.model.ReservationsBD.PRIX;
import static com.caurele.projetsession.model.ReservationsBD.RESERVATION;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.caurele.projetsession.model.ReservationsBD;
import com.caurele.projetsession.vueModel.Reservation;

import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    @SuppressLint("Range")
    public static List<Reservation> lireReservations(Context context){
        List<Reservation> rese = new ArrayList<>();

        ReservationsBD reservationsBD = new ReservationsBD(context);
        SQLiteDatabase db = reservationsBD.getReadableDatabase();

        Cursor c = db.query(RESERVATION,
                new String[]{ID, NBPLACES, FKVOYAGE, FKCLIENT, PRIX, CONFIRME},
                null,
                null,null, null, null);

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

        return rese;
    }
}
