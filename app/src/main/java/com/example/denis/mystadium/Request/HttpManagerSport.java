package com.example.denis.mystadium.Request;

import android.os.AsyncTask;

import com.example.denis.mystadium.Model.InfoEquipe;
import com.example.denis.mystadium.Model.Sport;

import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by denis on 01-12-16.
 */

public class HttpManagerSport extends AsyncTask<Integer, Void, InfoEquipe>{


    public Sport getSportFromRencontre (int id) throws Exception{
        try {
            HttpManager manager = new HttpManager();
            Sport s = manager.getTemplate().getForObject(manager.getRestUrl()+"/sport/idRencontre/"+id, Sport.class);
            return s;
        } catch (Exception e) {
            throw e;
        }
    }



    @Override
    protected InfoEquipe doInBackground(Integer... params) {
        return null;
    }

}
