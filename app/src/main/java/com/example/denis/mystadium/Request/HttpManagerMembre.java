package com.example.denis.mystadium.Request;

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

public class HttpManagerMembre extends AsyncTask<Integer, Void, Membre>{


    public List<InfoMembre> getFavPlayersList (int... params) throws Exception{
        try {
            HttpManager manager = new HttpManager();
            ResponseEntity<InfoMembre[]> responseEntity =  manager.getTemplate().getForEntity(manager.getRestUrl()+"/utilisateur/"+params[0]+"/favorite", InfoMembre[].class);
            List<InfoMembre> liste =  new ArrayList<InfoMembre>(Arrays.asList( responseEntity.getBody()));
            return liste;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<InfoMembre> getMebersFromTeam (int id) throws Exception{
        try {
            HttpManager manager = new HttpManager();
            ResponseEntity<InfoMembre[]> responseEntity =  manager.getTemplate().getForEntity(manager.getRestUrl()+"/equipe/"+id+"/compo", InfoMembre[].class);
            List<InfoMembre> liste =  new ArrayList<InfoMembre>(Arrays.asList( responseEntity.getBody()));
            return liste;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<InfoMembre> getFollowingPlayersList (int... params) throws Exception{
        try {
            HttpManager manager = new HttpManager();
            ResponseEntity<InfoMembre[]> responseEntity =  manager.getTemplate().getForEntity(manager.getRestUrl()+"/utilisateur/"+params[0]+"/following", InfoMembre[].class);
            List<InfoMembre> liste =  new ArrayList<InfoMembre>(Arrays.asList( responseEntity.getBody()));
            return liste;
        } catch (Exception e) {
            throw e;
        }
    }


    public List<InfoMembre> getMembreFromSearch (String... params) throws Exception{
        try {
            HttpManager manager = new HttpManager();
            ResponseEntity<InfoMembre[]> responseEntity = manager.getTemplate().getForEntity(manager.getRestUrl()+"/membre/search/"+params[0], InfoMembre[].class);
            return new ArrayList<InfoMembre>(Arrays.asList( responseEntity.getBody()));
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    protected Membre doInBackground(Integer... params) {
        return null;
    }
}
