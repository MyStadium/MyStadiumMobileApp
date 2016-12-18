package com.example.denis.mystadium;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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

import com.example.denis.mystadium.Model.InfoEquipe;
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

        myView = inflater.inflate(R.layout.joueurs, container, false);
        httpSuiviManager = new HttpManagerSuivi();
        httpMembreManager = new HttpManagerMembre();
        playersFollowedListView = (ListView) myView.findViewById(R.id.playersList);
        btnAdd = (Button)myView.findViewById(R.id.btnAdd);
        pref = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        new AsyncListFavTask(getActivity(),this.getContext()).execute();

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
                        new AsyncDeleteFavTask(getActivity()).execute(pos);
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

    private class AsyncListFavTask extends AsyncTask {
        private Context mContext;
        private ProgressDialog dialog;
        private Context adaptateurContext;
        public AsyncListFavTask(Context c, Context m){
            mContext = c;
            dialog = new ProgressDialog(c);
            adaptateurContext = m;
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
                favPlayersList= httpMembreManager.getFollowingPlayersList(pref.getInt("connectedUserId", 0));
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
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if(dialog.isShowing()){
                dialog.dismiss();
            }
            adaptater = new ArrayAdapter<InfoMembre>(adaptateurContext, android.R.layout.simple_list_item_1, favPlayersList);
            playersFollowedListView.setAdapter(adaptater);
            adaptater.notifyDataSetChanged();

        }
    }

    private class AsyncDeleteFavTask extends AsyncTask {
        private Context mContext;

        private ProgressDialog dialog;
        public AsyncDeleteFavTask(Context c){
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
        protected Object doInBackground(Object[] params) {

            try {
                httpSuiviManager.deleteSuivi(pref.getInt("connectedUserId", 0), favPlayersList.get((int)params[0]).getId());
            } catch (Exception e){
                e.printStackTrace();
                if(dialog.isShowing()){
                    dialog.dismiss();

                }
                cancel(true);

            }
            return (int)params[0];
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(mContext, "Serveur injoignable", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if(dialog.isShowing()){
                dialog.dismiss();
            }
            favPlayersList.remove((int)o);
            adaptater.notifyDataSetChanged();
            Toast.makeText(getActivity().getApplicationContext(), "Vous ne suivez plus "+playerLongClickName, Toast.LENGTH_LONG).show();

        }
    }

}
