package com.example.denis.mystadium;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denis.mystadium.Model.InfoRencontre;
import com.example.denis.mystadium.Model.Sport;
import com.example.denis.mystadium.Request.HttpManagerRencontre;
import com.example.denis.mystadium.Request.HttpManagerSport;

import org.apache.http.conn.ConnectTimeoutException;
import org.w3c.dom.Text;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ResultRechercheMatch extends ListActivity {
    private ResultListAdaptateur adaptater;
    private HttpManagerRencontre requestManager;
    private List<InfoRencontre> rencontreList;
    private String dateDebut = "";
    private String dateFin = "";
    private String nbKilometre = "";
    private double latitude = 0;
    private double longitude = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestManager = new HttpManagerRencontre();
        if (savedInstanceState == null) {
            Bundle extra = getIntent().getExtras();
            if(extra != null){
                dateDebut = extra.getString("dateDebut");
                dateFin = extra.getString("dateFin");
                nbKilometre = extra.getString("nbKilometre");
                latitude = extra.getDouble("latitude");
                longitude = extra.getDouble("longitude");
            }
        }else{
            dateDebut = savedInstanceState.getString("dateDebut");
            dateFin = savedInstanceState.getString("dateFin");
            nbKilometre = savedInstanceState.getString("nbKilometre");
            latitude = Double.parseDouble(savedInstanceState.getString("latitude"));
            longitude = Double.parseDouble(savedInstanceState.getString("longitude"));
        }
        AsyncListTask async = new AsyncListTask(this);
        async.execute();


    }


    private class AsyncListTask extends AsyncTask{
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
                if (nbKilometre != null && dateDebut != null && dateFin != null) {

                    rencontreList = requestManager.getRencontreRechercheList(dateDebut, dateFin, nbKilometre, latitude, longitude);
                } else {
                    rencontreList = requestManager.getRencontreProcheList(latitude, longitude);
                }
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
            if(rencontreList.isEmpty()){
                onBackPressed();
                Toast.makeText(mContext, "Aucun élément ne correspond à votre recherche", Toast.LENGTH_SHORT).show();
            }else {
                adaptater = new ResultListAdaptateur(mContext, rencontreList);
                setListAdapter(adaptater);
            }
        }
    }


}
