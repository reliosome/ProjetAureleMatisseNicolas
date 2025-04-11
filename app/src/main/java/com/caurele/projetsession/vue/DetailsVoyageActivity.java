package com.caurele.projetsession.vue;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.caurele.projetsession.R;
import com.caurele.projetsession.vueModel.Voyage;

public class DetailsVoyageActivity extends AppCompatActivity {

    private TextView titre, destination, description, type, duree, prix, placesDispo, prixTotal;
    private ImageView image;
    private Spinner dateSpinner;
    private EditText nombrePlaces;
    private Button reserver;

    private Voyage voyage;
    private Voyage.Trip[] trips;

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

    }}