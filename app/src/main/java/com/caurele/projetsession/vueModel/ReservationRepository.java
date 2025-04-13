package com.caurele.projetsession.vueModel;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.caurele.projetsession.model.DAO.ReservationDAO;
import com.caurele.projetsession.model.DAO.VoyageDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationRepository {
    private Context context;

    public ReservationRepository(Context context) {
        this.context = context;
    }

    public interface Callback {
        void onResult(List<ReservationVoyage> result);
    }

    public void getReservationsVoyages(Callback callback) {
        new Thread(()->{
            List<Reservation> reservations = ReservationDAO.lireReservations(context);
            List<Voyage> voyages = VoyageDAO.rechercherVoyages("","","",Double.MAX_VALUE);

            Map<Integer, Voyage> voyageMap = new HashMap<>();
            for (Voyage v : voyages) {
                voyageMap.put(v.getId_voyage(), v);
            }

            List<ReservationVoyage> liste = new ArrayList<>();
            for (Reservation r : reservations) {
                Voyage v = voyageMap.get(r.getFkIdVoyage());
                if (v != null) {
                    liste.add(new ReservationVoyage(r, v));
                }
            }

            new Handler(Looper.getMainLooper()).post(() -> callback.onResult(liste));
        }).start();
    }
}
