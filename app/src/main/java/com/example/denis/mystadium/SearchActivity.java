package com.example.denis.mystadium;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.IDNA;
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
        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
}
