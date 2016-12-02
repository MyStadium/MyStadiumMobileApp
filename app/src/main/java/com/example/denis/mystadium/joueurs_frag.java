package com.example.denis.mystadium;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.denis.mystadium.Model.InfoMembre;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denis on 30-11-16.
 */

public class joueurs_frag extends android.support.v4.app.Fragment{
    private View myView;
    private ListView playersList;
    private HttpRequestMembre requestManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.joueurs, container, false);
        requestManager = new HttpRequestMembre();
        playersList = (ListView) myView.findViewById(R.id.playersList);

        SharedPreferences pref = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        List<InfoMembre> favPlayersList= requestManager.getFavPlayersList(pref.getInt("connectedUserId", 0));



        ArrayAdapter<InfoMembre> adaptater = new ArrayAdapter<InfoMembre>(this.getContext(), android.R.layout.simple_list_item_1, favPlayersList);
        playersList.setAdapter(adaptater);
        return myView;
    }
}
