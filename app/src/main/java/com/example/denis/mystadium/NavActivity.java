package com.example.denis.mystadium;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.icu.text.IDNA;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.denis.mystadium.Model.InfoMembre;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class NavActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        //Choose the frag to put on the screen at the beginning of the app, depending on if the user is alredy connected or not
        FragmentManager manager = getSupportFragmentManager();

        Fragment startingFragment;
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(this);
        if(shared.getString("connectedUserName", "").equals("") || shared.getString("connectedUserName", "") == null){
            startingFragment = new connection_frag();
        }else{
            startingFragment = new disconnect_frag();
        }
        manager.beginTransaction().replace(R.id.content_nav, startingFragment).commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }




        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.denis.mystadium",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                System.out.println("KeyHash:"+Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        android.support.v4.app.Fragment myFrag = null;

        boolean connected = false;
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        if(pref.getString("connectedUserName", "")!= null && !pref.getString("connectedUserName", "").equals("")){
            connected = true;
        }

        if (id == R.id.nav_con) {
            if(connected){
                myFrag = new disconnect_frag();
            }else{
                myFrag = new connection_frag();
            }
        } else if (id == R.id.nav_che) {
            myFrag = new chercherMatch_frag();
        }  else if (id == R.id.nav_equ) {
            if(connected){
                myFrag = new equipes_frag();
            }else{
                myFrag = new connection_frag();
                Toast.makeText(getApplicationContext(), "Vous devez être connecté pour afficher votre profil", Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.nav_jou) {
            if(connected){
                myFrag = new joueurs_frag();
            }else{
                myFrag = new connection_frag();
                Toast.makeText(getApplicationContext(), "Vous devez être connecté pour afficher votre profil", Toast.LENGTH_LONG).show();
            }
        }

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_nav, myFrag).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        ArrayList<InfoMembre> liste;
        liste = data.getParcelableArrayListExtra("listFavRest");

    }*/


}
