/*************************************************************
 *  Projet de session TCH057
 *  Agence de voyage
 *  Groupe 9
 *  Auteurs : Aurèle Collin, Matisse Ruel, Nicolas Berlinguet
 *
 *************************************************************/
package com.caurele.projetsession.vue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.caurele.projetsession.R;

public class ConnexionActivity extends AppCompatActivity {

    private EditText eTxtCourriel, eTxtMotPasse;
    private Button btnConnect, btnInscrire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_connexion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        eTxtCourriel = findViewById(R.id.eTxtCourriel);
        eTxtMotPasse = findViewById(R.id.eTxtMotPasse);
        btnConnect = findViewById(R.id.btnConnecter);
        btnInscrire = findViewById(R.id.btnInscrire);

        btnInscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intention = new Intent(ConnexionActivity.this, InscriptionActivity.class);
                startActivity(intention);
            }
        });

        btnConnect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String courriel = String.valueOf(eTxtCourriel.getText());
                String motPasse = String.valueOf(eTxtMotPasse.getText());

                // Authentifier(courriel, motPasse); TODO
            }
        });
    }
}