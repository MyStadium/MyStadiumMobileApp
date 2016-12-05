package com.example.denis.mystadium;

import android.os.AsyncTask;
import android.util.Log;

import com.example.denis.mystadium.Model.InfoMembre;
import com.example.denis.mystadium.Model.Membre;
import com.example.denis.mystadium.Model.Suivre;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by denis on 01-12-16.
 */

public class HttpRequestMembre extends AsyncTask<Integer, Void, Membre>{


    public List<InfoMembre> getFavPlayersList (int... params) {
        try {
            final String url = "http://192.168.128.13:8081/MyStadium-REST-DEVILLE-BRONSART/resources/utilisateur/"+params[0]+"/following";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());


            ResponseEntity<InfoMembre[]> responseEntity = restTemplate.getForEntity(url, InfoMembre[].class);
            List<InfoMembre> liste = new ArrayList<InfoMembre>(Arrays.asList( responseEntity.getBody()));
            return liste;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MainActivity", e.getMessage(), e);
            return null;
        }
    }

    public List<InfoMembre> getMembreFromSearch (String... params) {
        try {
            final String url = "http://192.168.128.13:8081/MyStadium-REST-DEVILLE-BRONSART/resources/membre/search/"+params[0];
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());


            ResponseEntity<InfoMembre[]> responseEntity = restTemplate.getForEntity(url, InfoMembre[].class);
            List<InfoMembre> liste = new ArrayList<InfoMembre>(Arrays.asList( responseEntity.getBody()));
            return liste;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MainActivity", e.getMessage(), e);
            return null;
        }
    }

    public void deleteFav(int idUtilisateur, int idMembre){
        try {
            final String url = "http://192.168.128.13:8081/MyStadium-REST-DEVILLE-BRONSART/resources/suivre/idUtilisateur/"+idUtilisateur+"/idMembre/"+idMembre;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

            restTemplate.delete(url);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MainActivity", e.getMessage(), e);
        }
    }

    public void addFav(Suivre s){
        try {
            final String url = "http://192.168.128.13:8081/MyStadium-REST-DEVILLE-BRONSART/resources/suivre";
            /*RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

            restTemplate.postForEntity(url, s, Suivre.class);*/


            /*RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<Suivre> entity = new HttpEntity<Suivre>(s, headers);
            restTemplate.exchange(url, HttpMethod.POST, entity, Suivre.class);*/


        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MainActivity", e.getMessage(), e);
        }
    }


    @Override
    protected Membre doInBackground(Integer... params) {
        return null;
    }
}
