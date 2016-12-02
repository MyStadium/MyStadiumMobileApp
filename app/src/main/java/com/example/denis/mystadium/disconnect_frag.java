package com.example.denis.mystadium;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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
    private TextView txtConnected;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.disconnection, container, false);
        SharedPreferences shared = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        btnDisconnect = (Button)myView.findViewById(R.id.btnDisconnect);
        txtConnected = (TextView)myView.findViewById(R.id.txtConnected);
        txtConnected.setText("Connecté en tant que" + shared.getString("connectedUserName", ""));
        btnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disconnectUser();
            }
        });

        return myView;
    }


    public void disconnectUser(){
        SharedPreferences shared = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("connectedUserName", "");
        editor.putString("connectedUserForname", "");
        editor.putInt("connectedUserId", 0);
        editor.commit();
        txtConnected.setText("Aucun utilisateur connecté");

        FragmentManager manager = this.getActivity().getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_nav, new connection_frag()).commit();
    }
}
