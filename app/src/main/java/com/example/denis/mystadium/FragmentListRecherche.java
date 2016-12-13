package com.example.denis.mystadium;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.denis.mystadium.Model.InfoRencontre;
import com.example.denis.mystadium.Request.HttpManagerRencontre;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Utilisateur on 11-12-16.
 */

public class FragmentListRecherche extends ListFragment{
    private HttpManagerRencontre requestManager;
    private List<InfoRencontre> rencontreList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View)inflater.inflate(R.layout.list_recherche_frag,container,false);
        requestManager = new HttpManagerRencontre();
        String dateDebut = "";
        String dateFin = "";
        String nbKilometre = "";
        double latitude = 0;
        double longitude = 0;
        if (savedInstanceState == null) {
            Bundle extra = getActivity().getIntent().getExtras();
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

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<InfoRencontre> adapter = new ArrayAdapter<InfoRencontre>(getActivity(),android.R.layout.simple_list_item_1,rencontreList);
        setListAdapter(adapter);
    }
}
