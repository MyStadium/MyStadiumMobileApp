package com.example.denis.mystadium.Request;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by denis on 06-12-16.
 */

public class HttpManager {

    private final String urlRest = "http://192.168.128.13:8081/MyStadium-REST-DEVILLE-BRONSART/resources";

    public HttpManager(){

    }

    public RestTemplate getTemplate(){
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.setRequestFactory(factory);

        return restTemplate;
    }

    public String getRestUrl(){
        return this.urlRest;
    }
}
