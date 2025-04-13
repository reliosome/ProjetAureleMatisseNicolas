package com.caurele.projetsession.vue.adaptateur;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.caurele.projetsession.R;

import com.caurele.projetsession.vue.HistoriqueActivity;
import com.caurele.projetsession.vueModel.Reservation;
import com.caurele.projetsession.model.DAO.VoyageDAO;
import com.caurele.projetsession.vueModel.Voyage;
import com.caurele.projetsession.vueModel.VoyageVueModel;

import java.util.ArrayList;
import java.util.List;

public class ReservationAdapter extends ArrayAdapter<Reservation> {

    private final Activity context;
    private List<Reservation> reservations = new ArrayList<>();
    private Voyage voyage;

    public ReservationAdapter(Activity context, List<Reservation> reservations) {
        super(context, R.layout.item_reservation, reservations);
        this.context = context;
    }

    public void setReservations(List<Reservation> rese) {
        this.reservations = rese;
        clear();
        addAll(rese);
        notifyDataSetChanged();
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

        Voyage voy = VoyageDAO.chercherVoyageParId(reservation.getFkIdVoyage());
        destination.setText(voy.getDestination());

        dateVoyage.setText("Places : " + reservation.getNbPlaces());

        montantPaye.setText(String.format("%.2f $", reservation.getPrix()));
        statut.setText(reservation.isConfirme() == 1 ? "Confirmée" : "Annulée");

        return view;
    }
}
