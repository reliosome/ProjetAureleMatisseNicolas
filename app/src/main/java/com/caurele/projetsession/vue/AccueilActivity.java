package com.caurele.projetsession.vue;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

    private ListView lvVoyage;




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







    }
}