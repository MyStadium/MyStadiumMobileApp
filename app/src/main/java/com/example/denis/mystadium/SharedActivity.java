package com.example.denis.mystadium;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.example.denis.mystadium.Model.Utilisateur;

/**
 * Created by denis on 12-12-16.
 */

public class SharedActivity extends AppCompatActivity {

    private Context context;
    private SharedPreferences shared;

    public SharedActivity(Context con){
        this.context = con;
        shared = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void connectUser(Utilisateur user){
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("connectedUserName", user.getNom());
        editor.putString("connectedUserForname", user.getPrenom());
        editor.putString("connectedUserMail", user.getEmail());
        editor.putString("connectedUserPassword", user.getPass());
        editor.putString("connectedUserLogin", user.getLogin());
        editor.putInt("connectedUserIdRole", user.getIdRole());
        editor.putInt("connectedUserNbrBonScore", user.getNbrBonScore());
        editor.putInt("connectedUserId", user.getId());
        editor.putString("connectedUserFacebookId",user.getIdFacebook());
        editor.commit();
    }

    public void disconnectUser(){
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("connectedUserName", "");
        editor.putString("connectedUserForname", "");
        editor.putString("connectedUserMail", "");
        editor.putInt("connectedUserId", 0);
        editor.commit();
    }
}
