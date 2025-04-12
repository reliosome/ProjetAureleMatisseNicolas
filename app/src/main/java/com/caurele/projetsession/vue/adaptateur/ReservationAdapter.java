package com.caurele.projetsession.vue.adaptateur;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.caurele.projetsession.R;

import com.caurele.projetsession.vueModel.Reservation;
import com.caurele.projetsession.model.DAO.VoyageDAO;
import com.caurele.projetsession.vueModel.Voyage;
import java.util.List;

public class ReservationAdapter extends ArrayAdapter<Reservation> {

    private final Activity context;

    public ReservationAdapter(Activity context, List<Reservation> reservations) {
        super(context, R.layout.item_reservation, reservations);
        this.context = context;
    }

    public View getView(int position, View view, ViewGroup parent) {
        Reservation reservation = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_reservation, parent, false);
        }

        TextView destination = view.findViewById(R.id.destinationReservation);
        TextView dateVoyage = view.findViewById(R.id.dateVoyageReservation);
        TextView montantPaye = view.findViewById(R.id.montantPayeReservation);
        TextView statut = view.findViewById(R.id.statutReservation);


        Voyage voyage = VoyageDAO.chercherVoyageParId(context, reservation.getFkIdVoyage());
        destination.setText(voyage.getDestination());


        dateVoyage.setText("Places : " + reservation.getNbPlaces());

        montantPaye.setText(String.format("%.2f $", reservation.getPrix()));
        statut.setText(reservation.isConfirme() == 1 ? "Confirmée" : "Annulée");

        return view;
    }
}
