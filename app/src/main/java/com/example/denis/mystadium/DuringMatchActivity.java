package com.example.denis.mystadium;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denis.mystadium.Model.InfoEquipe;
import com.example.denis.mystadium.Model.InfoMedia;
import com.example.denis.mystadium.Model.InfoMembre;
import com.example.denis.mystadium.Model.InfoRencontre;
import com.example.denis.mystadium.Model.Media;
import com.example.denis.mystadium.Model.Score;
import com.example.denis.mystadium.Model.UserOnMatch;
import com.example.denis.mystadium.Model.Vote;
import com.example.denis.mystadium.Request.HttpManager;
import com.example.denis.mystadium.Request.HttpManagerEquipe;
import com.example.denis.mystadium.Request.HttpManagerMedia;
import com.example.denis.mystadium.Request.HttpManagerRencontre;
import com.example.denis.mystadium.Request.HttpManagerScore;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.springframework.web.client.HttpClientErrorException;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class DuringMatchActivity extends AppCompatActivity {

    private TextView txtDate;
    private TextView txtScore;
    private TextView txtNomDomicile;
    private TextView txtNomExterieur;
    private TextView txtJournee;

    private Button btnClassement;
    private Button btnAddComm;
    private Button btnShare;
    private Button addScore;
    private Button btnAutreMatch;
    private ListView listViewCommentaires;

    private HttpManagerRencontre httpRencontreManager;
    private HttpManagerEquipe httpEquipeManager;
    private HttpManagerMedia httpMediaManager;
    private HttpManagerScore httpScoreManager;

    private int selectedRencontreId;

    private ArrayList<String> listeCommentaires;

    private ArrayAdapter<String> commentaireAdapter;

    private InfoRencontre rencontre;
    private InfoEquipe equipeDomicile;
    private InfoEquipe equipeExterieur;
    private List<InfoMedia> mediaFromRest;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;
    private SharedActivity shared;

    private Spinner spinnerScoreDomicile;
    private Spinner spinnerScoreExterieur;
    private ArrayAdapter<Integer> domicileAdpater;
    private ArrayAdapter<Integer> exterieurAdpater;
    private ArrayList<Integer> listeScore = new ArrayList<>();

    private UserOnMatch userOnMatch;
    private Date now;
    private Date dayAfter;
    private GPSTracker gps;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_during_match);
        txtDate = (TextView)findViewById(R.id.txtDuringDate);
        txtScore = (TextView)findViewById(R.id.txtDuringScore);
        txtNomDomicile = (TextView)findViewById(R.id.txtDuringNomDomicile);
        txtNomExterieur = (TextView)findViewById(R.id.txtDuringNomExterieur);
        txtJournee = (TextView)findViewById(R.id.txtDuringJournee);

        shared = new SharedActivity(this);
        gps = new GPSTracker(this);
        now = new Date();
        dayAfter = new Date(now.getTime() + TimeUnit.DAYS.toMillis(1));

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        btnClassement = (Button)findViewById(R.id.btnDuringClassement);
        btnAddComm = (Button)findViewById(R.id.btnDuringCOmm);
        btnShare = (Button)findViewById(R.id.share_btn);
        addScore = (Button)findViewById(R.id.btnDuringAddScore);
        btnAutreMatch = (Button)findViewById(R.id.btnAUtreMatch);

        listViewCommentaires = (ListView)findViewById(R.id.duringListCom);
        listeCommentaires = new ArrayList<String>();

        httpRencontreManager = new HttpManagerRencontre();
        httpEquipeManager = new HttpManagerEquipe();
        httpMediaManager = new HttpManagerMedia();


        for(int i = 0; i < 150; i++){
            listeScore.add(i);
        }

        spinnerScoreDomicile = (Spinner)findViewById(R.id.spinnerDuringDomicile);
        spinnerScoreExterieur = (Spinner)findViewById(R.id.spinnerDuringExterieur);
        domicileAdpater = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, listeScore);
        exterieurAdpater = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, listeScore);
        spinnerScoreDomicile.setAdapter(domicileAdpater);
        spinnerScoreExterieur.setAdapter(exterieurAdpater);

        if (savedInstanceState == null) {
            Bundle extra = getIntent().getExtras();
            if(extra != null){
                selectedRencontreId = extra.getInt("selectedRencontreId");
            }
        }else{
            selectedRencontreId = savedInstanceState.getInt("selectedRencontreId");
        }
        new AsyncChargerTask(DuringMatchActivity.this).execute();

        commentaireAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listeCommentaires);
        listViewCommentaires.setAdapter(commentaireAdapter);

        btnClassement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnClassementClicked();
            }
        });

        btnAddComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddComClicked();
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareOnFacebook();
            }
        });
        addScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddScoreClicked();
            }
        });

        btnAutreMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAutreMatchClicked();
            }
        });

    }

    public void btnClassementClicked(){
        Intent intent = new Intent(this, ClassementActivity.class);
        intent.putExtra("idChampionnat", rencontre.getIdChampionnat());
        intent.putExtra("libelleChampionnat", equipeDomicile.getCategorieAge() +" "+rencontre.getLibelleChampionnat());
        startActivity(intent);
    }

    public void btnAddComClicked() {
        if(userOnMatch.getBool()){


            final EditText txtCom;
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View convertView = (View) inflater.inflate(R.layout.addcommlayout, null);
            txtCom = (EditText) convertView.findViewById(R.id.longTextFieldAddCom);
            convertView.setPadding(50, 50, 50, 50);
            builder.setView(convertView);
            builder.setMessage("Ajouter un commentaire")
                    .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(!txtCom.getText().toString().equals("")){
                                Media m = new Media(1, "", txtCom.getText().toString(), shared.getConnectedUserId(), selectedRencontreId);
                                new AsyncCommenterTask(DuringMatchActivity.this).execute(m);
                            }else{
                                Toast.makeText(getApplicationContext(), "Veuillez écrire un commentaire plus long", Toast.LENGTH_LONG).show();
                            }

                        }
                    });

            builder.show();
        }else{
            Toast.makeText(getApplicationContext(), "Vous n'êtes pas sur le lieu du match", Toast.LENGTH_LONG).show();
        }


    }

    public void btnAddScoreClicked(){
        if(userOnMatch.getBool()){
            httpScoreManager = new HttpManagerScore();
            int scoreDomicile = domicileAdpater.getItem(spinnerScoreDomicile.getSelectedItemPosition());
            int scoreExterieur = exterieurAdpater.getItem(spinnerScoreExterieur.getSelectedItemPosition());
            Date date = new Date();
            boolean certifiate  =false;
            if(shared.getUserRole().equals("Administrateur") || shared.getUserRole().equals("Utilisateur certifié")){
                certifiate = true;
            }
            Score s = new Score(1, scoreDomicile, scoreExterieur, date, certifiate, selectedRencontreId, shared.getConnectedUserId());
            new AsyncScoreTask(DuringMatchActivity.this).execute(s);
        }else{
            Toast.makeText(getApplicationContext(), "Vous n'êtes pas sur le lieu du match", Toast.LENGTH_SHORT).show();
        }


    }

    public void btnAutreMatchClicked(){
        Intent i = new Intent(this, AutreMatchActivity.class);
        i.putExtra("idChampionnat", rencontre.getIdChampionnat());
        i.putExtra("journee", rencontre.getJournee());
        startActivity(i);
    }

    public UserOnMatch getUserOnMatch(){
        Location location = gps.getLocation();
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        try{
            userOnMatch = httpRencontreManager.isUserOnMatch(now, dayAfter, 1, latitude, longitude, rencontre.getIdRencontre());
        }catch(Exception e){

        }
        return userOnMatch;
    }

    public void shareOnFacebook(){
        if(userOnMatch.getBool()){
            if(ShareDialog.canShow(ShareLinkContent.class)){
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentTitle("MyStadium App")
                        .setContentDescription("Salut, je regarde le match "+ equipeDomicile.getNomClub() +" - "+ equipeExterieur.getNomClub()+" dans la catégorie :"+equipeDomicile.getCategorieAge())
                        .setContentUrl(Uri.parse("https://www.google.be"))
                        .build();

                shareDialog.show(content);
            }
        }else{
            Toast.makeText(getApplicationContext(), "Vous n'êtes pas sur le lieu du match", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    private class AsyncChargerTask extends AsyncTask {
        private Context mContext;
        private ProgressDialog dialog;
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
        protected Object doInBackground(Object[] params) {
            try {
                rencontre = httpRencontreManager.getInfoRencontreById(selectedRencontreId);
                equipeDomicile = httpEquipeManager.getEquipeById(rencontre.getIdEquipeDomicile());
                equipeExterieur =  httpEquipeManager.getEquipeById(rencontre.getIdEquipeExterieur());
                mediaFromRest = httpMediaManager.getMediaFromRencontre(selectedRencontreId);
                userOnMatch = getUserOnMatch();
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
            SimpleDateFormat format = new SimpleDateFormat("EE dd/MM/yyyy hh:mm");
            String dateHeure = format.format(rencontre.getDateHeure());
            txtDate.setText(dateHeure);
            txtScore.setText(rencontre.getScoreFinalDomicile()+"-"+rencontre.getScoreFinalExterieur());
            txtNomDomicile.setText(rencontre.getNomEquipeDomicile());
            txtNomExterieur.setText(rencontre.getNomEquipeExterieur());
            txtJournee.setText("Journée "+rencontre.getJournee());
            for(int i = 0; i < mediaFromRest.size(); i++){
                listeCommentaires.add(mediaFromRest.get(i).getLoginUser() +": "+mediaFromRest.get(i).getCommentaire());
            }

        }
    }

    private class AsyncCommenterTask extends AsyncTask<Media,Void,Media> {
        private Context mContext;
        private ProgressDialog dialog;
        public AsyncCommenterTask(Context c) {
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
        protected Media doInBackground(Media[] params) {
            try {
                httpMediaManager.postMedia(params[0]);
            } catch (Exception e) {
                e.printStackTrace();

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                cancel(true);
            }
            return (Media)params[0];
        }


        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(mContext, "Serveur injoignable", Toast.LENGTH_LONG).show();


        }

        @Override
        protected void onPostExecute(Media o) {

            super.onPostExecute(o);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            listeCommentaires.add(shared.getConnectedUserLogin()+": "+o.getCommentaire());
            Toast.makeText(getApplicationContext(), "Merci pour votre commentaire", Toast.LENGTH_LONG).show();


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
}
