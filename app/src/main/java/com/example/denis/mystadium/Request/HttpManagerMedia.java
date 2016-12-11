package com.example.denis.mystadium.Request;

import android.os.AsyncTask;

import com.example.denis.mystadium.Model.InfoEquipe;
import com.example.denis.mystadium.Model.InfoMedia;
import com.example.denis.mystadium.Model.Media;

import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by denis on 01-12-16.
 */

public class HttpManagerMedia extends AsyncTask<Integer, Void, InfoEquipe>{


    public List<InfoMedia> getMediaFromRencontre (int id) throws Exception{
        try {
            HttpManager manager = new HttpManager();
            ResponseEntity<InfoMedia[]> responseEntity =  manager.getTemplate().getForEntity(manager.getRestUrl()+"/media/infoMedia/idRencontre/"+id, InfoMedia[].class);
            List<InfoMedia> liste =  new ArrayList<InfoMedia>(Arrays.asList( responseEntity.getBody()));
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
