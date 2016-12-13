package com.example.denis.mystadium.Request;

import com.example.denis.mystadium.Model.AvgVote;
import com.example.denis.mystadium.Model.Vote;
import com.example.denis.mystadium.Model.VoteMVP;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Created by denis on 06-12-16.
 */

public class HttpManagerVoteMVP {


    public void postVoteMVP(VoteMVP v) throws Exception{
        HttpManager manager = new HttpManager();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        HttpEntity<VoteMVP> request = new HttpEntity<VoteMVP>(v, headers);
        try{
            manager.getTemplate().postForObject(manager.getRestUrl()+"/voteMVP",request,VoteMVP.class);
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
