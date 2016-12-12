package com.example.denis.mystadium;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.denis.mystadium.Model.InfoEquipe;
import com.example.denis.mystadium.Model.InfoMedia;
import com.example.denis.mystadium.Model.InfoRencontre;
import com.example.denis.mystadium.Model.Media;
import com.example.denis.mystadium.Model.Vote;
import com.example.denis.mystadium.Request.HttpManager;
import com.example.denis.mystadium.Request.HttpManagerEquipe;
import com.example.denis.mystadium.Request.HttpManagerMedia;
import com.example.denis.mystadium.Request.HttpManagerRencontre;

import org.springframework.web.client.HttpClientErrorException;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class DuringMatchActivity extends AppCompatActivity {

    private TextView txtDate;
    private TextView txtScore;
    private TextView txtNomDomicile;
    private TextView txtNomExterieur;
    private TextView txtJournee;

    private Button btnClassement;
    private Button btnAddComm;

    private ListView listViewCommentaires;

    private HttpManagerRencontre httpRencontreManager;
    private HttpManagerEquipe httpEquipeManager;
    private HttpManagerMedia httpMediaManager;

    private int selectedRencontreId;

    private ArrayList<String> listeCommentaires;

    private ArrayAdapter<String> commentaireAdapter;

    private InfoRencontre rencontre;
    private InfoEquipe equipeDomicile;
    private List<InfoMedia> mediaFromRest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_during_match);
        txtDate = (TextView)findViewById(R.id.txtDuringDate);
        txtScore = (TextView)findViewById(R.id.txtDuringScore);
        txtNomDomicile = (TextView)findViewById(R.id.txtDuringNomDomicile);
        txtNomExterieur = (TextView)findViewById(R.id.txtDuringNomExterieur);
        txtJournee = (TextView)findViewById(R.id.txtDuringJournee);

        btnClassement = (Button)findViewById(R.id.btnDuringClassement);
        btnAddComm = (Button)findViewById(R.id.btnDuringCOmm);

        listViewCommentaires = (ListView)findViewById(R.id.duringListCom);
        listeCommentaires = new ArrayList<String>();

        httpRencontreManager = new HttpManagerRencontre();
        httpEquipeManager = new HttpManagerEquipe();
        httpMediaManager = new HttpManagerMedia();

        if (savedInstanceState == null) {
            Bundle extra = getIntent().getExtras();
            if(extra != null){
                selectedRencontreId = extra.getInt("selectedRencontreId");
            }
        }else{
            selectedRencontreId = savedInstanceState.getInt("selectedRencontreId");
        }

        try{
            rencontre = httpRencontreManager.getInfoRencontreById(selectedRencontreId);
            equipeDomicile = httpEquipeManager.getEquipeById(rencontre.getIdEquipeDomicile());
            SimpleDateFormat format = new SimpleDateFormat("EE dd/MM/yyyy hh:mm");
            String dateHeure = format.format(rencontre.getDateHeure());
            txtDate.setText(dateHeure);
            txtScore.setText(rencontre.getScoreFinalDomicile()+"-"+rencontre.getScoreFinalExterieur());
            txtNomDomicile.setText(rencontre.getNomEquipeDomicile());
            txtNomExterieur.setText(rencontre.getNomEquipeExterieur());
            txtJournee.setText("Journée "+rencontre.getJournee());
            mediaFromRest = httpMediaManager.getMediaFromRencontre(selectedRencontreId);
        }catch(Exception e){
            Toast.makeText(this, "Impossible de charger les infos de la rencontre", Toast.LENGTH_LONG).show();
        }

        for(int i = 0; i < mediaFromRest.size(); i++){
            listeCommentaires.add(mediaFromRest.get(i).getLoginUser() +": "+mediaFromRest.get(i).getCommentaire());
        }
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
    }

    public void btnClassementClicked(){
        Intent intent = new Intent(this, ClassementActivity.class);
        intent.putExtra("idChampionnat", rencontre.getIdChampionnat());
        intent.putExtra("libelleChampionnat", equipeDomicile.getCategorieAge() +" "+rencontre.getLibelleChampionnat());
        startActivity(intent);
    }

    public void btnAddComClicked() {

        final SharedActivity shared = new SharedActivity(this);
        HttpManagerMedia httmMediaManager = new HttpManagerMedia();

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
                            try{
                                httpMediaManager.postMedia(m);
                                listeCommentaires.add(shared.getConnectedUserLogin()+": "+m.getCommentaire());
                                Toast.makeText(getApplicationContext(), "Merci pour votre commentaire", Toast.LENGTH_LONG).show();
                            }catch(Exception e){
                                Toast.makeText(getApplicationContext(), "Erreur lors de l'ajout de votre commentaire", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Veuillez écrire un commentaire plus long", Toast.LENGTH_LONG).show();
                        }

                    }
                });

        builder.show();
    }
}
