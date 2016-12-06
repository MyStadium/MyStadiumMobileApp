package com.example.denis.mystadium;




import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denis.mystadium.Model.InfoMembre;
import com.example.denis.mystadium.Model.InfoRencontre;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by denis on 30-11-16.
 */

public class chercherMatch_frag extends android.support.v4.app.Fragment{
    private View myView;
    private Button btnRecherche ;
    private Button btnDejaSurPlace ;
    private DatePicker datePickerDebut;
    private DatePicker datePickerFin;
    private EditText nbKilometre;
    private GPSTracker gps;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.cherchermatch, container, false);
        gps = new GPSTracker(this.getActivity().getApplicationContext());
        btnRecherche = (Button) myView.findViewById(R.id.btnRecherche);
        btnDejaSurPlace = (Button) myView.findViewById(R.id.btnLocaliserMatchProche);
        datePickerDebut = (DatePicker) myView.findViewById(R.id.datePickerDebut);
        datePickerFin = (DatePicker) myView.findViewById(R.id.datePickerFin);
        nbKilometre = (EditText) myView.findViewById(R.id.nbKilometre);
        btnDejaSurPlace.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                    if( gps.canGetLocation() ) {
                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();
                            Intent i = new Intent(getActivity(), ResultRechercheMatch.class);
                            i.putExtra("latitude", latitude);
                            i.putExtra("longitude", longitude);
                            startActivity(i);


                    }else{
                        gps.showSettingAlert();
                    }

            }
        });
        btnRecherche.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                    String dateDebut = datePickerDebut.toString();
                    String dateFin = datePickerFin.toString();
                    String nbKilometreString = nbKilometre.toString();
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    Intent i = new Intent(getActivity(),ResultRechercheMatch.class);
                    i.putExtra("latitude",latitude);
                    i.putExtra("longitude",longitude);
                    i.putExtra("dateDebut",dateDebut);
                    i.putExtra("dateFin",dateFin);
                    i.putExtra("nbKilometre",nbKilometreString);
                    startActivity(i);


            }
        });
        return myView;
    }
}
