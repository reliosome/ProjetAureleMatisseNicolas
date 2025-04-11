package com.caurele.projetsession.vue;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.caurele.projetsession.R;

public class AccueilActivity extends AppCompatActivity {


    private Button rechercher;
    private TextView destinationText, prixText, typeText, dateText;
    private EditText destEdit, prixEdit, typeEdit, dateEdit;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_accueil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rechercher = findViewById(R.id.id_rechercher);
        destinationText = findViewById(R.id.id_destText);
        prixText = findViewById(R.id.id_prixText);
        typeText = findViewById(R.id.id_typeText);
        dateText = findViewById(R.id.id_dateText);
        destEdit = findViewById(R.id.id_entreDest);
        prixEdit = findViewById(R.id.id_entrePrix);
        typeEdit = findViewById(R.id.id_entreType);
        dateEdit = findViewById(R.id.id_entreDate);



        rechercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String destination = destEdit.getText().toString().trim();
                String type = typeEdit.getText().toString().trim();
                String date = dateEdit.getText().toString().trim();
                String prixStr = prixEdit.getText().toString().trim();

                if (destination.equalsIgnoreCase("Entrez votre destination")) destination = "";
                if (type.equalsIgnoreCase("Entrez un type de voyage")) type = "";
                if (date.equalsIgnoreCase("Entrez votre date de départ")) date = "";
                if (prixStr.equalsIgnoreCase("Entrez votre budget")) prixStr = "";

                double prixMax;
                try {
                    prixMax = prixStr.isEmpty() ? Double.MAX_VALUE : Double.parseDouble(prixStr);
                } catch (NumberFormatException e) {
                    prixMax = Double.MAX_VALUE;
                }

                Intent intent = new Intent(AccueilActivity.this, ListVoyageActivity.class);
                intent.putExtra("destination", destination);
                intent.putExtra("type", type);
                intent.putExtra("date", date);
                intent.putExtra("prixMax", prixMax);
                startActivity(intent);

        }});



    }
}