package com.example.denis.mystadium;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.denis.mystadium.Model.Utilisateur;
import com.example.denis.mystadium.Request.HttpRequestUser;

/**
 * Created by denis on 30-11-16.
 */

public class connection_frag extends android.support.v4.app.Fragment{
    private View myView;
    private EditText txtLogin;
    private EditText txtPass;
    private Button btnConnection;
    private Utilisateur user = null;
    private HttpRequestUser requestUser;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.connection, container, false);

        requestUser = new HttpRequestUser();
        txtLogin = (EditText) myView.findViewById(R.id.txtLogin);
        txtPass = (EditText) myView.findViewById(R.id.txtPass);
        btnConnection = (Button) myView.findViewById(R.id.btnConnection);
        btnConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryConnection();
            }
        });

        return myView;
    }

    public void tryConnection(){
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(this.getContext());

        user = requestUser.tryToConnectUser(txtLogin.getText().toString(), txtPass.getText().toString());
        if(user != null){
            SharedPreferences.Editor editor = shared.edit();
            editor.putString("connectedUserName", user.getNom());
            editor.putString("connectedUserForname", user.getPrenom());
            editor.putString("connectedUserMail", user.getEmail());
            editor.putInt("connectedUserId", user.getId());
            editor.commit();

            FragmentManager manager = this.getActivity().getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_nav, new disconnect_frag()).commit();
        }else{
            Toast.makeText(getContext(), "Mauvaise combinaison login/mot de passe", Toast.LENGTH_SHORT).show();
        }
    }
}
