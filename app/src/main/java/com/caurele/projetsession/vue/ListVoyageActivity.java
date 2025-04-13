package com.caurele.projetsession.vue;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.caurele.projetsession.R;
import com.caurele.projetsession.vue.adaptateur.VoyageAdapter;
import com.caurele.projetsession.vueModel.Voyage;
import com.caurele.projetsession.model.DAO.VoyageDAO;
import com.caurele.projetsession.vueModel.VoyageVueModel;

import java.util.List;

public class ListVoyageActivity extends AppCompatActivity {

    private TextView titre;
    private VoyageAdapter voyageAdapter;
    private ListView lvVoyage;
    private Button annuler;
    private VoyageVueModel voyageVueModel;
    private String destination;
    private String type;
    private String date;
    private double prixMax;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_voyage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        titre = findViewById(R.id.id_titrelistvoyage);
        lvVoyage = findViewById(R.id.id_lvVoy);
        annuler = findViewById(R.id.id_annuler);

        Intent intent = getIntent();
        destination = intent.getStringExtra("destination");
        type = intent.getStringExtra("type");
        date = intent.getStringExtra("date");
        prixMax = intent.getDoubleExtra("prixMax", Double.MAX_VALUE);

        // Créer et régler Adapteur
        voyageAdapter = new VoyageAdapter(ListVoyageActivity.this);
        lvVoyage.setAdapter(voyageAdapter);

        // Récupérer VueModel
        voyageVueModel = new ViewModelProvider(this).get(VoyageVueModel.class);

        voyageVueModel.getVoyages().observe(this, voyages -> {
            voyageAdapter.setVoyages(voyages);
        });

        // Observer les éventuelles erreurs
        voyageVueModel.getError().observe(this, errorMessage ->
                Toast.makeText(ListVoyageActivity.this, errorMessage, Toast.LENGTH_SHORT).show()
        );

        lvVoyage.setOnItemClickListener((parent, view, position, id) -> {
            Voyage voyage = voyageAdapter.getItem(position);
            Intent i = new Intent(ListVoyageActivity.this, DetailsVoyageActivity.class);
            i.putExtra("voyage", voyage);
            startActivity(i);
        });


        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        voyageVueModel.obtenirVoyages(destination, type, date, prixMax);
    }
}