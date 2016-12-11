package com.example.denis.mystadium;

import android.content.Context;
import android.content.Intent;
import android.icu.text.IDNA;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denis.mystadium.Model.InfoRencontre;

import java.util.Date;
import java.util.List;

/**
 * Created by Utilisateur on 11-12-16.
 */

public class ResultListAdaptateur extends ArrayAdapter<InfoRencontre> {


    public ResultListAdaptateur(Context context, List<InfoRencontre> objects) {
        super(context, 0,objects);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_recherche_frag, parent, false);
        TextView txtDateTime = (TextView) rowView.findViewById(R.id.txtDateTime);
        TextView txtEquipeDomicile = (TextView) rowView.findViewById(R.id.txtEquipeDomicile);
        TextView txtEquipeExterieur = (TextView) rowView.findViewById(R.id.txtEquipeExtérieure);
        TextView txtScore = (TextView) rowView.findViewById(R.id.txtScore);
        TextView txtAdresse = (TextView) rowView.findViewById(R.id.txtAdresse);
        Button btnVoirDetails = (Button) rowView.findViewById(R.id.btnVoirMatch);

        txtDateTime.setText(getItem(position).getDateHeure().toString());
        txtEquipeDomicile.setText(getItem(position).getNomEquipeDomicile());
        txtEquipeExterieur.setText(getItem(position).getNomEquipeExterieur());
        txtScore.setText(getItem(position).getScoreFinalDomicile()+"-"+getItem(position).getScoreFinalExterieur());

        txtAdresse.setText(getItem(position).getNomStade()+" - "+getItem(position).getRue()+" "+getItem(position).getNumero()+" "+getItem(position).getLocalite());
        btnVoirDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    int selectedMatch = position;
                    Date now = new Date();
                    InfoRencontre r = getItem(position);
                    Intent intent;
                    if(r.getDateHeure().compareTo(now) > 0){
                        intent = new Intent(view.getContext(), BeforeMatchActivity.class);
                        Toast.makeText(getContext().getApplicationContext(), "Le match n'a pas encore commencé", Toast.LENGTH_SHORT).show();
                    }else{
                        intent = new Intent(getContext(), AfterMatchActivity.class);
                    }
                    intent.putExtra("selectedRencontreId", r.getIdRencontre());
                    getContext().startActivity(intent);

            }
        });
        return rowView;
    }

}
