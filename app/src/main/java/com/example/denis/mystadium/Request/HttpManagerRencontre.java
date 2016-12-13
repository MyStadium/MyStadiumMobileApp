package com.example.denis.mystadium.Request;

import com.example.denis.mystadium.Model.InfoRencontre;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Utilisateur on 06-12-16.
 */

public class HttpManagerRencontre {

    public HttpManagerRencontre(){

    }

    public List<InfoRencontre> getRencontreProcheList(double latitude, double longitude){
        try {
            HttpManager manager = new HttpManager();
            Date date = new Date();
            String dateDebut = new SimpleDateFormat("yyyy-MM-dd").format(date);
            Date dayAfter = new Date(date.getTime() + TimeUnit.DAYS.toMillis(1));
            String dateFin = new SimpleDateFormat("yyyy-MM-dd").format(dayAfter); ;
            String nbKilometreString = "0.5";
            String url = "/rencontre/find/" + dateDebut + "/" + dateFin + "/" + nbKilometreString + "/" + latitude + "/" + longitude;



            String urlComplet = manager.getRestUrl()+url;
            ResponseEntity<InfoRencontre[]> responseEntity = manager.getTemplate().getForEntity(urlComplet, InfoRencontre[].class);
            List<InfoRencontre> liste = new ArrayList<InfoRencontre>(Arrays.asList(responseEntity.getBody()));
            return liste;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public List<InfoRencontre> getRencontreRechercheList(String dateDebut,String dateFin,String nbKilometreString,double latitude,double longitude){
        try{
            HttpManager manager = new HttpManager();
            String url = "/rencontre/find/" + dateDebut + "/" + dateFin + "/" + nbKilometreString + "/" + latitude + "/" + longitude;
            String urlComplet = manager.getRestUrl()+url;
            ResponseEntity<InfoRencontre[]> responseEntity = manager.getTemplate().getForEntity(urlComplet, InfoRencontre[].class);
            List<InfoRencontre> liste = new ArrayList<InfoRencontre>(Arrays.asList(responseEntity.getBody()));
            return liste;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<InfoRencontre> getRencontreFromSerie(int idChampionnat, int idJournee){
        try{
            HttpManager manager = new HttpManager();
            ResponseEntity<InfoRencontre[]> responseEntity = manager.getTemplate().getForEntity(manager.getRestUrl()+"/rencontre/championnat/"+idChampionnat+"/journee/"+idJournee, InfoRencontre[].class);
            List<InfoRencontre> liste = new ArrayList<InfoRencontre>(Arrays.asList(responseEntity.getBody()));
            return liste;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public InfoRencontre getInfoRencontreById(int id) throws Exception{
        try{
            HttpManager manager = new HttpManager();
            InfoRencontre rencontre = manager.getTemplate().getForObject(manager.getRestUrl()+"/infoRencontre/id/"+id, InfoRencontre.class);
            return rencontre;
        }catch(Exception e){
            throw e;
        }
    }
}
