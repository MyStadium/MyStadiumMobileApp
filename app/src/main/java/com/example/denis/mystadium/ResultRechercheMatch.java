package com.example.denis.mystadium;

import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ResultRechercheMatch extends ListActivity {
    private ResultListAdaptateur adaptater;
    private HttpManagerRencontre requestManager;
    private List<InfoRencontre> rencontreList;
    private TextView txtVide;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestManager = new HttpManagerRencontre();
        String dateDebut = "";
        String dateFin = "";
        String nbKilometre = "";
        double latitude = 0;
        double longitude = 0;
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

        if(nbKilometre != null && dateDebut != null && dateFin != null) {
            rencontreList = requestManager.getRencontreRechercheList(dateDebut,dateFin,nbKilometre,latitude,longitude);
        }else{
            rencontreList = requestManager.getRencontreProcheList(latitude,longitude);
        }

        adaptater = new ResultListAdaptateur(this,rencontreList);
        setListAdapter(adaptater);

        if(!rencontreList.isEmpty()) {
            adaptater = new ResultListAdaptateur(this, rencontreList);
            setListAdapter(adaptater);
        }else{
            onBackPressed();
            Toast.makeText(getApplicationContext(), "Aucun match ne correspond à votre recherche", Toast.LENGTH_LONG).show();
        }


    }


}
