package com.example.denis.mystadium;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.denis.mystadium.Model.Utilisateur;
import com.example.denis.mystadium.Request.HttpRequestUser;

/**
 * Created by denis on 30-11-16.
 */

public class profil_frag extends android.support.v4.app.Fragment{

    View myView;
    private Button btnGet;
    private TextView txt;
    Utilisateur user;
    HttpRequestUser requestmanager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.profil, container, false);
        final GPSTracker gps = new GPSTracker(getContext());

        txt = (TextView)myView.findViewById(R.id.textProfil);
        btnGet = (Button)myView.findViewById(R.id.btnget);
        btnGet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Location location = gps.getLocation();
                if(gps.canGetlocation()){
                    txt.setText("LAT:" +location.getLatitude()+ "\nLONG:"+location.getLongitude());
                }else{
                    txt.setText("Activez la location svp");
                    Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(i);
                }

            }
        });
        return myView;
    }

}
