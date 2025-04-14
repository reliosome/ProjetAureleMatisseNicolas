package com.caurele.projetsession.vueModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.caurele.projetsession.model.DAO.UtilitaireJSON;
import com.caurele.projetsession.model.DAO.VoyageDAO;

import java.util.List;

public class VoyageVueModel extends ViewModel {
    private MutableLiveData<List<Voyage>> voyages = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public LiveData<List<Voyage>> getVoyages(){return voyages;}
    public LiveData<String> getError() {
        return error;
    }

    public void obtenirVoyages(String destination, String type,
                           String date, Double prixMax){
        new Thread(() -> {
            List<Voyage> liste = VoyageDAO.rechercherVoyages(destination,type,date,prixMax);
            voyages.postValue(liste);
        }).start();
    }

    // Fonction pour modifier voyages (places dispo quand on réserve)
    public void modifierVoyage(Voyage voyage) {
        new Thread(() -> {

            new UtilitaireJSON().modifierVoyage(voyage);

        }).start();
    }
}
