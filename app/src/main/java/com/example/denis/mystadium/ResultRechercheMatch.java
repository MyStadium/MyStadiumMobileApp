package com.example.denis.mystadium;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.denis.mystadium.Model.InfoRencontre;
import com.example.denis.mystadium.Request.HttpManagerRencontre;

import java.util.Collections;
import java.util.List;

public class ResultRechercheMatch extends AppCompatActivity {
    private ListView listViewResult;
    private ArrayAdapter<InfoRencontre> adaptater;
    private HttpManagerRencontre requestManager;
    private List<InfoRencontre> rencontreList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_recherche_match);
        listViewResult = (ListView)findViewById(R.id.listViewResult);
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
        adaptater = new ArrayAdapter<InfoRencontre>(this,android.R.layout.simple_list_item_1,rencontreList);
        listViewResult.setAdapter(adaptater);
        listViewResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onRencontreClick(position);
            }
        });
    }

    public void onRencontreClick(int pos){
        int selectedMatch = pos;
        InfoRencontre r = rencontreList.get(pos);
        Intent intent = new Intent(this, BeforeMatchActivity.class);
        intent.putExtra("selectedRencontreId", r.getIdRencontre());
        startActivity(intent);
    }
}
