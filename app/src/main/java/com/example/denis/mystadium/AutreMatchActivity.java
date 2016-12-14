package com.example.denis.mystadium;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;

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

        rencontreList = requestManager.getRencontreFromSerie(idChampionnat, journee);

        adaptater = new ResultListAdaptateur(this,rencontreList);
        setListAdapter(adaptater);
    }


}
