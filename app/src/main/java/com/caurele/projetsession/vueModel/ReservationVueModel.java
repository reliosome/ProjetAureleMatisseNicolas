package com.caurele.projetsession.vueModel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.caurele.projetsession.model.DAO.UtilitaireJSON;

import java.util.ArrayList;

public class ReservationVueModel extends ViewModel {

    private ReservationRepository reservationRepository;
    // ReservationVueModel pourrait potentiellement remplacer
    // entièrement ReservationRepository, mais sinon on peut l'avoir ici

    private UtilitaireJSON utilitaireJSON;

    public ReservationVueModel(Context context){
        reservationRepository = new ReservationRepository(context);
        utilitaireJSON = new UtilitaireJSON();
    }


    // Fonctions pour sauvegarder et charger les réservations
    public void saveReservation(int nbPlaces, int idVoyage, int idClient){
        reservationRepository.sauvegarderReservation(nbPlaces, idVoyage, idClient);
    }

    public ArrayList<Reservation> getReservations(int idClient){
        return reservationRepository.recupererReservations(idClient);
    }

    public void annulerReservation(int idReservation){
        reservationRepository.annulerReservation(idReservation);
    }

}
