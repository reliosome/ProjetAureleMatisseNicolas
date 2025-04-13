package com.caurele.projetsession.model.DAO;

import android.content.Context;

import androidx.annotation.Nullable;

import com.caurele.projetsession.vueModel.Voyage;

import java.util.List;

public class VoyageDAO {

    public static List<Voyage> rechercherVoyages(String destination, String type,
                                                 String date, Double prixMax) {
        return new UtilitaireJSON().rechercherVoyages(destination, type, date, prixMax);
    }

    public static Voyage chercherVoyageParId(int idVoyage)  {
        return new UtilitaireJSON().chercherVoyageParId(idVoyage);
    }
}