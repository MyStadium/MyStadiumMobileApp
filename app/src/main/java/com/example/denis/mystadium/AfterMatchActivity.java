package com.example.denis.mystadium;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denis.mystadium.Model.AvgVote;
import com.example.denis.mystadium.Model.InfoMedia;
import com.example.denis.mystadium.Model.InfoMembre;
import com.example.denis.mystadium.Model.InfoRencontre;
import com.example.denis.mystadium.Model.Media;
import com.example.denis.mystadium.Model.Role;
import com.example.denis.mystadium.Model.Score;
import com.example.denis.mystadium.Model.UserOnMatch;
import com.example.denis.mystadium.Model.Vote;
import com.example.denis.mystadium.Model.VoteMVP;
import com.example.denis.mystadium.Request.HttpManagerMedia;
import com.example.denis.mystadium.Request.HttpManagerMembre;
import com.example.denis.mystadium.Request.HttpManagerRencontre;
import com.example.denis.mystadium.Request.HttpManagerScore;
import com.example.denis.mystadium.Request.HttpManagerSuivi;
import com.example.denis.mystadium.Request.HttpManagerVote;
import com.example.denis.mystadium.Request.HttpManagerVoteMVP;
import com.facebook.login.LoginResult;

import org.springframework.web.client.HttpClientErrorException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class AfterMatchActivity extends AppCompatActivity {

    private TextView txtNomDomicile;
    private TextView txtNomExterieur;
    private TextView txtDate;
    private TextView txtScore;
    private TextView txtAmbiance;
    private TextView txtNiveau;
    private TextView txtFairplay;
    private RatingBar ambianceRate;
    private RatingBar niveauRate;
    private RatingBar fairplayRate;



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
    private HttpManagerScore httpScoreManager ;
    private HttpManagerVote httpVoteManager;
    private HttpManagerVoteMVP httpVoteMvpManager;
    private HttpManagerMembre httpMembreManager;

    private List<InfoMembre> listMembersFromTeam1;
    private List<InfoMembre> listMembersFromTeam2;
    private int selectedRencontreId;
    private AvgVote avgVote;

    private SharedActivity shared;

    private int dureeMatch;
    private Date dateFinMatch;
    private GPSTracker gps;


    private int currentlyItemSelected;
    private int previousItemSelected;
    private ListView listPlayers;
    private ArrayAdapter<InfoMembre> listViewAdapter;
    private InfoMembre selectedMember;

    private UserOnMatch userOnMatch;
    private Date now;
    private Date dayAfter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_match);

        gps = new GPSTracker(this);
        now = new Date();
        dayAfter = new Date(now.getTime() + TimeUnit.DAYS.toMillis(1));

        shared = new SharedActivity(this);
        if (savedInstanceState == null) {
            Bundle extra = getIntent().getExtras();
            if (extra != null) {
                selectedRencontreId = extra.getInt("selectedRencontreId");
                dureeMatch = extra.getInt("dureeMatch");
            }
        } else {
            selectedRencontreId = savedInstanceState.getInt("selectedRencontreId");
            dureeMatch = savedInstanceState.getInt("dureeMatch");
        }
        httpRencontreManager = new HttpManagerRencontre();
        httpMediaManager = new HttpManagerMedia();
        httpVoteManager = new HttpManagerVote();
        httpMembreManager = new HttpManagerMembre();
        httpVoteMvpManager = new HttpManagerVoteMVP();

        listMembersFromTeam1 = new ArrayList<>();
        listMembersFromTeam2 = new ArrayList<>();

        for (int i = 0; i < 150; i++) {
            listeScore.add(i);
        }
        txtNomDomicile = (TextView) findViewById(R.id.txtAfterMatchNomDomicile);
        txtNomExterieur = (TextView) findViewById(R.id.txtAfterMatchNomExterieur);
        txtDate = (TextView) findViewById(R.id.txtAfterMatchDate);
        txtScore = (TextView) findViewById(R.id.txtAfterMatchScore);
        txtAmbiance = (TextView) findViewById(R.id.txtAfterMatchAmbiance);
        txtNiveau = (TextView) findViewById(R.id.txtAfterMatchNiveau);
        txtFairplay = (TextView) findViewById(R.id.txtAfterMatchFairplay);

        AsyncChargerTask task = new AsyncChargerTask(this);
        task.execute();
        btnVote = (Button) findViewById(R.id.btnVoteMVP);
        btnAmbiance = (Button) findViewById(R.id.btnAmbiance);
        btnAddScore = (Button) findViewById(R.id.btnAddScore);

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

        btnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnVoteClicked();
            }
        });

        spinnerDomicile = (Spinner) findViewById(R.id.spinnerScoreDomicile);
        spinnerExterieur = (Spinner) findViewById(R.id.spinnerScoreExterieur);


    }

    public void btnAmbianceClicked() {

        Date now = new Date();
        if (!now.after(dateFinMatch)) {
            if (userOnMatch.getBool()) {


                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = getLayoutInflater();
                View convertView = (View) inflater.inflate(R.layout.ratinglayout, null);


                ambianceRate = (RatingBar) convertView.findViewById(R.id.ratingAmbiance);
                niveauRate = (RatingBar) convertView.findViewById(R.id.ratingNiveau);
                fairplayRate = (RatingBar) convertView.findViewById(R.id.ratingFairplay);


                convertView.setPadding(50, 50, 50, 50);
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

                                AsyncAmbianceTask ambianceTask = new AsyncAmbianceTask(AfterMatchActivity.this);
                                ambianceTask.execute();

                            }
                        });

                builder.show();
            } else {
                Toast.makeText(getApplicationContext(), "Vous n'êtes pas sur le lieu du match", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "Il est trop tard voter", Toast.LENGTH_SHORT).show();
        }

    }

    public void refreshAvgVote() {
        try {
            avgVote = httpVoteManager.getAvgVoteFor(selectedRencontreId);
            txtAmbiance.setText("Ambiance: " + ((int) avgVote.getAmbiance()) + "/5");
            txtFairplay.setText("Fairplay: " + ((int) avgVote.getFairplay()) + "/5");
            txtNiveau.setText("Niveau: " + ((int) avgVote.getNiveau()) + "/5");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Impossible de rafraichir les votes", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnAddScoreClicked() {
        Date now = new Date();

        if (!now.after(dateFinMatch)) {
            if (userOnMatch.getBool()) {
                httpScoreManager = new HttpManagerScore();
                int scoreDomicile = domicileAdpater.getItem(spinnerDomicile.getSelectedItemPosition());
                int scoreExterieur = exterieurAdpater.getItem(spinnerExterieur.getSelectedItemPosition());
                Date date = new Date();
                int userID = shared.getConnectedUserId();
                boolean certifiate = false;
                if (shared.getUserRole().equals("Administrateur") || shared.getUserRole().equals("Utilisateur certifié")) {
                    certifiate = true;
                }
                Score s = new Score(1, scoreDomicile, scoreExterieur, date, certifiate, selectedRencontreId, userID);
                AsyncScoreTask scoreTask = new AsyncScoreTask(AfterMatchActivity.this);
                scoreTask.execute(s);
            } else {
                Toast.makeText(getApplicationContext(), "Vous n'êtes pas sur le lieu du match", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "Il est trop tard voter", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnVoteClicked() {
        Date now = new Date();

        if (!now.after(dateFinMatch)) {
            if (userOnMatch.getBool()) {
                final SharedActivity shared = new SharedActivity(this);




                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = getLayoutInflater();
                View convertView = (View) inflater.inflate(R.layout.playerlistlayout, null);


                listPlayers = (ListView) convertView.findViewById(R.id.listViewMVP);
                listViewAdapter = new ArrayAdapter<InfoMembre>(this, android.R.layout.simple_list_item_1, listMembersFromTeam1);
                listPlayers.setAdapter(listViewAdapter);
                listPlayers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        onItemClickMethod(position);
                    }
                });


                convertView.setPadding(50, 50, 50, 50);
                builder.setView(convertView);
                builder.setMessage("Elir MVP")
                        .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (selectedMember != null) {
                                    VoteMVP v = new VoteMVP(shared.getConnectedUserId(), selectedMember.getId(), selectedRencontreId);
                                    new AsyncVoteMVPTask(AfterMatchActivity.this).execute(v);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Veuillez sélectionner un joueur", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                builder.show();
            } else {
                Toast.makeText(getApplicationContext(), "Vous n'êtes pas sur le lieu du match", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "Il est trop tard voter", Toast.LENGTH_SHORT).show();
        }
    }

    public void onItemClickMethod(int position) {
        currentlyItemSelected = position;
        if (previousItemSelected != -1) {
            listPlayers.getChildAt(previousItemSelected).setBackgroundColor(Color.TRANSPARENT);
        }
        listPlayers.getChildAt(currentlyItemSelected).setBackgroundColor(Color.CYAN);
        selectedMember = listViewAdapter.getItem(position);
        listViewAdapter.notifyDataSetChanged();
        previousItemSelected = position;
    }

    public UserOnMatch getUserOnMatch() {
        Location location = gps.getLocation();
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        try {
            userOnMatch = httpRencontreManager.isUserOnMatch(now, dayAfter, 1, latitude, longitude, rencontre.getIdRencontre());
        } catch (Exception e) {

        }
        return userOnMatch;
    }


    private class AsyncChargerTask extends AsyncTask {
        private Context mContext;
        private ProgressDialog dialog;
        ;

        public AsyncChargerTask(Context c) {
            mContext = c;
            dialog = new ProgressDialog(c);

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Récupération des données depuis le serveur...");
            dialog.show();


        }

        @Override
        protected Object doInBackground(Object[] objects) {

            try {

                rencontre = httpRencontreManager.getInfoRencontreById(selectedRencontreId);
                listeMediaFromRest = httpMediaManager.getMediaFromRencontre(selectedRencontreId);
                avgVote = httpVoteManager.getAvgVoteFor(selectedRencontreId);
                userOnMatch = getUserOnMatch();
                listMembersFromTeam1 = httpMembreManager.getMebersFromTeam(rencontre.getIdEquipeDomicile());
                listMembersFromTeam2 = httpMembreManager.getMebersFromTeam(rencontre.getIdEquipeExterieur());


            } catch (Exception e) {
                e.printStackTrace();
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

                cancel(true);
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(mContext, "Serveur injoignable", Toast.LENGTH_LONG).show();

        }

        @Override
        protected void onPostExecute(Object o) {

            super.onPostExecute(o);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            listMembersFromTeam1.addAll(listMembersFromTeam2);
            txtAmbiance.setText("Ambiance: " + ((int) avgVote.getAmbiance()) + "/5");
            txtFairplay.setText("Fairplay: " + ((int) avgVote.getFairplay()) + "/5");
            txtNiveau.setText("Niveau: " + ((int) avgVote.getNiveau()) + "/5");
            domicileAdpater = new ArrayAdapter<Integer>(mContext, android.R.layout.simple_list_item_1, listeScore);
            exterieurAdpater = new ArrayAdapter<Integer>(mContext, android.R.layout.simple_list_item_1, listeScore);

            spinnerDomicile.setAdapter(domicileAdpater);
            spinnerExterieur.setAdapter(exterieurAdpater);


            for (int i = 0; i < listeMediaFromRest.size(); i++) {
                listeCommentaire.add(listeMediaFromRest.get(i).getLoginUser() + ": " + listeMediaFromRest.get(i).getCommentaire());
            }

            if (listeCommentaire.size() == 0) {
                listeCommentaire.add("Pas encore de commentaire pour ce match");
            }
            listeViewCommentaire = (ListView) findViewById(R.id.listAfterMatchCommentaire);
            commentaireAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, listeCommentaire);
            listeViewCommentaire.setAdapter(commentaireAdapter);


            dateFinMatch = new Date(rencontre.getDateHeure().getTime() + TimeUnit.MINUTES.toMillis(dureeMatch + 30));


            SimpleDateFormat format = new SimpleDateFormat("EE dd/MM/yyyy HH:mm");
            String dateHeure = format.format(rencontre.getDateHeure());
            txtNomDomicile.setText(rencontre.getNomEquipeDomicile());
            txtNomExterieur.setText(rencontre.getNomEquipeExterieur());
            txtDate.setText(dateHeure);
            txtScore.setText(rencontre.getScoreFinalDomicile() + " - " + rencontre.getScoreFinalExterieur());
        }
    }


    private class AsyncAmbianceTask extends AsyncTask {
        private Context mContext;
        private ProgressDialog dialog;
        private Vote v;
        private int exception;
        public AsyncAmbianceTask(Context c) {
            mContext = c;
            dialog = new ProgressDialog(c);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Récupération des données depuis le serveur...");
            dialog.show();
            v = new Vote((int) niveauRate.getRating(), (int) fairplayRate.getRating(), (int) ambianceRate.getRating(), shared.getConnectedUserId(), rencontre.getIdRencontre());

        }

        @Override
        protected Object doInBackground(Object[] params) {
            try {

                httpVoteManager.postVote(v);
                refreshAvgVote();
            } catch (HttpClientErrorException e) {
                exception = 406;
                cancel(true);

            } catch (Exception e) {
                e.printStackTrace();
                exception = 1;
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                cancel(true);
            }
            return null;
        }


        @Override
        protected void onCancelled() {
            super.onCancelled();
            if(exception == 406){
                Toast.makeText(mContext, "Vous avez déjà voté pour ce match", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(mContext, "Serveur injoignable", Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected void onPostExecute(Object o) {

            super.onPostExecute(o);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            Toast.makeText(getApplicationContext(), "Merci pour votre vote !", Toast.LENGTH_SHORT).show();

        }
    }

    private class AsyncScoreTask extends AsyncTask {
        private Context mContext;
        private ProgressDialog dialog;
        public AsyncScoreTask(Context c) {
            mContext = c;
            dialog = new ProgressDialog(c);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Récupération des données depuis le serveur...");
            dialog.show();


        }

        @Override
        protected Object doInBackground(Object[] params) {
            try {


                httpScoreManager.postScore((Score) params[0]);


            } catch (Exception e) {
                e.printStackTrace();

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                cancel(true);
            }
            return null;
        }


        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(mContext, "Serveur injoignable", Toast.LENGTH_LONG).show();


        }

        @Override
        protected void onPostExecute(Object o) {

            super.onPostExecute(o);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            Toast.makeText(getApplicationContext(), "Merci pour votre score !", Toast.LENGTH_SHORT).show();

        }
    }

    private class AsyncVoteMVPTask extends AsyncTask {
        private Context mContext;
        private ProgressDialog dialog;
        private int exception;
        public AsyncVoteMVPTask(Context c) {
            mContext = c;
            dialog = new ProgressDialog(c);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog.setMessage("Récupération des données depuis le serveur...");
            dialog.show();


        }

        @Override
        protected Object doInBackground(Object[] params) {
            try {



                    httpVoteMvpManager.postVoteMVP((VoteMVP)params[0]);

            } catch (HttpClientErrorException e) {
                exception = 406;
                cancel(true);
            } catch (Exception e) {
                e.printStackTrace();
                exception = 1;
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                cancel(true);
            }
            return null;
        }


        @Override
        protected void onCancelled() {
            super.onCancelled();
            if(exception==406) {
                Toast.makeText(getApplicationContext(), "Vous avez déjà voté pour ce match", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(mContext, "Serveur injoignable", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPostExecute(Object o) {

            super.onPostExecute(o);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            Toast.makeText(getApplicationContext(), "Merci pour votre vote !", Toast.LENGTH_SHORT).show();

        }
    }

}
