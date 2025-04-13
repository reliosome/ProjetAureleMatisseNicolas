package com.caurele.projetsession.model.DAO;

import android.content.Context;

import androidx.annotation.Nullable;

import com.caurele.projetsession.vueModel.Voyage;

import java.util.List;

public class VoyageDAO {

    public static List<Voyage> rechercherVoyages(Context context, String destination, String type,
                                                 String date, Double prixMax) {
        return new UtilitaireJSON().rechercherVoyages(context, destination, type, date, prixMax);
    }

    public static Voyage chercherVoyageParId(Context context, int idVoyage)  {
        return new UtilitaireJSON().chercherVoyageParId(context, idVoyage);
    }
}