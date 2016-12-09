package com.example.denis.mystadium.Request;

import android.util.Log;

import com.example.denis.mystadium.Model.AvgVote;
import com.example.denis.mystadium.Model.InfoMembre;
import com.example.denis.mystadium.Model.Suivre;
import com.example.denis.mystadium.Model.Utilisateur;
import com.example.denis.mystadium.Model.Vote;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by denis on 06-12-16.
 */

public class HttpManagerVote {


    public void postVote(Vote v) throws Exception{
        HttpManager manager = new HttpManager();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        HttpEntity<Vote> request = new HttpEntity<Vote>(v, headers);
        try{
            manager.getTemplate().postForObject(manager.getRestUrl()+"/vote",request,Vote.class);
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

    public AvgVote getAvgVoteFor (int id) throws Exception{
        try {
           HttpManager manager = new HttpManager();
            AvgVote vote =manager.getTemplate().getForObject(manager.getRestUrl()+"/vote/avg/"+id, AvgVote.class);
            return vote;
        } catch (Exception e) {
            throw e;
        }
    }
}
