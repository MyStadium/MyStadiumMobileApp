package com.example.denis.mystadium.restrequest;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Utilisateur on 02-12-16.
 */

public class RequestManager {
    private final String urlRest = "http://192.168.128.13:8081/MyStadium-REST-DEVILLE-BRONSART/resources/";

    public void postRequest(String url, Object o){
        RestTemplate restTemplate = new RestTemplate();
        Object result = restTemplate.postForObject(urlRest+url,o,o.getClass());
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
