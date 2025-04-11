package com.caurele.projetsession.vue;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.caurele.projetsession.R;
import com.caurele.projetsession.vueModel.ClientVueModel;

public class InscriptionActivity extends AppCompatActivity {

    private EditText eTxtNom, eTxtPrenom, eTxtAge, eTxtAdresse,
            eTxtTelephone, eTxtCourriel, eTxtMotPasse, eTxtMotPasse2;
    private Button btnInscription;
    private ClientVueModel clientVueModel;

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

        clientVueModel = new ViewModelProvider(this).get(ClientVueModel.class);


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

                        String nom = eTxtNom.getText().toString();
                        String prenom = eTxtPrenom.getText().toString();
                        String courriel = eTxtCourriel.getText().toString();
                        String motPasse = eTxtMotPasse.getText().toString();
                        int age = Integer.parseInt(eTxtAge.getText().toString());
                        String telephone = eTxtTelephone.getText().toString();
                        String adresse = eTxtAdresse.getText().toString();

                        if(clientVueModel.inscription(nom,prenom,courriel,motPasse,age,telephone,adresse)){
                            finish();
                        }
                    }
                }).start();


            }
        });
    }
}