package com.example.denis.mystadium;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.denis.mystadium.Model.InfoRencontre;
import com.example.denis.mystadium.Request.HttpManagerRencontre;

import java.util.List;

public class AutreMatchActivity extends ListActivity {
    private ResultListAdaptateur adaptater;
    private HttpManagerRencontre requestManager;
    private List<InfoRencontre> rencontreList;

    private int idChampionnat;
    private int journee;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            Bundle extra = getIntent().getExtras();
            if(extra != null){
                idChampionnat = extra.getInt("idChampionnat");
                journee = extra.getInt("journee");
            }
        }else{
            idChampionnat = savedInstanceState.getInt("idChampionnat");
            journee = savedInstanceState.getInt("journee");
        }

        requestManager = new HttpManagerRencontre();

        new AsyncListTask(this).execute();



    }

    private class AsyncListTask extends AsyncTask {
        private Context mContext;
        private ProgressDialog dialog;
        public AsyncListTask(Context c){
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
                rencontreList = requestManager.getRencontreFromSerie(idChampionnat, journee);
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
            adaptater = new ResultListAdaptateur(mContext,rencontreList);
            setListAdapter(adaptater);

        }
    }
}
