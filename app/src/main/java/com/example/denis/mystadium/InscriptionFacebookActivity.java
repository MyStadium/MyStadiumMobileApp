package com.example.denis.mystadium;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.support.v4.app.FragmentManager;


import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.denis.mystadium.Model.Utilisateur;
import com.example.denis.mystadium.Request.HttpManagerUtilisateur;

import org.springframework.web.client.HttpClientErrorException;

import java.security.MessageDigest;
import java.util.Formatter;


public class InscriptionFacebookActivity extends AppCompatActivity {

    private EditText txtLogin;
    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtPasswordConf;
    private Button btnValider;
    private Utilisateur user;
    private HttpManagerUtilisateur requestUser;
    private SharedActivity shared;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription_facebook);
        txtLogin = (EditText) findViewById(R.id.txtLogin);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtPasswordConf = (EditText) findViewById(R.id.txtPasswordConf);
        btnValider = (Button) findViewById(R.id.btnValider);
        requestUser = new HttpManagerUtilisateur();
        shared = new SharedActivity(this);
        user = new Utilisateur();

        if (savedInstanceState == null) {
            Bundle extra = getIntent().getExtras();
            if(extra != null){
                user.setNom(extra.getString("nom"));
                user.setPrenom(extra.getString("prenom"));
                user.setIdFacebook(extra.getString("idFacebook"));
            }
        }else{
            user.setNom(savedInstanceState.getString("nom"));
            user.setPrenom(savedInstanceState.getString("prenom"));
            user.setIdFacebook(savedInstanceState.getString("idFacebook"));

        }
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnValiderClick();
            }
        });
    }

    public void btnValiderClick(){
        if(!txtEmail.getText().toString().equals("") && !txtLogin.getText().toString().equals("") && !txtPassword.getText().toString().equals("") && !txtPasswordConf.getText().toString().equals("")) {
            if(txtPassword.getText().toString().equals(txtPasswordConf.getText().toString())) {
                user.setLogin(txtLogin.getText().toString());
                user.setPass(txtPassword.getText().toString());
                user.setEmail(txtEmail.getText().toString());
                user.setNbrBonScore(0);
                user.setIdRole(1);
                String mdpsha = shared.hashPassword(user.getPass());
                user.setPass(mdpsha);

                new AsyncInscriptionTask(this).execute(user);
            }else{
                Toast.makeText(this, "Les mots de passe ne correspondent pas", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show();
        }
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
                requestUser.addUser((Utilisateur) params[0]);

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
