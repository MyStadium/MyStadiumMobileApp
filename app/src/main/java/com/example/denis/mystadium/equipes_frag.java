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
import android.widget.TextView;
import android.widget.Toast;

import com.example.denis.mystadium.Model.ClassementAdapter;
import com.example.denis.mystadium.Model.InfoEquipe;
import com.example.denis.mystadium.Model.InfoMembre;
import com.example.denis.mystadium.Request.HttpManager;
import com.example.denis.mystadium.Request.HttpManagerEquipe;
import com.example.denis.mystadium.Request.HttpManagerFavoris;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denis on 30-11-16.
 */

public class equipes_frag extends android.support.v4.app.Fragment{
    private View myView;
    private Button btnAddteam;
    private ListView listViewTeam;
    private List<InfoEquipe> listFavEquipe;
    private ArrayAdapter<InfoEquipe> adaptater;
    private HttpManagerEquipe httpEquipeManager;
    private HttpManagerFavoris httpFavorisManager;
    private SharedPreferences pref;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.equipes, container, false);

        pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        btnAddteam = (Button) myView.findViewById(R.id.btnAddTeam);
        listViewTeam = (ListView) myView.findViewById(R.id.teamsList);

        httpEquipeManager = new HttpManagerEquipe();
        httpFavorisManager = new HttpManagerFavoris();

        new AsyncListFavTask(getActivity()).execute();


        btnAddteam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddteamClickedMethod();
            }
        });

        listViewTeam.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int pos, long id) {
                return buildDialog(pos);
            }
        });




        return myView;
    }

    public void btnAddteamClickedMethod(){
        Intent intent = new Intent(super.getContext(), SearchTeamActivity.class);
        intent.putParcelableArrayListExtra("listFavTeamRest", (ArrayList<InfoEquipe>)listFavEquipe);
        startActivityForResult(intent, 2666);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2666) {
            listFavEquipe = data.getParcelableArrayListExtra("listFavTeamRest");
            adaptater = new ArrayAdapter<InfoEquipe>(this.getContext(), android.R.layout.simple_list_item_1, listFavEquipe);
            listViewTeam.setAdapter(adaptater);
            adaptater.notifyDataSetChanged();
        }
    }

    private boolean buildDialog(final int pos){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Ne plus suivre " +listFavEquipe.get(pos).toString()+" ?")
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

    private class AsyncListFavTask extends AsyncTask {
        private Context mContext;
        private ProgressDialog dialog;
        public AsyncListFavTask(Context c){
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
                listFavEquipe = httpEquipeManager.getFavTeamList(pref.getInt("connectedUserId", 0));
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
            adaptater = new ArrayAdapter<InfoEquipe>(mContext, android.R.layout.simple_list_item_1, listFavEquipe);
            listViewTeam.setAdapter(adaptater);
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
                httpFavorisManager.deleteFav(pref.getInt("connectedUserId", 0), listFavEquipe.get((int)params[0]).getIdEquipe());
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
            listFavEquipe.remove(o);
            adaptater.notifyDataSetChanged();
            Toast.makeText(mContext, "Vous ne suivez plus cette équipe", Toast.LENGTH_LONG).show();

        }
    }
}
