package com.example.denis.mystadium;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
    ArrayAdapter<InfoMembre> adaptater;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.joueurs, container, false);
        requestManager = new HttpRequestMembre();
        playersList = (ListView) myView.findViewById(R.id.playersList);



        SharedPreferences pref = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        final List<InfoMembre> favPlayersList= requestManager.getFavPlayersList(pref.getInt("connectedUserId", 0));

        playersList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int pos, long id) {
               return buildDialog(favPlayersList, pos);
            }
        });



        adaptater = new ArrayAdapter<InfoMembre>(this.getContext(), android.R.layout.simple_list_item_1, favPlayersList);
        playersList.setAdapter(adaptater);
        return myView;
    }

    private boolean buildDialog(final List<InfoMembre> favPlayersList, final int pos){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Ne plus suivre " +favPlayersList.get(pos).getPrenom()+" "+favPlayersList.get(pos).getNom()+" ?")
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Ne plus suivre", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences pref = getActivity().getPreferences(Context.MODE_PRIVATE);

                        requestManager.deleteFav(pref.getInt("connectedUserId", 0), favPlayersList.get(pos).getId());
                        favPlayersList.remove(pos);
                        adaptater.notifyDataSetChanged();
                        Toast.makeText(getActivity().getApplicationContext(), "Vous ne suivez plus ce joueur", Toast.LENGTH_LONG).show();
                    }
                });

        builder.show();
        return true;
    }
}
