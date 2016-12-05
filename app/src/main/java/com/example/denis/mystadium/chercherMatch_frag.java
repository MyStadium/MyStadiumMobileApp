package com.example.denis.mystadium;




import android.os.Build;
import android.os.Bundle;
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
        gps = new GPSTracker(getContext());
        btnRecherche = (Button) myView.findViewById(R.id.btnRecherche);
        btnDejaSurPlace = (Button) myView.findViewById(R.id.btnLocaliserMatchProche);
        datePickerDebut = (DatePicker) myView.findViewById(R.id.datePickerDebut);
        datePickerFin = (DatePicker) myView.findViewById(R.id.datePickerFin);
        nbKilometre = (EditText) myView.findViewById(R.id.nbKilometre);
        btnDejaSurPlace.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    Date date = new Date();
                    String dateDebut = new SimpleDateFormat("yyyy-MM-dd").format(date);
                    Date dayAfter = new Date(date.getTime() + TimeUnit.DAYS.toMillis(1));
                    String dateFin = new SimpleDateFormat("yyyy-MM-dd").format(dayAfter); ;
                    String nbKilometreString = "0.5";
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    String url = "http://192.168.128.13:8081/MyStadium-REST-DEVILLE-BRONSART/resources/rencontre/find/"+dateDebut+"/"+dateFin+"/"+nbKilometreString+"/"+latitude+"/"+longitude;
                    RestTemplate restTemplate = new RestTemplate();
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());


                    ResponseEntity<InfoRencontre[]> responseEntity = restTemplate.getForEntity(url, InfoRencontre[].class);
                    List<InfoRencontre> liste = new ArrayList<InfoRencontre>(Arrays.asList( responseEntity.getBody()));

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("MainActivity", e.getMessage(), e);

                }
            }
        });
        btnRecherche.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    String dateDebut = datePickerDebut.toString();
                    String dateFin = datePickerFin.toString();
                    String nbKilometreString = nbKilometre.toString();
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    String url = "http://192.168.128.13:8081/MyStadium-REST-DEVILLE-BRONSART/resources/rencontre/find/"+dateDebut+"/"+dateFin+"/"+nbKilometreString+"/"+latitude+"/"+longitude;
                    RestTemplate restTemplate = new RestTemplate();
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());


                    ResponseEntity<InfoRencontre[]> responseEntity = restTemplate.getForEntity(url, InfoRencontre[].class);
                    List<InfoRencontre> liste = new ArrayList<InfoRencontre>(Arrays.asList( responseEntity.getBody()));

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("MainActivity", e.getMessage(), e);

                }
            }
        });
        return myView;
    }
}
