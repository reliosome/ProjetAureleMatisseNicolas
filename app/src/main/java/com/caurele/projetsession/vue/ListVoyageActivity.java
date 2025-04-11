package com.caurele.projetsession.vue;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.caurele.projetsession.R;
import com.caurele.projetsession.vue.adaptateur.VoyageAdapter;
import com.caurele.projetsession.vueModel.Voyage;
import com.caurele.projetsession.model.DAO.VoyageDAO;

import java.util.List;

public class ListVoyageActivity extends AppCompatActivity {

    private TextView titre;
    private VoyageAdapter voyageAdapter;
    private ListView lvVoyage;
    private Button annuler;




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
        String destination = intent.getStringExtra("destination");
        String type = intent.getStringExtra("type");
        String date = intent.getStringExtra("date");
        double prixMax = intent.getDoubleExtra("prixMax", Double.MAX_VALUE);


        List<Voyage> resultats = VoyageDAO.rechercherVoyages(this, destination, type, date, prixMax);
        Log.d("LIST_VOYAGE", "Filtrage reçu -> destination: " + destination + ", type: " + type + ", date: " + date + ", prixMax: " + prixMax);
        voyageAdapter = new VoyageAdapter(this);
        lvVoyage.setAdapter(voyageAdapter);
        voyageAdapter.setVoyages(resultats);


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
}