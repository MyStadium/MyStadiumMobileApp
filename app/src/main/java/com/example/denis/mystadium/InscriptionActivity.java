package com.example.denis.mystadium;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.denis.mystadium.Model.InfoEquipe;
import com.example.denis.mystadium.Model.Utilisateur;
import com.example.denis.mystadium.Request.HttpManagerUtilisateur;

import org.springframework.web.client.HttpClientErrorException;

import java.security.MessageDigest;
import java.util.Formatter;

public class InscriptionActivity extends AppCompatActivity {

    private EditText txtNom;
    private EditText txtPrenom;
    private EditText txtMail;
    private EditText txtPass;
    private EditText txtPassConf;
    private Button btnValider;
    private EditText txtLogin;
    private  HttpManagerUtilisateur manager ;
    private SharedActivity shared;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);



        txtNom = (EditText)findViewById(R.id.editInscriptionNom);
        txtPrenom = (EditText)findViewById(R.id.editInscriptionPrenom);
        txtMail = (EditText)findViewById(R.id.editInscriptionMail);
        txtPass = (EditText)findViewById(R.id.editInscriptionPass);
        txtPassConf = (EditText)findViewById(R.id.editInscriptionPassConf);
        txtLogin = (EditText)findViewById(R.id.editInscriptionLogin);
        manager = new HttpManagerUtilisateur();
        btnValider = (Button) findViewById(R.id.btnInscriptionValider);

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnValidateClicked();
            }
        });

    }

    public void btnValidateClicked(){
        String nom = txtNom.getText().toString().trim();
        String prenom = txtPrenom.getText().toString().trim();
        String login = txtLogin.getText().toString().trim();
        String mail = txtMail.getText().toString().trim();
        String pass = txtPass.getText().toString().trim();
        String passconf = txtPassConf.getText().toString().trim();

        if(pass.equals(passconf)){
            if(formIsFilled(nom, prenom, login, mail, pass, passconf)){
                String mdpsha = shared.hashPassword(pass);
                Utilisateur user = new Utilisateur(1, nom, prenom, login, mdpsha, mail, 0, 1,"0");
                new AsyncInscriptionTask(this).execute(user);
            }else{
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "Les mots de passe doivent correspondre", Toast.LENGTH_LONG).show();
        }
    }

    public boolean formIsFilled(String... params){
        boolean filled = true;
        for(int i = 0; i < params.length; i++){
            if(params[i].length() == 0){
                return false;
            }
        }
        return true;
    }

    private class AsyncInscriptionTask extends AsyncTask<Utilisateur,Void,Utilisateur> {
        private Context mContext;
        private ProgressDialog dialog;
        private int exception;

        public AsyncInscriptionTask(Context c){
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
        protected Utilisateur doInBackground(Utilisateur[] params) {

            try {
                manager.addUser((Utilisateur) params[0]);

            }catch(HttpClientErrorException e){
                exception=406;
                cancel(true);
            } catch (Exception e){
                e.printStackTrace();
                exception = 1;
                if(dialog.isShowing()){
                    dialog.dismiss();

                }
                cancel(true);

            }
            return params[0];
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if(exception == 406) {
                Toast.makeText(mContext, "L'adresse mail ou le login sont déjà utilisés", Toast.LENGTH_LONG).show();

            }else {
                Toast.makeText(mContext, "Serveur injoignable", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPostExecute(Utilisateur user) {
            super.onPostExecute(user);
            if(dialog.isShowing()){
                dialog.dismiss();
            }
            shared = new SharedActivity(mContext);
            shared.connectUser(user);
            FragmentManager fragManager = getSupportFragmentManager();
            fragManager.beginTransaction().replace(R.id.content_nav, new disconnect_frag()).commit();


        }
    }
}
