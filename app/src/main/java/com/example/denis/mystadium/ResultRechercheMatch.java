package com.example.denis.mystadium;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.denis.mystadium.Model.InfoRencontre;

import java.util.List;

public class ResultRechercheMatch extends Activity {
    private ListView listViewResult;
    private ArrayAdapter<InfoRencontre> adaptater;
    private HttpRequestMembre requestManager;
    private List<InfoRencontre> rencontreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_recherche_frag);
        listViewResult = (ListView)findViewById(R.id.listViewResult);
        requestManager = new HttpRequestMembre();
        String dateDebut = savedInstanceState.getString("dateDebut");
        String dateFin = savedInstanceState.getString("dateFin");
        String nbKilometre = savedInstanceState.getString("nbKilometre");
        double latitude = Double.parseDouble(savedInstanceState.getString("latitude"));
        double longitude = Double.parseDouble(savedInstanceState.getString("longitude"));
        if(nbKilometre != null && dateDebut != null && dateFin != null) {
            rencontreList = requestManager.getRencontreRechercheList(dateDebut,dateFin,nbKilometre,latitude,longitude);
        }else{
            rencontreList = requestManager.getRencontreProcheList(latitude,longitude);
        }
        adaptater = new ArrayAdapter<InfoRencontre>(this.getApplicationContext(),android.R.layout.simple_list_item_1,rencontreList);
        listViewResult.setAdapter(adaptater);

    }
}
