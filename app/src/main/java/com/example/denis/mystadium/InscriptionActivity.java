package com.example.denis.mystadium;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
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

public class InscriptionActivity extends AppCompatActivity {

    private EditText txtNom;
    private EditText txtPrenom;
    private EditText txtMail;
    private EditText txtPass;
    private EditText txtPassConf;
    private Button btnValider;
    private EditText txtLogin;
    private SharedPreferences shared;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        shared = PreferenceManager.getDefaultSharedPreferences(this);

        txtNom = (EditText)findViewById(R.id.editInscriptionNom);
        txtPrenom = (EditText)findViewById(R.id.editInscriptionPrenom);
        txtMail = (EditText)findViewById(R.id.editInscriptionMail);
        txtPass = (EditText)findViewById(R.id.editInscriptionPass);
        txtPassConf = (EditText)findViewById(R.id.editInscriptionPassConf);
        txtLogin = (EditText)findViewById(R.id.editInscriptionLogin);
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
                Utilisateur user = new Utilisateur(1, nom, prenom, login, pass, mail, 0, 1,"0");
                try{
                    HttpManagerUtilisateur manager = new HttpManagerUtilisateur();
                    manager.addUser(user);
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putString("connectedUserName", user.getNom());
                    editor.putString("connectedUserForname", user.getPrenom());
                    editor.putString("connectedUserMail", user.getEmail());
                    editor.putString("connectedUserPassword", user.getPass());
                    editor.putString("connectedUserLogin", user.getLogin());
                    editor.putInt("connectedUserIdRole", user.getIdRole());
                    editor.putInt("connectedUserNbrBonScore", user.getNbrBonScore());
                    Utilisateur userConnected = manager.getUserByMail(mail);
                    editor.putInt("connectedUserId", userConnected.getId());
                    editor.putString("connectedUserFacebookId",userConnected.getIdFacebook());
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
}
