package com.example.denis.mystadium;




import android.Manifest;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;


import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by denis on 30-11-16.
 */

public class chercherMatch_frag extends android.support.v4.app.Fragment {
    private View myView;
    private Button btnRecherche ;
    private Button btnDejaSurPlace ;
    private Button btnChoisirDateDebut;
    private Button btnChoisirDateFin;
    private TextView datePickerDebut;
    private TextView datePickerFin;
    private EditText nbKilometre;
    private GPSTracker gps;
    private String dateDebut;
    private String dateFin;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.cherchermatch, container, false);
        gps = new GPSTracker(getContext());
        btnRecherche = (Button) myView.findViewById(R.id.btnRecherche);
        btnDejaSurPlace = (Button) myView.findViewById(R.id.btnLocaliserMatchProche);
        btnChoisirDateDebut = (Button) myView.findViewById(R.id.btnChoisirDateDebut);
        btnChoisirDateFin = (Button) myView.findViewById(R.id.btnChoisirDateFin);
        datePickerDebut = (TextView) myView.findViewById(R.id.datePickerDebut);
        datePickerFin = (TextView) myView.findViewById(R.id.datePickerFin);
        nbKilometre = (EditText) myView.findViewById(R.id.nbKilometre);
        dateDebut="";
        dateFin ="";
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this.getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, getTargetRequestCode());
            }
        }
            btnDejaSurPlace.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        if (gps.canGetlocation()) {
                            Location location = gps.getLocation();
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            Intent i = new Intent(getContext(), ResultRechercheMatch.class);
                            i.putExtra("latitude", latitude);
                            i.putExtra("longitude", longitude);
                            startActivity(i);
                        } else {
                            gps.showSettingAlert();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("MainActivity", e.getMessage(), e);

                    }
                }
            });
            btnRecherche.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!dateDebut.equals("") && !dateFin.equals("") && !nbKilometre.getText().toString().equals("")) {
                        try {
                            if (gps.canGetlocation()) {
                                String nbKilometreString = nbKilometre.getText().toString();
                                double latitude = gps.getLatitude();
                                double longitude = gps.getLongitude();
                                Intent i = new Intent(getContext(), ResultRechercheMatch.class);
                                i.putExtra("latitude", latitude);
                                i.putExtra("longitude", longitude);
                                i.putExtra("dateDebut", dateDebut);
                                i.putExtra("dateFin", dateFin);
                                i.putExtra("nbKilometre", nbKilometreString);
                                startActivity(i);

                            } else {
                                gps.showSettingAlert();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("MainActivity", e.getMessage(), e);
                        }
                    }else{
                        Toast.makeText(getContext(), "Veuillez compléter tout les champs", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            btnChoisirDateFin.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    FragmentManager fm = getActivity().getFragmentManager();
                    DialogFragment newFragment = new DatePickerFragment(){
                        @Override
                        public void onDateSet(DatePicker view,int year,int month,int day){
                            datePickerFin.setText(""+day+"-"+(month+1)+"-"+year);
                            dateFin= year+"-"+(month+1)+"-"+day;
                        }
                    };
                    newFragment.show(fm,"datePickerFin");
                }
            });
        btnChoisirDateDebut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentManager fm = getActivity().getFragmentManager();
                DialogFragment newFragment = new DatePickerFragment(){
                    @Override
                    public void onDateSet(DatePicker view,int year,int month,int day){
                        datePickerDebut.setText(""+day+"-"+(month+1)+"-"+year);
                        dateDebut= year+"-"+(month+1)+"-"+day;
                    }
                };
                newFragment.show(fm,"datePickerDebut");
            }
        });
        return myView;
    }


}
