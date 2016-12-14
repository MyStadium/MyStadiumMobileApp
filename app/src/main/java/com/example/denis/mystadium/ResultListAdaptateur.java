package com.example.denis.mystadium;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denis.mystadium.Model.InfoRencontre;
import com.example.denis.mystadium.Model.Sport;
import com.example.denis.mystadium.Request.HttpManagerSport;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Utilisateur on 11-12-16.
 */

public class ResultListAdaptateur extends ArrayAdapter<InfoRencontre> {

    private HttpManagerSport httpSportManager;


    public ResultListAdaptateur(Context context, List<InfoRencontre> objects) {
        super(context, 0,objects);
        httpSportManager = new HttpManagerSport();
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
        final Date date = getItem(position).getDateHeure();
        SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String dateString = format2.format(date);
        txtDateTime.setText(dateString);
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

                    Sport s;
                    int dureeMatch = 0;
                    try{
                        s = httpSportManager.getSportFromRencontre(r.getIdRencontre());
                        dureeMatch = s.getNbrPeriodes()*s.getTempsPeriode() + 20;

                    }catch(Exception e){
                        Toast.makeText(getContext(), "Erreur lors de la récupération du sport", Toast.LENGTH_SHORT).show();
                    }

                    Date dateFinMatch = new Date(r.getDateHeure().getTime() + TimeUnit.MINUTES.toMillis(dureeMatch));
                    if(now.after(r.getDateHeure()) && now.before(dateFinMatch) ){
                        intent = new Intent(view.getContext(), DuringMatchActivity.class);
                    }
                    else if(now.after(dateFinMatch)){
                        intent = new Intent(view.getContext(), AfterMatchActivity.class);
                        intent.putExtra("dureeMatch", dureeMatch);
                    }else{
                        intent = new Intent(getContext(), BeforeMatchActivity.class);
                    }
                    intent.putExtra("selectedRencontreId", r.getIdRencontre());
                    getContext().startActivity(intent);
            }
        });
        return rowView;
    }

}
