package com.example.denis.mystadium;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.denis.mystadium.Model.Utilisateur;
import com.example.denis.mystadium.Request.HttpManagerUtilisateur;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by denis on 30-11-16.
 */

public class connection_frag extends android.support.v4.app.Fragment{
    private View myView;
    private EditText txtLogin;
    private EditText txtPass;
    private Button btnConnection;
    private Button btnInscription;
    private Utilisateur user = null;
    private HttpManagerUtilisateur requestUser;
    private LoginButton loginButton;
    private CallbackManager mCallBackManager;
    private String idFacebook;

    private SharedActivity shared;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        mCallBackManager = CallbackManager.Factory.create();
        shared = new SharedActivity(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.connection, container, false);

        requestUser = new HttpManagerUtilisateur();
        txtLogin = (EditText) myView.findViewById(R.id.txtLogin);
        txtPass = (EditText) myView.findViewById(R.id.txtPass);
        btnConnection = (Button) myView.findViewById(R.id.btnConnection);
        btnInscription = (Button) myView.findViewById(R.id.btnInscription);
        btnConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryConnection();
            }
        });

        loginButton = (LoginButton) myView.findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile","user_friends") );
        loginButton.setFragment(this);
        loginButton.registerCallback(mCallBackManager, new FacebookCallback<LoginResult>() {

                @Override
                public void onSuccess(LoginResult loginResult) {
                    AccessToken accessToken = loginResult.getAccessToken();
                    Profile profile = Profile.getCurrentProfile();


                    idFacebook = profile.getId();
                    user = requestUser.getUserByIdFacebook(idFacebook);
                    if( user != null){
                        shared.connectUser(user);

                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        manager.beginTransaction().replace(R.id.content_nav, new disconnect_frag()).commit();
                    }else {
                        Intent i = new Intent(getContext(),InscriptionFacebookActivity.class);
                        i.putExtra("nom",Profile.getCurrentProfile().getLastName());
                        i.putExtra("prenom",Profile.getCurrentProfile().getFirstName());
                        i.putExtra("idFacebook",idFacebook);
                        startActivity(i);
                    }

                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {

                }
            });

        btnInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnInscriptionClicked();
            }
        });

        return myView;
    }

    public void tryConnection(){

        try {
            user = requestUser.tryToConnectUser(txtLogin.getText().toString(), txtPass.getText().toString());
        }catch(Exception e){
            Toast.makeText(getContext(), "Erreur lors de l'accès au serveur", Toast.LENGTH_SHORT).show();
        }
        if(user != null){
            shared.connectUser(user);
            FragmentManager manager = this.getActivity().getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_nav, new disconnect_frag()).commit();
        }else{
            Toast.makeText(getContext(), "Mauvaise combinaison login/mot de passe", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnInscriptionClicked(){
        Intent intent = new Intent(getContext(), InscriptionActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallBackManager.onActivityResult(requestCode,resultCode,data);
    }
}
