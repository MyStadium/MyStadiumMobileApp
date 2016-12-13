package com.example.denis.mystadium.Request;

import com.example.denis.mystadium.Model.AvgVote;
import com.example.denis.mystadium.Model.Score;
import com.example.denis.mystadium.Model.Vote;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.support.Base64;
import org.springframework.web.client.HttpClientErrorException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by denis on 06-12-16.
 */

public class HttpManagerScore {


    public void postScore(Score s) throws Exception{
        try{
            HttpManager manager = new HttpManager();
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Content-Type", "application/json");
            HttpEntity<Score> request = new HttpEntity<Score>(s, headers);

            manager.getTemplate().postForObject(manager.getRestUrl()+"/score/s",request,Score.class);
        }catch (Exception e) {
            throw e;
        }
    }
}
