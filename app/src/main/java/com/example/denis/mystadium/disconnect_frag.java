package com.example.denis.mystadium;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

/**
 * Created by denis on 02-12-16.
 */

public class disconnect_frag extends Fragment {

    private View myView;
    private Button btnDisconnect;
    private Button btnModify;
    private TextView txtConnected;

    private TextView txtProfilNom;
    private TextView txtProfilPrenom;
    private TextView txtProfilMail;

    private SharedActivity shared;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        shared = new SharedActivity(getContext());
        myView = inflater.inflate(R.layout.disconnection, container, false);
        btnDisconnect = (Button)myView.findViewById(R.id.btnDisconnect);
        btnModify = (Button)myView.findViewById(R.id.btnModify);
        txtConnected = (TextView)myView.findViewById(R.id.txtConnected);
        txtProfilNom = (TextView)myView.findViewById(R.id.txtProfilNom);
        txtProfilPrenom = (TextView)myView.findViewById(R.id.txtProfilPrenom);
        txtProfilMail = (TextView)myView.findViewById(R.id.txtProfilMail);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnModifyClicked();
            }
        });

        txtConnected.setText("Connecté en tant que: ");
        txtProfilNom.setText(shared.getConnectedUserName());
        txtProfilPrenom.setText(shared.getConnectedUserForname());
        txtProfilMail.setText(shared.getConnectedUserMail());
        btnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disconnectUser();
            }
        });
        return myView;
    }


    public void disconnectUser(){
        shared.disconnectUser();

        txtConnected.setText("Aucun utilisateur connecté");
        LoginManager.getInstance().logOut();
        FragmentManager manager = this.getActivity().getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_nav, new connection_frag()).commit();
    }

    public void btnModifyClicked(){
        Intent intent = new Intent(super.getContext(), updateLoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        txtProfilNom.setText(shared.getConnectedUserName());
        txtProfilPrenom.setText(shared.getConnectedUserForname());
        txtProfilMail.setText(shared.getConnectedUserMail());
        super.onStart();
    }
}
