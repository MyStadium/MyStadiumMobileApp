package com.example.denis.mystadium;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.denis.mystadium.Model.Suivre;
import com.example.denis.mystadium.Model.Utilisateur;

import org.springframework.http.HttpEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Created by denis on 30-11-16.
 */

public class profil_frag extends android.support.v4.app.Fragment{

    View myView;
    private Button btnGet;
    private TextView txt;
    private EditText editText;
    Utilisateur user;
    HttpRequestUser requestmanager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.profil, container, false);

        editText = (EditText) myView.findViewById(R.id.editText);
        txt = (TextView)myView.findViewById(R.id.textProfil);
        btnGet = (Button)myView.findViewById(R.id.btnget);
        btnGet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Suivre suivre = new Suivre(1,3);
                MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
                headers.add("Content-Type", "application/json");

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                HttpEntity<Suivre> request = new HttpEntity<Suivre>(suivre, headers);

                String response = restTemplate.postForObject("http://192.168.128.13:8081/MyStadium-REST-DEVILLE-BRONSART/resources/suivre",request,String.class);
                System.out.println(response);

            }
        });
        return myView;
    }

}
