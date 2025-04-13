package com.caurele.projetsession.vueModel;

public class ReservationVoyage {
    public Reservation reservation;
    public Voyage voyage;

    public ReservationVoyage(Reservation reservation, Voyage voyage) {
        this.reservation = reservation;
        this.voyage = voyage;
    }
}
