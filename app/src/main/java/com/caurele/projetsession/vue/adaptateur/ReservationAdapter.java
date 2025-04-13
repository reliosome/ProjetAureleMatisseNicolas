package com.caurele.projetsession.vue.adaptateur;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.caurele.projetsession.R;

import com.caurele.projetsession.vueModel.Reservation;
import com.caurele.projetsession.vueModel.ReservationVoyage;
import com.caurele.projetsession.vueModel.Voyage;

import java.util.List;

public class ReservationAdapter extends BaseAdapter {

    private List<ReservationVoyage> data;
    private Context context;

    public ReservationAdapter(Context context, List<ReservationVoyage> data) {
        this.context = context;
        this.data = data;
    }

    public void setData(List<ReservationVoyage> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() { return data.size(); }

    @Override
    public Object getItem(int position) { return data.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate layout et bind reservation + voyage
        ReservationVoyage rv = data.get(position);
        Reservation r = rv.reservation;
        Voyage v = rv.voyage;

        View view = LayoutInflater.from(context).inflate(R.layout.item_reservation, parent, false);

        ((TextView) view.findViewById(R.id.destinationReservation)).setText(v.getNom_voyage());
        ((TextView) view.findViewById(R.id.dateVoyageReservation)).setText(v.getTrips()[0].date);

        return view;
    }
/*
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

        //Voyage voy = VoyageDAO.chercherVoyageParId(reservation.getFkIdVoyage());
        Voyage voy = null;
        if(voy!=null)
            destination.setText(voy.getDestination());

        dateVoyage.setText("Places : " + reservation.getNbPlaces());

        montantPaye.setText(String.format("%.2f $", reservation.getPrix()));
        statut.setText(reservation.isConfirme() == 1 ? "Confirmée" : "Annulée");

        return view;
    }

 */
}
