package com.example.denis.mystadium.Request;

import android.os.AsyncTask;
import android.util.Log;

import com.example.denis.mystadium.Model.Favoris;
import com.example.denis.mystadium.Model.Utilisateur;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

/**
 * Created by denis on 01-12-16.
 */

public class HttpManagerUtilisateur extends AsyncTask<String, Void, Utilisateur>{

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

    public Utilisateur getUserByMail (String... params) {
        try {
            final String url = "http://192.168.128.13:8081/MyStadium-REST-DEVILLE-BRONSART/resources/utilisateur/mail/"+params[0];
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
    public Utilisateur getUserByIdFacebook (String... params) {
        try {
            HttpManager manager  = new HttpManager();
            final String url = manager.getRestUrl()+"/utilisateur/idFacebook/"+params[0];

            Utilisateur user = manager.getTemplate().getForObject(url, Utilisateur.class);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MainActivity", e.getMessage(), e);
            return null;
        }
    }

    public void updateUser (Utilisateur u) throws Exception{
        try {
            HttpManager manager = new HttpManager();
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Content-Type", "application/json");
            HttpEntity<Utilisateur> request = new HttpEntity<Utilisateur>(u, headers);

            manager.getTemplate().put(manager.getRestUrl()+"/utilisateur/"+u.getId(), request, Utilisateur.class);
        } catch (HttpClientErrorException e) {
            HttpStatus status = e.getStatusCode();
            if(status == HttpStatus.NOT_ACCEPTABLE){
                throw e;
            }else{
                throw new Exception();
            }
        }catch (Exception e) {
            throw e;
        }
    }

    public void addUser (Utilisateur u) throws Exception{
        try {
            HttpManager manager = new HttpManager();
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Content-Type", "application/json");
            HttpEntity<Utilisateur> request = new HttpEntity<Utilisateur>(u, headers);

            manager.getTemplate().postForObject(manager.getRestUrl()+"/utilisateur", request, Utilisateur.class);
        } catch (HttpClientErrorException e) {
            HttpStatus status = e.getStatusCode();
            if(status == HttpStatus.NOT_ACCEPTABLE){
                throw e;
            }else{
                throw new Exception();
            }
        }catch (Exception e) {
            throw e;
        }
    }

    public Utilisateur tryToConnectUser (String... params) {
        try {
            HttpManager manager = new HttpManager();
            final String url = manager.getRestUrl()+"/utilisateur/connect/"+params[0]+"/"+params[1];
            Utilisateur user = manager.getTemplate().getForObject(url, Utilisateur.class);
            return user;
        } catch (Exception e) {
           throw e;
        }
    }


}
