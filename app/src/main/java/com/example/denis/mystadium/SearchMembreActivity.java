package com.example.denis.mystadium;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.example.denis.mystadium.Model.InfoMembre;
import com.example.denis.mystadium.Model.Membre;
import com.example.denis.mystadium.Model.Suivre;
import com.example.denis.mystadium.Request.HttpManagerSuivi;
import com.example.denis.mystadium.Request.HttpManagerMembre;

import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

public class SearchMembreActivity extends AppCompatActivity {

    private Button btnSearch;
    private Button btnAddSearch;
    private ListView searchListView;
    private EditText txtSearch;

    private ArrayAdapter<InfoMembre> adaptater;
    private  List<InfoMembre> listFromSearchInRest;
    private InfoMembre selectedMember;
    private List<InfoMembre> listeSuivis;

    private HttpManagerMembre httpMembreManager;
    private HttpManagerSuivi httpManager;

    private int previousItemSelected;
    private int currentlyItemSelected;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_member);


        btnSearch = (Button)findViewById(R.id.btnSearch);
        btnAddSearch = (Button)findViewById(R.id.btnAddSearch);
        searchListView = (ListView) findViewById(R.id.searchList);
        txtSearch = (EditText) findViewById(R.id.txtSearch);

        httpMembreManager = new HttpManagerMembre();
        httpManager = new HttpManagerSuivi();

        previousItemSelected = -1;
        currentlyItemSelected = -1;


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSearchClicked();
            }
        });
        btnAddSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddSearchClicked();
            }
        });
        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               onItemClickMethod(position);
            }
        });
        searchListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        if (savedInstanceState == null) {
            Bundle extra = getIntent().getExtras();
            if (extra == null) {
                listeSuivis = null;
            } else {
                listeSuivis=(ArrayList<InfoMembre>)extra.getSerializable("listFavRest");
            }
        } else {
            listeSuivis=(ArrayList<InfoMembre>)savedInstanceState.getSerializable("listFavRest");
        }

    }

    public void btnSearchClicked(){
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        if(txtSearch.getText().toString().length() == 0){
            Toast.makeText(this, "Le champs de recherche est vide", Toast.LENGTH_LONG).show();
        }else {
            new AsyncRechercheTask(this).execute();
        }

    }

    public void btnAddSearchClicked(){
        if(selectedMember != null){
            SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(this);
            int idUtilisateur = shared.getInt("connectedUserId", 0);
            Suivre s = new Suivre(selectedMember.getId(), idUtilisateur);
            new AsyncSuivreTask(this).execute(s);
        }else{
            Toast.makeText(this, "Aucun membre selectionné", Toast.LENGTH_SHORT).show();
        }
    }

    public void onItemClickMethod(int position){
        currentlyItemSelected = position;
        if(previousItemSelected != -1){
            searchListView.getChildAt(previousItemSelected).setBackgroundColor(Color.TRANSPARENT);
        }
        searchListView.getChildAt(currentlyItemSelected).setBackgroundColor(Color.CYAN);
        selectedMember = adaptater.getItem(position);
        adaptater.notifyDataSetChanged();
        previousItemSelected = position;
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putParcelableArrayListExtra("listFavRest", (ArrayList<InfoMembre>)listeSuivis);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    private class AsyncRechercheTask extends AsyncTask {
        private Context mContext;
        private ProgressDialog dialog;
        private String txtRecherche;
        public AsyncRechercheTask(Context c){
            mContext = c;
            dialog = new ProgressDialog(c);
            txtRecherche = txtSearch.getText().toString();
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
                listFromSearchInRest= httpMembreManager.getMembreFromSearch(txtRecherche);
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
            onBackPressed();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if(dialog.isShowing()){
                dialog.dismiss();
            }
            adaptater = new ArrayAdapter<InfoMembre>(mContext, android.R.layout.simple_list_item_activated_1, listFromSearchInRest);
            searchListView.setAdapter(adaptater);


        }
    }

    private class AsyncSuivreTask extends AsyncTask {
        private Context mContext;
        private ProgressDialog dialog;
        private int exception ;
        public AsyncSuivreTask(Context c){
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
                httpManager.postRequestSuivi("suivre", (Suivre) objects[0]);
            }catch(HttpClientErrorException eh){
                exception = 406;
                cancel(true);
            } catch (Exception e){
                e.printStackTrace();
                exception=1;
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
            if(exception == 406) {
                Toast.makeText(mContext, "Vous suivez déjà ce joueur", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(mContext, "Serveur injoignable", Toast.LENGTH_LONG).show();

            }
            onBackPressed();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if(dialog.isShowing()){
                dialog.dismiss();
            }
            listeSuivis.add(selectedMember);
            Toast.makeText(mContext, "Vous suivez désormais " +selectedMember.toString(), Toast.LENGTH_SHORT).show();


        }
    }
}
