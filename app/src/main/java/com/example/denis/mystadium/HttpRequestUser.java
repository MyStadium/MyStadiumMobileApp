package com.example.denis.mystadium;

import android.os.AsyncTask;
import android.util.Log;

import com.example.denis.mystadium.Model.Utilisateur;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by denis on 01-12-16.
 */

public class HttpRequestUser extends AsyncTask<String, Void, Utilisateur>{

    @Override
    protected Utilisateur doInBackground (String... params) {
        return new Utilisateur();
    }

    public Utilisateur getUser (String... params) {
        try {
            final String url = "http://192.168.128.13:8081/MyStadium-REST-DEVILLE-BRONSART/resources/utilisateur/id/"+params[0];
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

            Utilisateur user = restTemplate.getForObject(url, Utilisateur.class);
            return user;

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MainActivity", e.getMessage(), e);
            return null;
        }
    }

    public Utilisateur tryToConnectUser (String... params) {
        try {
            final String url = "http://192.168.128.13:8081/MyStadium-REST-DEVILLE-BRONSART/resources/utilisateur/connect/"+params[0]+"/"+params[1];
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

            Utilisateur user = restTemplate.getForObject(url, Utilisateur.class);


            return user;

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MainActivity", e.getMessage(), e);
            return null;
        }
    }


}
