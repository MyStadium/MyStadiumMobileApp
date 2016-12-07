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

    private SharedPreferences shared;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.disconnection, container, false);
        shared = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        SharedPreferences.Editor editor = shared.edit();
        btnDisconnect = (Button)myView.findViewById(R.id.btnDisconnect);
        btnModify = (Button)myView.findViewById(R.id.btnModify);
        txtConnected = (TextView)myView.findViewById(R.id.txtConnected);
        txtProfilNom = (TextView)myView.findViewById(R.id.txtProfilNom);
        txtProfilPrenom = (TextView)myView.findViewById(R.id.txtProfilPrenom);
        txtProfilMail = (TextView)myView.findViewById(R.id.txtProfilMail);

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnModifyClicked();
            }
        });

        txtConnected.setText("Connecté en tant que: ");
        txtProfilNom.setText(shared.getString("connectedUserName", ""));
        txtProfilPrenom.setText(shared.getString("connectedUserForname", ""));
        txtProfilMail.setText(shared.getString("connectedUserMail", ""));
        btnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disconnectUser();
            }
        });
        return myView;
    }


    public void disconnectUser(){
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("connectedUserName", "");
        editor.putString("connectedUserForname", "");
        editor.putString("connectedUserMail", "");
        editor.putInt("connectedUserId", 0);
        editor.commit();
        txtConnected.setText("Aucun utilisateur connecté");

        FragmentManager manager = this.getActivity().getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_nav, new connection_frag()).commit();
    }

    public void btnModifyClicked(){
        Intent intent = new Intent(super.getContext(), updateLoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        txtProfilNom.setText(shared.getString("connectedUserName", ""));
        txtProfilPrenom.setText(shared.getString("connectedUserForname", ""));
        txtProfilMail.setText(shared.getString("connectedUserMail", ""));
        super.onStart();
    }
}
