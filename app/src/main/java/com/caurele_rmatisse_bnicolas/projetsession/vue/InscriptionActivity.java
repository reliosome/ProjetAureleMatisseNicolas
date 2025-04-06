package com.caurele_rmatisse_bnicolas.projetsession.vue;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.caurele_rmatisse_bnicolas.projetsession.R;
import com.caurele_rmatisse_bnicolas.projetsession.model.UtilitaireJSON;
import com.caurele_rmatisse_bnicolas.projetsession.vueModel.Client;

public class InscriptionActivity extends AppCompatActivity {

    private EditText eTxtNom, eTxtPrenom, eTxtAge, eTxtAdresse,
            eTxtTelephone, eTxtCourriel, eTxtMotPasse, eTxtMotPasse2;
    private Button btnInscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inscription);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        eTxtNom = findViewById(R.id.eTxtNom);
        eTxtPrenom = findViewById(R.id.eTxtPrenom);
        eTxtAge = findViewById(R.id.eTxtAge);
        eTxtAdresse = findViewById(R.id.eTxtAdresse);
        eTxtTelephone = findViewById(R.id.eTxtTelephone);
        eTxtCourriel = findViewById(R.id.eTxtCourriel2);
        eTxtMotPasse = findViewById(R.id.eTxtMotDePasse);
        eTxtMotPasse2 = findViewById(R.id.eTxtMotDePasse2);

        btnInscription = findViewById(R.id.btnInscription);

        btnInscription.setOnClickListener(v -> {
            String mdp1 = eTxtMotPasse.getText().toString();
            String mdp2 = eTxtMotPasse2.getText().toString();

            if(!mdp1.equals(mdp2)){
                Toast.makeText(this, "Veuillez entrer le même mot de passe deux fois.", Toast.LENGTH_LONG).show();

                eTxtMotPasse.setText("");
                eTxtMotPasse2.setText("");
            }else{

                (new Thread(){
                    @Override
                    public void run(){
                        UtilitaireJSON utilJson = new UtilitaireJSON();

                        Client nouvClient = new Client(utilJson.getLastId()+1, eTxtNom.getText().toString(), eTxtPrenom.getText().toString(),
                                eTxtCourriel.getText().toString(), eTxtMotPasse.getText().toString(),
                                Integer.parseInt(eTxtAge.getText().toString()),
                                eTxtTelephone.getText().toString(), eTxtAdresse.getText().toString());

                        if(utilJson.creerClient(nouvClient)){
                            finish();
                        }
                    }
                }).start();


            }
        });
    }
}