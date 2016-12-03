package com.example.denis.mystadium;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.denis.mystadium.Model.Utilisateur;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * Created by denis on 30-11-16.
 */

public class connection_frag extends android.support.v4.app.Fragment{
    private View myView;
    private EditText txtLogin;
    private EditText txtPass;
    private TextView txtConnection;
    private Button btnConnection;
    private Utilisateur user = null;
    private HttpRequestUser requestUser;
    private LoginButton loginButton;
    private CallbackManager mCallBackManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        mCallBackManager = CallbackManager.Factory.create();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.connection, container, false);

        requestUser = new HttpRequestUser();
        txtLogin = (EditText) myView.findViewById(R.id.txtLogin);
        txtPass = (EditText) myView.findViewById(R.id.txtPass);
        txtConnection = (TextView) myView.findViewById(R.id.txtConnection);
        btnConnection = (Button) myView.findViewById(R.id.btnConnection);
        btnConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryConnection();
            }
        });
        loginButton = (LoginButton) myView.findViewById(R.id.login_button);
        loginButton.setFragment(this);
        loginButton.registerCallback(mCallBackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Profile profile = Profile.getCurrentProfile();
                txtConnection.setText("Connecté en tant que:"+profile.getName());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        return myView;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallBackManager.onActivityResult(requestCode,resultCode,data);
    }

    public void tryConnection(){
        SharedPreferences pref = this.getActivity().getPreferences(Context.MODE_PRIVATE);

        user = requestUser.tryToConnectUser(txtLogin.getText().toString(), txtPass.getText().toString());
        if(user != null){
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("connectedUserName", user.getNom());
            editor.putString("connectedUserForname", user.getPrenom());
            editor.putInt("connectedUserId", user.getId());
            editor.commit();
            txtConnection.setText("Connecté en tant que:"+pref.getString("connectedUserName", "") + " " +pref.getString("connectedUserForname", ""));

            FragmentManager manager = this.getActivity().getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_nav, new disconnect_frag()).commit();
        }else{
            txtConnection.setText("Mauvaise combinaison login / pass");
        }
    }
}
