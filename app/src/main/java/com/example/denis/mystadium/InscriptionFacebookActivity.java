package com.example.denis.mystadium;

import android.app.Activity;
import android.content.SharedPreferences;
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
    private SharedPreferences shared;
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
        shared = PreferenceManager.getDefaultSharedPreferences(this);
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
                String mdpsha = hashPassword(user.getPass());
                user.setPass(mdpsha);
                try {
                    requestUser.addUser(user);
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putString("connectedUserName", user.getNom());
                    editor.putString("connectedUserForname", user.getPrenom());
                    editor.putString("connectedUserMail", user.getEmail());
                    editor.putString("connectedUserPassword", user.getPass());
                    editor.putString("connectedUserLogin", user.getLogin());
                    editor.putInt("connectedUserIdRole", user.getIdRole());
                    editor.putInt("connectedUserNbrBonScore", user.getNbrBonScore());
                    Utilisateur userConnected = requestUser.getUserByMail(user.getEmail());
                    editor.putInt("connectedUserId", userConnected.getId());
                    editor.putString("connectedUserIdFacebook", userConnected.getIdFacebook());
                    editor.commit();

                    FragmentManager fragManager = getSupportFragmentManager();
                    fragManager.beginTransaction().replace(R.id.content_nav, new disconnect_frag()).commit();
                }catch(HttpClientErrorException e){
                    Toast.makeText(this, "L'adresse mail ou le login sont déjà utilisés", Toast.LENGTH_LONG).show();
                }
                catch(Exception e){
                    Toast.makeText(this, "Erreur lors de votre inscription", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this, "Les mots de passe ne correspondent pas", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show();
        }
    }
    public String hashPassword(String mdp){
        String mdpsha ="";
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(mdp.getBytes("UTF-8"));
            mdpsha = byteToHex(crypt.digest());
            return mdpsha;


        }catch(Exception ex){
            ex.printStackTrace();
            return"";
        }
    }
    private static String byteToHex(final byte[] hash)
    {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
