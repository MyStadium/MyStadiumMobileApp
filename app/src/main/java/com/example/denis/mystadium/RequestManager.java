package com.example.denis.mystadium;

import com.example.denis.mystadium.Model.Favoris;
import com.example.denis.mystadium.Model.Suivre;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Created by denis on 06-12-16.
 */

public class RequestManager {
    private final String urlRest = "http://192.168.128.13:8081/MyStadium-REST-DEVILLE-BRONSART/resources/";

    public void postRequestFavoris(String url, Favoris o){
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        HttpEntity<Favoris> request = new HttpEntity<Favoris>(o, headers);

        restTemplate.postForObject(urlRest+url,request,Favoris.class);
    }

    public void postRequestSuivi(String url, Suivre o){
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        HttpEntity<Suivre> request = new HttpEntity<Suivre>(o, headers);

        restTemplate.postForObject(urlRest+url,request,Suivre.class);
    }


    public void putRequest(String url, Object o,Map<String,String> params){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(urlRest+url,o,params);
    }


    public void deleteRequest(String url,Map<String,String> params){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(urlRest+url,params);
    }


    public RestTemplate requestGet(String url,Map<String,String> params){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }
}
