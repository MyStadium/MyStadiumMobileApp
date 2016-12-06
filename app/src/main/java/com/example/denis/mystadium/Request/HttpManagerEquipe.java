package com.example.denis.mystadium.Request;

import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.denis.mystadium.Model.InfoEquipe;

import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by denis on 01-12-16.
 */

public class HttpManagerEquipe extends AsyncTask<Integer, Void, InfoEquipe>{


    public List<InfoEquipe> getFavTeamList (int... params) throws Exception{
        try {
            HttpManager manager = new HttpManager();
            ResponseEntity<InfoEquipe[]> responseEntity =  manager.getTemplate().getForEntity(manager.getRestUrl()+"/utilisateur/"+params[0]+"/favorite", InfoEquipe[].class);
            List<InfoEquipe> liste =  new ArrayList<InfoEquipe>(Arrays.asList( responseEntity.getBody()));
            return liste;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<InfoEquipe> getTeamFromSearch (String... params) throws Exception{
        try {
            HttpManager manager = new HttpManager();
            ResponseEntity<InfoEquipe[]> responseEntity =  manager.getTemplate().getForEntity(manager.getRestUrl()+"/equipe/search/"+params[0], InfoEquipe[].class);
            List<InfoEquipe> liste =  new ArrayList<InfoEquipe>(Arrays.asList( responseEntity.getBody()));
            return liste;
        } catch (Exception e) {
            throw e;
        }
    }


    @Override
    protected InfoEquipe doInBackground(Integer... params) {
        return null;
    }

}
