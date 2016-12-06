package com.example.denis.mystadium.Request;

import com.example.denis.mystadium.Model.Suivre;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by denis on 06-12-16.
 */

public class HttpManagerSuivi {

    public void deleteSuivi(int idUtilisateur, int idMembre) throws Exception{
        try {
            HttpManager manager = new HttpManager();
            manager.getTemplate().delete(manager.getRestUrl()+"/suivre/idUtilisateur/"+idUtilisateur+"/idMembre/"+idMembre);
        } catch (Exception e) {
            throw e;
        }
    }

    public void postRequestSuivi(String url, Suivre o) throws Exception{
        HttpManager manager = new HttpManager();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        HttpEntity<Suivre> request = new HttpEntity<Suivre>(o, headers);
        try{
            manager.getTemplate().postForObject(manager.getRestUrl()+"/suivre",request,Suivre.class);
        }catch(HttpClientErrorException e){
            throw e;
        }catch(Exception e){
            throw e;
        }

    }
}
