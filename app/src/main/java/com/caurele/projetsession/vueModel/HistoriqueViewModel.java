package com.caurele.projetsession.vueModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Context;
import java.util.List;

public class HistoriqueViewModel extends ViewModel {
    private MutableLiveData<List<ReservationVoyage>> reservations = new MutableLiveData<>();

    public LiveData<List<ReservationVoyage>> getReservations() {
        return reservations;
    }
    public void chargerReservations(Context context) {
        new ReservationRepository(context).getReservationsVoyages(result -> {
            reservations.setValue(result);
        });
    }
}
