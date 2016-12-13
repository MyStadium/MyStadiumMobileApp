package com.example.denis.mystadium.Request;

import android.util.Log;

import com.example.denis.mystadium.Model.Favoris;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by denis on 06-12-16.
 */

public class HttpManagerFavoris {

    public void deleteFav(int idUtilisateur, int idEquipe) throws Exception{
        try {
           HttpManager manager = new HttpManager();
            manager.getTemplate().delete(manager.getRestUrl()+"/favoris/idUtilisateur/"+idUtilisateur+"/idEquipe/"+idEquipe);
        } catch (Exception e) {
            throw e;
        }
    }

    public void postFavoris(Favoris o) throws Exception{
        try{
            HttpManager manager = new HttpManager();
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Content-Type", "application/json");
            HttpEntity<Favoris> request = new HttpEntity<Favoris>(o, headers);

            manager.getTemplate().postForObject(manager.getRestUrl()+"/favoris",request,Favoris.class);
        }catch (HttpClientErrorException e) {
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

}
