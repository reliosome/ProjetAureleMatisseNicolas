package com.caurele.projetsession.model.adaptateur;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.caurele.projetsession.R;
import com.caurele.projetsession.vueModel.Voyage;

import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class VoyageAdapter extends ArrayAdapter<Voyage> {

    private final Activity context;
    private List<Voyage> voyages = new ArrayList<>();

    public VoyageAdapter(Activity context) {
        super(context, 0);
        this.context = context;
    }

    public void setVoyages(List<Voyage> voyages) {
        this.voyages = voyages;
        clear();
        addAll(voyages);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return voyages.size();
    }

    @Override
    public Voyage getItem(int position) {
        return voyages.get(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("VOYAGE_ADAPTER", "getView called for position: " + position);
        Voyage voyage = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_voyage, parent, false);
        }

        ImageView image = convertView.findViewById(R.id.voyage_image);
        TextView titre = convertView.findViewById(R.id.voyage_titre);
        TextView description = convertView.findViewById(R.id.voyage_description);
        TextView prix = convertView.findViewById(R.id.voyage_prix);

        titre.setText(voyage.getNom_voyage());
        description.setText(voyage.getDescription());
        prix.setText(String.format("%.2f $", voyage.getPrix()));


        Picasso.get()
                .load(voyage.getImage_url())

                .into(image);

        return convertView;
    }
}