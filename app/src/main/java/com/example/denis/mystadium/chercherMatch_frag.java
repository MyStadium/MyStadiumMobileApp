package com.example.denis.mystadium;




import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;



/**
 * Created by denis on 30-11-16.
 */

public class chercherMatch_frag extends android.support.v4.app.Fragment{
    private View myView;
    private Button btnRecherche ;
    private Button btnDejaSurPlace ;
    private EditText datePickerDebut;
    private EditText datePickerFin;
    private EditText nbKilometre;
    private GPSTracker gps;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.cherchermatch, container, false);
        gps = new GPSTracker(this.getActivity().getApplicationContext());
        btnRecherche = (Button) myView.findViewById(R.id.btnRecherche);
        btnDejaSurPlace = (Button) myView.findViewById(R.id.btnLocaliserMatchProche);
        datePickerDebut = (EditText) myView.findViewById(R.id.dateDebut);
        datePickerFin = (EditText) myView.findViewById(R.id.dateFin);
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

                    String dateDebut = datePickerDebut.getText().toString();
                    String dateFin = datePickerFin.getText().toString();
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
