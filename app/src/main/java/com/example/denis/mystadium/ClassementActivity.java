package com.example.denis.mystadium;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.denis.mystadium.Model.ClassementAdapter;
import com.example.denis.mystadium.Model.InfoEquipe;
import com.example.denis.mystadium.Request.HttpManagerEquipe;

import java.util.ArrayList;
import java.util.List;

public class ClassementActivity extends AppCompatActivity {

    private List<InfoEquipe> liste;
    private TextView txtClassement;
    private GridView gridViewClassement;
    private HttpManagerEquipe equipeManager;
    private ClassementAdapter adapter;

    private int idChampionnat;
    private String libelleChampionnat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classement);
        equipeManager = new HttpManagerEquipe();
        gridViewClassement = (GridView)findViewById(R.id.gridViewClassement);

        if (savedInstanceState == null) {
            Bundle extra = getIntent().getExtras();
            if(extra != null){
                idChampionnat = extra.getInt("idChampionnat");
                libelleChampionnat = extra.getString("libelleChampionnat");
            }
        }else{
            idChampionnat = savedInstanceState.getInt("idChampionnat");
            libelleChampionnat = savedInstanceState.getString("libelleChampionnat");
        }

        try {
            liste = equipeManager.getClassementForChampionnat(idChampionnat);
        } catch (Exception e) {

        }



        List<String> listeString = new ArrayList<String>();
        for(int i = 0; i < liste.size(); i++){
            String place = ""+(i+1);
            String nomEquipe = liste.get(i).getNomClub();
            String nbrPoint = ""+liste.get(i).getNbrPoints();
            listeString.add(place);
            listeString.add(nomEquipe);
            listeString.add(nbrPoint);

        }

        adapter = new ClassementAdapter(this,liste);
        gridViewClassement.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        txtClassement = (TextView)findViewById(R.id.txtClassement);
        txtClassement.setText(libelleChampionnat);
    }
}
