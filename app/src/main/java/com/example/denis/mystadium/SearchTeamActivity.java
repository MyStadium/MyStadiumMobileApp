package com.example.denis.mystadium;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.denis.mystadium.Model.Favoris;
import com.example.denis.mystadium.Model.InfoEquipe;
import com.example.denis.mystadium.Model.InfoMembre;
import com.example.denis.mystadium.Model.Suivre;
import com.example.denis.mystadium.Request.HttpManagerEquipe;
import com.example.denis.mystadium.Request.HttpManagerFavoris;
import com.example.denis.mystadium.Request.HttpManagerMembre;
import com.example.denis.mystadium.Request.HttpManagerSuivi;

import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

public class SearchTeamActivity extends AppCompatActivity {

    private Button btnSearch;
    private Button btnAddSearch;
    private ListView searchListView;
    private EditText txtSearch;

    private ArrayAdapter<InfoEquipe> adaptater;
    private  List<InfoEquipe> listFromSearchInRest;
    private InfoEquipe selectedTeam;

    private List<InfoEquipe> listeSuivis;
    private HttpManagerEquipe httpEquipeManager;
    private HttpManagerFavoris httpFavorisManager;

    private int previousItemSelected;
    private int currentlyItemSelected;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_team);
        //variables instanciation
        btnSearch = (Button)findViewById(R.id.btnSearchTeam);
        btnAddSearch = (Button)findViewById(R.id.btnAddSearchTeam);
        searchListView = (ListView) findViewById(R.id.searchListTeam);
        txtSearch = (EditText) findViewById(R.id.txtSearchTeam);

        httpEquipeManager = new HttpManagerEquipe();
        httpFavorisManager = new HttpManagerFavoris();


        previousItemSelected = -1;
        currentlyItemSelected = -1;

        //listeners
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
                listeSuivis=(ArrayList<InfoEquipe>)extra.getSerializable("listFavTeamRest");
            }
        } else {
            listeSuivis=(ArrayList<InfoEquipe>)savedInstanceState.getSerializable("listFavTeamRest");
        }

    }

    public void btnSearchClicked(){
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        try{
            listFromSearchInRest= httpEquipeManager.getTeamFromSearch(txtSearch.getText().toString());
            adaptater = new ArrayAdapter<InfoEquipe>(this, android.R.layout.simple_list_item_activated_1, listFromSearchInRest);
            searchListView.setAdapter(adaptater);
        }catch(Exception e){
            Toast.makeText(this, "Erreur lors de la recherche", Toast.LENGTH_LONG).show();
        }

    }

    public void btnAddSearchClicked(){
        if(selectedTeam != null){
            SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(this);
            int idUtilisateur = shared.getInt("connectedUserId", 0);
            Favoris f = new Favoris(idUtilisateur, selectedTeam.getIdEquipe());
            try{
                httpFavorisManager.postFavoris(f);
                listeSuivis.add(selectedTeam);
                Toast.makeText(this, "Vous suivez désormais " +selectedTeam.toString(), Toast.LENGTH_SHORT).show();
            }catch(HttpClientErrorException e){
                Toast.makeText(this, "Vous suivez déjà cette équipe", Toast.LENGTH_SHORT).show();
            }
            catch(Exception e){
                Toast.makeText(this, "Erreur lors de l'ajout du suivi", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Aucune équipe selectionné", Toast.LENGTH_SHORT).show();
        }
    }

    public void onItemClickMethod(int position){
        currentlyItemSelected = position;
        if(previousItemSelected != -1){
            searchListView.getChildAt(previousItemSelected).setBackgroundColor(Color.TRANSPARENT);
        }
        searchListView.getChildAt(currentlyItemSelected).setBackgroundColor(Color.CYAN);
        selectedTeam = adaptater.getItem(position);
        adaptater.notifyDataSetChanged();
        previousItemSelected = position;
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putParcelableArrayListExtra("listFavTeamRest", (ArrayList<InfoEquipe>)listeSuivis);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
