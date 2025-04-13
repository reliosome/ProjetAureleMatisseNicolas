package com.caurele.projetsession.vue;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;


import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;



import com.caurele.projetsession.R;
import com.caurele.projetsession.vue.adaptateur.ReservationAdapter;
import com.caurele.projetsession.vueModel.ClientVueModel;
import com.caurele.projetsession.vueModel.HistoriqueViewModel;
import com.caurele.projetsession.vueModel.Reservation;
import com.caurele.projetsession.vueModel.ReservationVueModel;
import com.caurele.projetsession.vueModel.ReservationVoyage;
import com.caurele.projetsession.vueModel.Voyage;

import java.util.ArrayList;

public class HistoriqueActivity extends AppCompatActivity {

    private ListView listViewReservations;
    private ReservationAdapter adapter;
    private ReservationVueModel reservationVueModel;
    private HistoriqueViewModel historiqueViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_historique);

        listViewReservations = findViewById(R.id.listViewReservations);
        adapter = new ReservationAdapter(this, new ArrayList<>());
        listViewReservations.setAdapter(adapter);

        historiqueViewModel = new ViewModelProvider(this).get(HistoriqueViewModel.class);
        historiqueViewModel.getReservations().observe(this, reservations -> {
            adapter.setData(reservations);
        });

        historiqueViewModel.chargerReservations(this);
        historiqueViewModel = new ViewModelProvider(this).get(HistoriqueViewModel.class);
        reservationVueModel = new ReservationVueModel(this);


        reservationVueModel = new ReservationVueModel(this);
        chargerReservations();


        listViewReservations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReservationVoyage rv = (ReservationVoyage) parent.getItemAtPosition(position);
                Reservation reservation = rv.reservation;
                Voyage voyage = rv.voyage;

                if (reservation.isConfirme() == 1) {
                    new AlertDialog.Builder(HistoriqueActivity.this)
                            .setTitle("Annuler la réservation")
                            .setMessage("Voulez-vous vraiment annuler cette réservation ?")
                            .setPositiveButton("Oui", (dialog, which) -> {
                                reservationVueModel.annulerReservation(reservation.getId());
                                Toast.makeText(HistoriqueActivity.this, "Réservation annulée", Toast.LENGTH_SHORT).show();
                                historiqueViewModel.chargerReservations(HistoriqueActivity.this);
                            })

                            .setNegativeButton("Non", null)
                            .show();
                } else {
                    Toast.makeText(HistoriqueActivity.this, "Cette réservation est déjà annulée", Toast.LENGTH_SHORT).show();
                }
            }
        });



        Button btnRetour = findViewById(R.id.btnRetourHistorique);
        btnRetour.setOnClickListener(v -> finish());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void chargerReservations() {

            historiqueViewModel.getReservations().observe(this, reservations -> {
                if (adapter == null) {

                    adapter = new ReservationAdapter(this, reservations);

                    listViewReservations.setAdapter(adapter);
                } else {
                    adapter.setData(reservations);
                    adapter.notifyDataSetChanged();
                }
            });




        //ArrayList<Reservation> reservations = reservationVueModel.getReservations();

//        if (reservations != null && !reservations.isEmpty()) {
//            if (adapter == null) {
//                adapter = new ReservationAdapter(this, reservations);
//                listViewReservations.setAdapter(adapter);
//            } else {
//                adapter.clear();
//                adapter.addAll(reservations);
//                adapter.notifyDataSetChanged();
//            }
//        } else {
//            Toast.makeText(this, "Aucune reservation trouvee", Toast.LENGTH_SHORT).show();
//            listViewReservations.setAdapter(null);
//        }
    }

}