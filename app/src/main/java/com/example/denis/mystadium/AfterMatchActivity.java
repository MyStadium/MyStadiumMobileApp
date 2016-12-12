package com.example.denis.mystadium;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denis.mystadium.Model.AvgVote;
import com.example.denis.mystadium.Model.InfoMedia;
import com.example.denis.mystadium.Model.InfoRencontre;
import com.example.denis.mystadium.Model.Media;
import com.example.denis.mystadium.Model.Score;
import com.example.denis.mystadium.Model.Vote;
import com.example.denis.mystadium.Request.HttpManagerMedia;
import com.example.denis.mystadium.Request.HttpManagerRencontre;
import com.example.denis.mystadium.Request.HttpManagerScore;
import com.example.denis.mystadium.Request.HttpManagerVote;

import org.springframework.web.client.HttpClientErrorException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AfterMatchActivity extends AppCompatActivity {

    private TextView txtNomDomicile;
    private TextView txtNomExterieur;
    private TextView txtDate;
    private TextView txtScore;
    private TextView txtAmbiance;
    private TextView txtNiveau;
    private TextView txtFairplay;

    private final HttpManagerVote httpVoteManager = new HttpManagerVote();

    private ArrayList<Integer> listeScore = new ArrayList<Integer>();

    private ListView listeViewCommentaire;
    private ArrayAdapter<String> commentaireAdapter;
    private ArrayList<String> listeCommentaire = new ArrayList<String>();

    private List<InfoMedia> listeMediaFromRest;

    private Button btnVote;
    private Button btnAmbiance;
    private Button btnAddScore;

    private Spinner spinnerDomicile;
    private Spinner spinnerExterieur;

    private ArrayAdapter<Integer> domicileAdpater;
    private ArrayAdapter<Integer> exterieurAdpater;

    private InfoRencontre rencontre;
    private HttpManagerRencontre httpRencontreManager;
    private HttpManagerMedia httpMediaManager;

    private int selectedRencontreId;
    private AvgVote avgVote;

    private SharedActivity shared;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_match);

        shared = new SharedActivity(this);
        if (savedInstanceState == null) {
            Bundle extra = getIntent().getExtras();
            if(extra != null){
                selectedRencontreId = extra.getInt("selectedRencontreId");
            }
        }else{
            selectedRencontreId = savedInstanceState.getInt("selectedRencontreId");
        }
        httpRencontreManager = new HttpManagerRencontre();
        httpMediaManager = new HttpManagerMedia();

        for(int i = 0; i < 150; i++){
            listeScore.add(i);
        }

        try{
            rencontre = httpRencontreManager.getInfoRencontreById(selectedRencontreId);
            listeMediaFromRest = httpMediaManager.getMediaFromRencontre(selectedRencontreId);
            avgVote = httpVoteManager.getAvgVoteFor(selectedRencontreId);
        }catch(Exception e){
            e.printStackTrace();
        }



        txtNomDomicile = (TextView)findViewById(R.id.txtAfterMatchNomDomicile);
        txtNomExterieur = (TextView)findViewById(R.id.txtAfterMatchNomExterieur);
        txtDate = (TextView)findViewById(R.id.txtAfterMatchDate);
        txtScore = (TextView)findViewById(R.id.txtAfterMatchScore);
        txtAmbiance = (TextView)findViewById(R.id.txtAfterMatchAmbiance);
        txtNiveau = (TextView)findViewById(R.id.txtAfterMatchNiveau);
        txtFairplay = (TextView)findViewById(R.id.txtAfterMatchFairplay);

        txtAmbiance.setText("Ambiance: "+ ((int) avgVote.getAmbiance())+"/5");
        txtFairplay.setText("Fairplay: "+((int) avgVote.getFairplay())+"/5");
        txtNiveau.setText("Niveau: "+((int) avgVote.getNiveau())+"/5");

        btnVote = (Button)findViewById(R.id.btnVoteMVP);
        btnAmbiance = (Button)findViewById(R.id.btnAmbiance);
        btnAddScore = (Button)findViewById(R.id.btnAddScore);

        btnAmbiance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAmbianceClicked();
            }
        });

        btnAddScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddScoreClicked();
            }
        });

        spinnerDomicile = (Spinner)findViewById(R.id.spinnerScoreDomicile);
        spinnerExterieur = (Spinner)findViewById(R.id.spinnerScoreExterieur);

        domicileAdpater = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, listeScore);
        exterieurAdpater = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, listeScore);

        spinnerDomicile.setAdapter(domicileAdpater);
        spinnerExterieur.setAdapter(exterieurAdpater);


        for(int i = 0; i < listeMediaFromRest.size(); i++){
            listeCommentaire.add(listeMediaFromRest.get(i).getLoginUser()+": " +listeMediaFromRest.get(i).getCommentaire());
        }

        if(listeCommentaire.size() == 0){
            listeCommentaire.add("Pas encore de commentaire pour ce match");
        }
        listeViewCommentaire = (ListView)findViewById(R.id.listAfterMatchCommentaire);
        commentaireAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listeCommentaire);
        listeViewCommentaire.setAdapter(commentaireAdapter);



        SimpleDateFormat format = new SimpleDateFormat("EE dd/MM/yyyy hh:mm");
        String dateHeure = format.format(rencontre.getDateHeure());
        txtNomDomicile.setText(rencontre.getNomEquipeDomicile());
        txtNomExterieur.setText(rencontre.getNomEquipeExterieur());
        txtDate.setText(dateHeure);
        txtScore.setText(rencontre.getScoreFinalDomicile()+" - "+rencontre.getScoreFinalExterieur());

    }

    public void btnAmbianceClicked(){
        final RatingBar ambianceRate;
        final RatingBar niveauRate;
        final RatingBar fairplayRate;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.ratinglayout, null);




        ambianceRate = (RatingBar)convertView.findViewById(R.id.ratingAmbiance);
        niveauRate = (RatingBar)convertView.findViewById(R.id.ratingNiveau);
        fairplayRate = (RatingBar)convertView.findViewById(R.id.ratingFairplay);



        convertView.setPadding(50,50,50,50);
        builder.setView(convertView);
        builder.setMessage("Vote d'ambiance")
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            final Vote v = new Vote((int)niveauRate.getRating(), (int)fairplayRate.getRating(), (int)ambianceRate.getRating(), shared.getConnectedUserId(), rencontre.getIdRencontre());
                            httpVoteManager.postVote(v);
                            Toast.makeText(getApplicationContext(), "Merci pour votre vote !", Toast.LENGTH_SHORT).show();
                            refreshAvgVote();
                        }catch(HttpClientErrorException e){
                            Toast.makeText(getApplicationContext(), "Vous avez déjà voté pour ce match", Toast.LENGTH_SHORT).show();
                        }catch(Exception e){
                            Toast.makeText(getApplicationContext(), "Erreur lors de l'envoie du vote", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        builder.show();
    }

    public void refreshAvgVote(){
        try{
            avgVote = httpVoteManager.getAvgVoteFor(selectedRencontreId);
            txtAmbiance.setText("Ambiance: "+ ((int) avgVote.getAmbiance())+"/5");
            txtFairplay.setText("Fairplay: "+((int) avgVote.getFairplay())+"/5");
            txtNiveau.setText("Niveau: "+((int) avgVote.getNiveau())+"/5");
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Impossible de rafraichir les votes", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnAddScoreClicked(){
        HttpManagerScore httpScoreManager = new HttpManagerScore();
        int scoreDomicile = domicileAdpater.getItem(spinnerDomicile.getSelectedItemPosition());
        int scoreExterieur = exterieurAdpater.getItem(spinnerExterieur.getSelectedItemPosition());
        Date date = new Date();
        int userID = shared.getConnectedUserId();
        Score s = new Score(1, scoreDomicile, scoreExterieur, date, false, selectedRencontreId, userID);
        try{
            httpScoreManager.postVote(s);
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Impossible d'envoyer votre score", Toast.LENGTH_SHORT).show();
        }
    }
}
