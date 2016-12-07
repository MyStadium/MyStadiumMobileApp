package com.example.denis.mystadium;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.denis.mystadium.Model.InfoMembre;
import com.example.denis.mystadium.Request.HttpManagerSuivi;
import com.example.denis.mystadium.Request.HttpManagerMembre;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denis on 30-11-16.
 */

public class joueurs_frag extends android.support.v4.app.Fragment{
    private View myView;
    private ListView playersFollowedListView;
    private HttpManagerSuivi httpSuiviManager;
    private HttpManagerMembre httpMembreManager;
    private ArrayAdapter<InfoMembre> adaptater;
    private Button btnAdd;
    private List<InfoMembre> favPlayersList;
    private SharedPreferences pref;
    private String playerLongClickName;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //instanciate variables
        myView = inflater.inflate(R.layout.joueurs, container, false);
        httpSuiviManager = new HttpManagerSuivi();
        httpMembreManager = new HttpManagerMembre();
        playersFollowedListView = (ListView) myView.findViewById(R.id.playersList);
        btnAdd = (Button)myView.findViewById(R.id.btnAdd);
        pref = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        try{
            favPlayersList= httpMembreManager.getFollowingPlayersList(pref.getInt("connectedUserId", 0));
            //adaptater
            adaptater = new ArrayAdapter<InfoMembre>(this.getContext(), android.R.layout.simple_list_item_1, favPlayersList);
            playersFollowedListView.setAdapter(adaptater);
            adaptater.notifyDataSetChanged();
        }catch(Exception e){
            Toast.makeText(getContext(), "Impossible de charger la liste de joueur", Toast.LENGTH_LONG).show();
        }




        //listeners
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddClicked();
            }
        });

        playersFollowedListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int pos, long id) {
                playerLongClickName = favPlayersList.get(pos).toString();
               return buildDialog(pos);
            }
        });
        return myView;
    }

    private boolean buildDialog(final int pos){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Ne plus suivre " +favPlayersList.get(pos).toString()+" ?")
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Ne plus suivre", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            httpSuiviManager.deleteSuivi(pref.getInt("connectedUserId", 0), favPlayersList.get(pos).getId());
                            favPlayersList.remove(pos);
                            adaptater.notifyDataSetChanged();
                            Toast.makeText(getActivity().getApplicationContext(), "Vous ne suivez plus "+playerLongClickName, Toast.LENGTH_LONG).show();
                        }catch(Exception e){
                            Toast.makeText(getContext(), "Erreur lors de la suppresion", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        builder.show();
        return true;
    }

    private void btnAddClicked(){
        Intent intent = new Intent(super.getContext(), SearchMembreActivity.class);
        intent.putParcelableArrayListExtra("listFavRest", (ArrayList<InfoMembre>)favPlayersList);
        startActivityForResult(intent, 2555);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2555) {
            favPlayersList = data.getParcelableArrayListExtra("listFavRest");
            adaptater = new ArrayAdapter<InfoMembre>(this.getContext(), android.R.layout.simple_list_item_1, favPlayersList);
            playersFollowedListView.setAdapter(adaptater);
            adaptater.notifyDataSetChanged();
        }
    }

}
