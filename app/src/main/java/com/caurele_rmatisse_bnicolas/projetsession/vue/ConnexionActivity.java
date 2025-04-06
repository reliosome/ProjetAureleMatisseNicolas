/*************************************************************
 *  Projet de session TCH057
 *  Agence de voyage
 *  Groupe 9
 *  Auteurs : Aurèle Collin, Matisse Ruel, Nicolas Berlinguet
 *
 *************************************************************/
package com.caurele_rmatisse_bnicolas.projetsession.vue;

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

import com.caurele_rmatisse_bnicolas.projetsession.R;
import com.caurele_rmatisse_bnicolas.projetsession.model.UtilitaireJSON;
import com.caurele_rmatisse_bnicolas.projetsession.vueModel.Client;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
                UtilitaireJSON utilJson = new UtilitaireJSON();

                (new Thread(){
                    @Override
                    public void run(){
                        String clients = utilJson.getClients();

                        String courriel = String.valueOf(eTxtCourriel.getText());
                        String motPasse = String.valueOf(eTxtMotPasse.getText());

                        ObjectMapper mapperClient = new ObjectMapper();
                        Client[] lesClients;
                        try {
                            lesClients = mapperClient.readValue(clients, Client[].class);
                        } catch (JsonProcessingException e){
                            throw new RuntimeException(e);
                        }

                        Intent iAccueil = new Intent(ConnexionActivity.this, AccueilActivity.class);

                        if(Authentifier(courriel, motPasse, lesClients)){
                            startActivity(iAccueil);
                        }
                    }
                }).start();
            }
        });
    }

    private boolean Authentifier(String courriel, String motPasse, Client[] clients) {
        Client user = null;

        for(int i=0; i< clients.length; i++){
            if(clients[i].existe(courriel)){
                user = clients[i];
            }
        }

        if(user != null){
            return user.getMdp().equals(motPasse);
        }

        return false;
    }
}