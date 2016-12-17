package com.example.denis.mystadium;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denis.mystadium.Model.ClassementAdapter;
import com.example.denis.mystadium.Model.InfoEquipe;
import com.example.denis.mystadium.Request.HttpManagerEquipe;

import java.util.ArrayList;
import java.util.List;

public class ClassementActivity extends AppCompatActivity {

    private List<InfoEquipe> liste;
    private TextView txtClassement;
    private GridView gridViewClassement;
    private HttpManagerEquipe equipeManager;
    private ClassementAdapter adapter;

    private int idChampionnat;
    private String libelleChampionnat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classement);
        equipeManager = new HttpManagerEquipe();
        gridViewClassement = (GridView)findViewById(R.id.gridViewClassement);

        if (savedInstanceState == null) {
            Bundle extra = getIntent().getExtras();
            if(extra != null){
                idChampionnat = extra.getInt("idChampionnat");
                libelleChampionnat = extra.getString("libelleChampionnat");
            }
        }else{
            idChampionnat = savedInstanceState.getInt("idChampionnat");
            libelleChampionnat = savedInstanceState.getString("libelleChampionnat");
        }

        new AsyncClassementTask(getApplicationContext()).execute();




    }
    private class AsyncClassementTask extends AsyncTask {
        private Context mContext;
        private ProgressDialog dialog;
        public AsyncClassementTask(Context c){
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
        protected Object doInBackground(Object[] objects) {

            try {
                liste = equipeManager.getClassementForChampionnat(idChampionnat);
            } catch (Exception e){
                e.printStackTrace();
                if(dialog.isShowing()){
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
            onBackPressed();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if(dialog.isShowing()){
                dialog.dismiss();
            }
            List<String> listeString = new ArrayList<String>();
            for(int i = 0; i < liste.size(); i++){
                String place = ""+(i+1);
                String nomEquipe = liste.get(i).getNomClub();
                String nbrPoint = ""+liste.get(i).getNbrPoints();
                listeString.add(place);
                listeString.add(nomEquipe);
                listeString.add(nbrPoint);

            }

            adapter = new ClassementAdapter(mContext,liste);
            gridViewClassement.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            txtClassement = (TextView)findViewById(R.id.txtClassement);
            txtClassement.setText(libelleChampionnat);

        }
    }
}
