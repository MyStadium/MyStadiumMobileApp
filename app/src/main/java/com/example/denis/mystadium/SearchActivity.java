package com.example.denis.mystadium;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.IDNA;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;


import com.example.denis.mystadium.Model.InfoMembre;
import com.example.denis.mystadium.Model.Suivre;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private Button btnSearch;
    private Button btnAddSearch;
    private ListView searchList;
    private EditText txtSearch;
    private HttpRequestMembre requestManager;
    private ArrayAdapter<InfoMembre> adaptater;
    private  List<InfoMembre> listFromRest;
    private InfoMembre selectedMember;

    private List<InfoMembre> listeSuivis;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestManager = new HttpRequestMembre();


        setContentView(R.layout.activity_search);
        btnSearch = (Button)findViewById(R.id.btnSearch);
        btnAddSearch = (Button)findViewById(R.id.btnAddSearch);
        searchList = (ListView) findViewById(R.id.searchList);
        txtSearch = (EditText) findViewById(R.id.txtSearch);
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
        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedMember = adaptater.getItem(position);
                Toast.makeText(getApplicationContext(), "Sélectionné: "+adaptater.getItem(position).getNom() +" "+adaptater.getItem(position).getPrenom(), Toast.LENGTH_SHORT).show();
                searchList.setItemChecked(position, true);
                view.setBackgroundColor(Color.CYAN);
                adaptater.notifyDataSetChanged();
            }
        });
        searchList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

    }

    public void btnSearchClicked(){
        listFromRest= requestManager.getMembreFromSearch(txtSearch.getText().toString());
        adaptater = new ArrayAdapter<InfoMembre>(this, android.R.layout.simple_list_item_activated_1, listFromRest);
        searchList.setAdapter(adaptater);
    }

    public void btnAddSearchClicked(){
        if(selectedMember != null){
            SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(this);
            int idUtilisateur = shared.getInt("connectedUserId", 0);
            Suivre suivi = new Suivre(selectedMember.getId(), idUtilisateur);
            requestManager.addFav(suivi);
        }else{
            Toast.makeText(this, "Aucun membre selectionné", Toast.LENGTH_SHORT).show();
        }
    }
}
