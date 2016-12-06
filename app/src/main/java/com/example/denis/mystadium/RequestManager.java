package com.example.denis.mystadium;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Created by e140677 on 06/12/2016.
 */
public class RequestManager {
    private final String urlRest = "http://192.168.128.13:8081/MyStadium-REST-DEVILLE-BRONSART/resources/";

    public RestTemplate postRequest(String url){
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Content-Type", "application/json");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return restTemplate;
        //HttpEntity<Favoris> request = new HttpEntity<Favoris>(o, headers);

        //Favoris response = restTemplate.postForObject(urlRest+url,request,Favoris.class);



    }
    public void putRequest(String url, Object o,Map<String,String> params){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(urlRest+url,o,params);
    }
    public void deleteRequest(String url,Map<String,String> params){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(urlRest+url,params);
    }
    public RestTemplate requestGet(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }
    public String getUrlRest(){
        return urlRest;
    }
}
