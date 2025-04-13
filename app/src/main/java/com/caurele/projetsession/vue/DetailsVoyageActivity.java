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

import org.json.JSONArray;
import org.json.JSONObject;
import com.caurele.projetsession.vueModel.Voyage.Trip;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

    @Override
    protected void onResume() {
        super.onResume();
        rafraichirVoyageDepuisServeur();
    }

    private void rafraichirVoyageDepuisServeur() {
        new Thread(() -> {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://10.0.2.2:3000/voyages/" + voyage.getId_voyage())
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String body = response.body().string();
                    JSONObject obj = new JSONObject(body);
                    JSONArray tripsArray = obj.getJSONArray("trips");

                    Voyage tempVoyage = new Voyage();
                    Voyage.Trip[] nouveauxTrips = new Voyage.Trip[tripsArray.length()];

                    for (int i = 0; i < tripsArray.length(); i++) {
                        JSONObject tripObj = tripsArray.getJSONObject(i);
                        nouveauxTrips[i] = tempVoyage.new Trip(
                                tripObj.getString("date"),
                                tripObj.getInt("nb_places_disponibles")
                        );
                    }
                    runOnUiThread(() -> {
                        this.trips = nouveauxTrips;
                        updatePlaces(trips[dateSpinner.getSelectedItemPosition()].nb_places_disponibles);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}