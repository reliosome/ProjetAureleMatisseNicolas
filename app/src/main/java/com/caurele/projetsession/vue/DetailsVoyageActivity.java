package com.caurele.projetsession.vue;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.caurele.projetsession.R;
import com.caurele.projetsession.vueModel.ClientVueModel;
import com.caurele.projetsession.vueModel.ReservationVueModel;
import com.caurele.projetsession.vueModel.Voyage;

import com.caurele.projetsession.vueModel.VoyageVueModel;

public class DetailsVoyageActivity extends AppCompatActivity {

    private TextView titre, destination, description, type, duree, prix, placesDispo, prixTotal;
    private ImageView image;
    private Spinner dateSpinner;
    private EditText nombrePlaces;
    private Button reserver;

    private Voyage voyage;
    private Voyage.Trip[] trips;
    private VoyageVueModel voyageVueModel;
    private ReservationVueModel reservationVueModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_voyage);

        titre = findViewById(R.id.titreVoyage);
        destination = findViewById(R.id.destinationVoyage);
        description = findViewById(R.id.descriptionVoyage);
        type = findViewById(R.id.typeVoyage);
        duree = findViewById(R.id.dureeVoyage);
        prix = findViewById(R.id.prixVoyage);
        placesDispo = findViewById(R.id.placesDisponibles);
        prixTotal = findViewById(R.id.prixTotal);
        image = findViewById(R.id.imageVoyage);
        dateSpinner = findViewById(R.id.spinnerDate);
        nombrePlaces = findViewById(R.id.editNombrePlaces);
        reserver = findViewById(R.id.btnReserver);

        voyage = (Voyage) getIntent().getSerializableExtra("voyage");

        reservationVueModel = new ReservationVueModel(this);
        voyageVueModel = new ViewModelProvider(this).get(VoyageVueModel.class);

        if (voyage == null) return;

        titre.setText(voyage.getNom_voyage());
        destination.setText(voyage.getDestination());
        description.setText(voyage.getDescription());
        type.setText(voyage.getType_de_voyage());
        duree.setText(voyage.getDuree_jours() + " jours");
        prix.setText(String.format("%.2f $", voyage.getPrix()));

        com.squareup.picasso.Picasso.get()
                .load(voyage.getImage_url())
                .into(image);

        trips = voyage.getTrips();
        String[] dates = new String[trips.length];

        for (int i = 0; i < trips.length; i++) {
            dates[i] = trips[i].date;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSpinner.setAdapter(adapter);

        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updatePlaces(trips[position].nb_places_disponibles);
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        reserver.setOnClickListener(v -> {
            int selectedIndex = dateSpinner.getSelectedItemPosition();
            int placesRestantes = trips[selectedIndex].nb_places_disponibles;

            String nbStr = nombrePlaces.getText().toString().trim();
            if (nbStr.isEmpty()) {
                Toast.makeText(this, "Entrez le nombre de places", Toast.LENGTH_SHORT).show();
                return;
            }

            int nbDemandes = Integer.parseInt(nbStr);
            if (nbDemandes <= 0) {
                Toast.makeText(this, "Le nombre doit etre superieur a 0", Toast.LENGTH_SHORT).show();
                return;
            }

            if (nbDemandes > placesRestantes) {
                Toast.makeText(this, "Pas assez de places disponibles ", Toast.LENGTH_LONG).show();
            } else {
                double total = voyage.getPrix() * nbDemandes;
                prixTotal.setText("Prix total : " + String.format("%.2f $", total));
                updatePlaces(placesRestantes - nbDemandes);

                // Mets à jour le nombre de places
                trips[selectedIndex].nb_places_disponibles -= nbDemandes;

                reservationVueModel.saveReservation(nbDemandes, voyage.getId_voyage(), ClientVueModel.idClientActuel);

                Voyage voyageUpdated = new Voyage(voyage.getId_voyage(), voyage.getNom_voyage(),
                        voyage.getDescription(), voyage.getPrix(),
                        voyage.getDestination(), voyage.getImage_url(), voyage.getDuree_jours(),
                        trips, // Tout demeure pareil sauf le nb de place dans le trip spécifique
                        voyage.getType_de_voyage(),
                        voyage.getActivites_incluses());

                voyageVueModel.modifierVoyage(voyageUpdated);

                Toast.makeText(this, "Reservation confirmee", Toast.LENGTH_SHORT).show();

                finish();
            }
        });


        updatePlaces(trips[0].nb_places_disponibles);
    }

    private void updatePlaces(int nbPlaces) {
        placesDispo.setText("Places disponibles : " + nbPlaces);
        prixTotal.setText("");
        reserver.setEnabled(nbPlaces > 0);
    }
}