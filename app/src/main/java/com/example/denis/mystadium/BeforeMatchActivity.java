package com.example.denis.mystadium;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denis.mystadium.Model.InfoEquipe;
import com.example.denis.mystadium.Model.InfoRencontre;
import com.example.denis.mystadium.Model.Score;
import com.example.denis.mystadium.Request.HttpManagerEquipe;
import com.example.denis.mystadium.Request.HttpManagerRencontre;

import java.text.SimpleDateFormat;

public class BeforeMatchActivity extends AppCompatActivity {

    private TextView txtNomDomicile;
    private TextView txtNomExterieur;
    private TextView txtRencontreDate;
    private TextView txtRencontreJournee;
    private TextView txtMarqueDomicile;
    private TextView txtMarqueExterieur;
    private TextView txtEncaisseDomicile;
    private TextView txtEncaisseExterieur;
    private TextView txtGagneDomicile;
    private TextView txtGagneExterieur;
    private TextView txtNulDomicile;
    private TextView txtNulExterieur;
    private TextView txtPerduDomicile;
    private TextView txtPerduExterieur;
    private TextView txtCategorie;
    private TextView txtNomStade;
    private TextView txtAdresseStade;
    private Button btnViewClassement;

    private InfoRencontre rencontre;
    private InfoEquipe equipeDomicile;
    private InfoEquipe equipeExterieur;
    private int selectedRencontreId;
    private HttpManagerRencontre httpRencontreManager;
    private HttpManagerEquipe httpEquipeManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_match);

        txtNomDomicile = (TextView)findViewById(R.id.txtAfterMatchNomDomicile);
        txtNomExterieur = (TextView)findViewById(R.id.txtNomExterieur);
        txtRencontreDate = (TextView)findViewById(R.id.txtAfterMatchDate);
        txtRencontreJournee = (TextView) findViewById(R.id.txtRencontreJournée);
        txtMarqueDomicile = (TextView) findViewById(R.id.txtMarqueDomicile);
        txtMarqueExterieur = (TextView) findViewById(R.id.txtMarqueExterieur);
        txtEncaisseDomicile = (TextView) findViewById(R.id.txtEncaisseDomicile);
        txtEncaisseExterieur = (TextView) findViewById(R.id.txtEncaisseExterieur);
        txtGagneDomicile = (TextView) findViewById(R.id.txtGagnesDomicile);
        txtGagneExterieur = (TextView) findViewById(R.id.txtGagnesExterieur);
        txtNulDomicile = (TextView) findViewById(R.id.txtNulDomicile);
        txtNulExterieur = (TextView) findViewById(R.id.txtNulExterieur);
        txtPerduDomicile = (TextView) findViewById(R.id.txtPerduDomicile);
        txtPerduExterieur = (TextView) findViewById(R.id.txtPerduExterieur);
        txtCategorie = (TextView) findViewById(R.id.txtCategorie);
        txtNomStade = (TextView) findViewById(R.id.txtNomStade);
        txtAdresseStade = (TextView) findViewById(R.id.txtAdresseStade);

        btnViewClassement = (Button) findViewById(R.id.btnViewClassement);

        httpRencontreManager = new HttpManagerRencontre();
        httpEquipeManager = new HttpManagerEquipe();

        if (savedInstanceState == null) {
            Bundle extra = getIntent().getExtras();
            if(extra != null){
                selectedRencontreId = extra.getInt("selectedRencontreId");
            }
        }else{
            selectedRencontreId = savedInstanceState.getInt("selectedRencontreId");
        }

        btnViewClassement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewClassement();
            }
        });

        new AsyncChargerTask(BeforeMatchActivity.this).execute();

    }

    public void viewClassement(){
        Intent intent = new Intent(this, ClassementActivity.class);
        intent.putExtra("idChampionnat", rencontre.getIdChampionnat());
        intent.putExtra("libelleChampionnat", equipeDomicile.getCategorieAge() +" "+rencontre.getLibelleChampionnat());
        startActivity(intent);
    }
    private class AsyncChargerTask extends AsyncTask {
        private Context mContext;
        private ProgressDialog dialog;
        public AsyncChargerTask(Context c) {
            mContext = c;
            dialog = new ProgressDialog(c);

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
                rencontre = httpRencontreManager.getInfoRencontreById(selectedRencontreId);
                equipeDomicile = httpEquipeManager.getEquipeById(rencontre.getIdEquipeDomicile());
                equipeExterieur = httpEquipeManager.getEquipeById(rencontre.getIdEquipeExterieur());
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
            SimpleDateFormat format = new SimpleDateFormat("EE dd/MM/yyyy HH:mm");
            String dateHeure = format.format(rencontre.getDateHeure());
            txtNomDomicile.setText(rencontre.getNomEquipeDomicile());
            txtNomExterieur.setText(rencontre.getNomEquipeExterieur());
            txtRencontreDate.setText(dateHeure);
            txtRencontreJournee.setText("Journée "+rencontre.getJournee());
            txtMarqueDomicile.setText(""+equipeDomicile.getNbrMarque());
            txtMarqueExterieur.setText(""+equipeExterieur.getNbrMarque());
            txtEncaisseDomicile.setText(""+equipeDomicile.getNbrEncaisse());
            txtEncaisseExterieur.setText(""+equipeExterieur.getNbrEncaisse());
            txtGagneDomicile.setText(""+equipeDomicile.getNbrGagnes());
            txtGagneExterieur.setText(""+equipeExterieur.getNbrGagnes());
            txtNulDomicile.setText(""+equipeDomicile.getNbrNuls());
            txtNulExterieur.setText(""+equipeExterieur.getNbrNuls());
            txtPerduDomicile.setText(""+equipeDomicile.getNbrPerdus());
            txtPerduExterieur.setText(""+equipeExterieur.getNbrPerdus());
            txtCategorie.setText(equipeDomicile.getCategorieAge()+" "+ rencontre.getLibelleChampionnat());
            txtNomStade.setText(rencontre.getNomStade());
            txtAdresseStade.setText(rencontre.getNumero() +" " +rencontre.getRue() +", " +rencontre.getLocalite());

        }
    }
}
