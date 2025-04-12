package com.caurele.projetsession.vueModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.caurele.projetsession.model.DAO.UtilitaireJSON;

public class ReservationVueModel extends ViewModel {

    private LiveData<Reservation> reservations;

    private ReservationRepository reservationRepository;
    // ReservationVueModel pourrait potentiellement remplacer
    // entièrement ReservationRepository, mais sinon on peut l'avoir ici

    private UtilitaireJSON utilitaireJSON;

    // Fonctions pour sauvegarder et charger les réservations

    // Fonction pour modifier voyages (places dispo quand on réserve)


}
