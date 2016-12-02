package com.example.denis.mystadium;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by denis on 30-11-16.
 */

public class chercherMatch_frag extends android.support.v4.app.Fragment{
    View myView;
    Button btn = null;
    private TextView match;

    GPSTracker gps;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.cherchermatch, container, false);
        btn = (Button) myView.findViewById(R.id.button);
        match = (TextView) myView.findViewById(R.id.match);
        match.setText("SALUT");
        System.out.println("toz");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gps = new GPSTracker(getContext());
                match.setText("LAT:"+gps.getLatitude()+"\nLONG:"+gps.getLongitude());
            }
        });
        return myView;
    }
}
