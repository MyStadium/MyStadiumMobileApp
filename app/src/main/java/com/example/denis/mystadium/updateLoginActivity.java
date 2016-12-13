package com.example.denis.mystadium;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
    private SharedPreferences pref;
    private Button btnValidateUpdate;
    private HttpManagerUtilisateur httpUtilisateurManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_login);
        httpUtilisateurManager = new HttpManagerUtilisateur();

        pref  = PreferenceManager.getDefaultSharedPreferences(this);

        txtPrenom = (EditText)findViewById(R.id.editPrenom);
        txtNom = (EditText)findViewById(R.id.editNom);
        txtMail = (EditText)findViewById(R.id.editMail);
        btnValidateUpdate = (Button) findViewById(R.id.btnValidateUpdate);

        txtPrenom.setText(pref.getString("connectedUserForname", ""));
        txtNom.setText(pref.getString("connectedUserName", ""));
        txtMail.setText(pref.getString("connectedUserMail", ""));

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
        String login = pref.getString("connectedUserLogin", "");
        String pass = pref.getString("connectedUserPassword", "");
        Utilisateur connectedUser = new Utilisateur(id, nom, prenom, login, pass, mail, nbrBonScore, idRole, pref.getString("connectedUserIdFacebook", ""));
        try{
            httpUtilisateurManager.updateUser(connectedUser);
            SharedPreferences.Editor edit = pref.edit();
            edit.putInt("connectedUserId", id);
            edit.putInt("connectedUserIdRole", idRole);
            edit.putInt("connectedUserNbrBonScore", nbrBonScore);
            edit.putString("connectedUserName", nom);
            edit.putString("connectedUserForname", prenom);
            edit.putString("connectedUserMail", mail);
            edit.putString("connectedUserLogin",login);
            edit.putString("connectedUserPassword", pass);
            edit.commit();
            onBackPressed();
        }catch(HttpClientErrorException e){
            Toast.makeText(this, "L'adresse mail ou le login sont déjà utilisés", Toast.LENGTH_LONG).show();
        }
        catch(Exception e){
            Toast.makeText(this, "Erreur lors de la connexion au serveur", Toast.LENGTH_LONG).show();
        }

    }
}
