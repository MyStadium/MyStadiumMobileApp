package com.example.denis.mystadium;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.denis.mystadium.Model.Utilisateur;
import com.example.denis.mystadium.Request.HttpManagerUtilisateur;

import org.springframework.web.client.HttpClientErrorException;

public class updateLoginActivity extends AppCompatActivity {

    private EditText txtPrenom;
    private EditText txtNom;
    private EditText txtMail;
    private EditText txtLogin;
    private SharedPreferences pref;
    private Button btnValidateUpdate;
    private HttpManagerUtilisateur httpUtilisateurManager;
    private SharedActivity shared;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_login);
        httpUtilisateurManager = new HttpManagerUtilisateur();

        pref  = PreferenceManager.getDefaultSharedPreferences(this);
        shared = new SharedActivity(this);
        txtPrenom = (EditText)findViewById(R.id.editPrenom);
        txtNom = (EditText)findViewById(R.id.editNom);
        txtMail = (EditText)findViewById(R.id.editMail);
        txtLogin = (EditText)findViewById(R.id.editLogin);
        btnValidateUpdate = (Button) findViewById(R.id.btnValidateUpdate);

        txtPrenom.setText(pref.getString("connectedUserForname", ""));
        txtNom.setText(pref.getString("connectedUserName", ""));
        txtMail.setText(pref.getString("connectedUserMail", ""));
        txtLogin.setText(pref.getString("connectedUserLogin", ""));

        btnValidateUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnValidateClicked();
            }
        });


    }

    public void btnValidateClicked(){
        int id = pref.getInt("connectedUserId", 0);
        int idRole = pref.getInt("connectedUserIdRole", 0);
        int nbrBonScore = pref.getInt("connectedUserNbrBonScore", 0);
        String nom = txtNom.getText().toString().trim();
        String prenom = txtPrenom.getText().toString().trim();
        String mail = txtMail.getText().toString().trim();
        String login = txtLogin.getText().toString().trim();
        String pass = pref.getString("connectedUserPassword", "");
        if(formIsFilled(nom, prenom, mail, login)){
            Utilisateur connectedUser = new Utilisateur(id, nom, prenom, login, pass, mail, nbrBonScore, idRole, pref.getString("connectedUserIdFacebook", ""));
            new AsyncUpdateTask(this).execute(connectedUser);
        }else{
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show();
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
    private class AsyncUpdateTask extends AsyncTask<Utilisateur,Void,Utilisateur> {
        private Context mContext;
        private ProgressDialog dialog;
        private int exception;

        public AsyncUpdateTask(Context c){
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
                httpUtilisateurManager.updateUser(params[0]);

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
            onBackPressed();


        }
    }
}
