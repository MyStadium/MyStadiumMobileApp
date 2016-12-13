package com.example.denis.mystadium;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.example.denis.mystadium.Model.Utilisateur;

import java.security.MessageDigest;
import java.util.Formatter;

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
        editor.putString("connectedUserPassword","");
        editor.putString("connectedUserLogin","");
        editor.putInt("connectedUserIdRole", 0);
        editor.putInt("connectedUserNbrBonScore", 0);
        editor.putInt("connectedUserId", 0);
        editor.putString("connectedUserFacebookId","");
        editor.commit();
    }

    public int getConnectedUserId(){
        return shared.getInt("connectedUserId", 0);
    }

    public String getConnectedUserName(){
        return shared.getString("connectedUserName", "");
    }

    public String getConnectedUserLogin(){
        return shared.getString("connectedUserLogin", "");
    }

    public String getConnectedUserForname(){
        return shared.getString("connectedUserForname", "");
    }
    public String getConnectedUserMail(){
        return shared.getString("connectedUserMail", "");
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
