package com.example.denis.mystadium;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.denis.mystadium.models.Favoris;
import com.example.denis.mystadium.services.ServiceFavoris;

/**
 * Created by denis on 30-11-16.
 */

public class profil_frag extends android.support.v4.app.Fragment{

    View myView;
    private Button btnGet;
    private TextView txt;
    private EditText editText;
    Utilisateur user;
    HttpRequest requestmanager;
    ServiceFavoris serviceFavoris;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.profil, container, false);

        serviceFavoris = new ServiceFavoris();
        editText = (EditText) myView.findViewById(R.id.editText);
        txt = (TextView)myView.findViewById(R.id.textProfil);
        btnGet = (Button)myView.findViewById(R.id.btnget);
        btnGet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Favoris fav = new Favoris(1,2);
                serviceFavoris.postRequest("favoris",fav);
                /*user = new HttpRequest().doInBackground(editText.getText().toString());
                if(user != null) {
                    txt.setText("Nom:" + user.getNom() + " Prenom: " +user.getPrenom());
                }else{
                    txt.setText("USER NULL");
                }*/
            }
        });
        return myView;
    }

}
