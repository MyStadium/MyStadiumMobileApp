package com.example.denis.mystadium.restrequest;

import com.example.denis.mystadium.models.Favoris;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Utilisateur on 02-12-16.
 */

public class RequestManager {
    private final String urlRest = "http://192.168.128.13:8081/MyStadium-REST-DEVILLE-BRONSART/resources/";

    public void postRequest(String url, Favoris o){
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Content-Type", "application/json");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        HttpEntity<Favoris> request = new HttpEntity<Favoris>(o, headers);

        Favoris response = restTemplate.postForObject(urlRest+url,request,Favoris.class);
        System.out.println(response.toString());


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
