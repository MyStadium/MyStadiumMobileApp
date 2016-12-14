package com.example.denis.mystadium.Request;

import android.util.Log;

import com.example.denis.mystadium.Model.Role;
import com.example.denis.mystadium.Model.Score;
import com.example.denis.mystadium.Model.Utilisateur;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Created by denis on 06-12-16.
 */

public class HttpManagerRole {


    public Role getRole(int id) throws Exception{
        try {
           HttpManager manager = new HttpManager();
            Role r = manager.getTemplate().getForObject(manager.getRestUrl()+"/role/id/"+id, Role.class);
            return r;
        } catch (Exception e) {
            throw e;
        }
    }
}
