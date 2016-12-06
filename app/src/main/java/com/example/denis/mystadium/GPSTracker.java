package com.example.denis.mystadium;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import javax.net.ssl.SSLContextSpi;

/**
 * Created by denis on 30-11-16.
 */

public class GPSTracker extends Service implements LocationListener {

    private final Context context;

    boolean canGetLocation = false;

    Location location;

    double longitude;
    double latitude;

    protected LocationManager locationManager;

    public GPSTracker(Context context) {
        this.context = context;

        location = getLocation();

    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            canGetLocation = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);


            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            }
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); //souligné en rouge en hommage au standard;
              if(location != null){
                      latitude = location.getLatitude();
                      longitude = location.getLongitude();
              }

        }catch(Exception e){
            e.printStackTrace();
        }

        return location;
    }


    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }
        return longitude;
    }

    public boolean canGetlocation(){
        return this.canGetLocation;
    }


    public void showSettingAlert(){

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(i);
    }
}
