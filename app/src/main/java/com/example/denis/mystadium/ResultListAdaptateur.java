package com.example.denis.mystadium;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.os.AsyncTask;
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
                new AsyncVoirDetailsTask(getContext(),position,view).execute();
           }
        });
        return rowView;
    }

    private class AsyncVoirDetailsTask extends AsyncTask {
        private Context mContext;
        private ProgressDialog dialog;
        private Date now ;
        private InfoRencontre r ;
        private Intent intent;
        private Sport s;
        private int dureeMatch;
        private View v;
        public AsyncVoirDetailsTask(Context c,int pos,View view) {
            mContext = c;
            dialog = new ProgressDialog(c);
            now = new Date();
            r = getItem(pos);
            dureeMatch = 0;
            v = view;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Récupération des données depuis le serveur...");
            dialog.show();


        }

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                s = httpSportManager.getSportFromRencontre(r.getIdRencontre());

            } catch (Exception e) {
                e.printStackTrace();

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                cancel(true);
            }
            return null;
        }


        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(mContext, "Serveur injoignable", Toast.LENGTH_LONG).show();


        }

        @Override
        protected void onPostExecute(Object o) {

            super.onPostExecute(o);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            dureeMatch = s.getNbrPeriodes()*s.getTempsPeriode() + 20;
            Date dateFinMatch = new Date(r.getDateHeure().getTime() + TimeUnit.MINUTES.toMillis(dureeMatch));
            if(now.after(r.getDateHeure()) && now.before(dateFinMatch) ){
                intent = new Intent(v.getContext(), DuringMatchActivity.class);
            }
            else if(now.after(dateFinMatch)){
                intent = new Intent(v.getContext(), AfterMatchActivity.class);
                intent.putExtra("dureeMatch", dureeMatch);
            }else{
                intent = new Intent(getContext(), BeforeMatchActivity.class);
            }
            intent.putExtra("selectedRencontreId", r.getIdRencontre());
            getContext().startActivity(intent);
        }
    }

}
