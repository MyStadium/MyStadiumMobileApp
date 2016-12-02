package com.example.denis.mystadium.services;

import com.example.denis.mystadium.models.Suivre;
import com.example.denis.mystadium.restrequest.RequestManager;

import java.util.Map;

/**
 * Created by Utilisateur on 02-12-16.
 */

public class ServiceSuivre {
    private RequestManager manager;

    public ServiceSuivre (RequestManager manager) {
        this.manager = manager;
    }
    public void postRequest(String url, Suivre sui){
        this.manager.postRequest(url,sui);
    }
    public void deleteRequest(String url,Map<String,String> params){
        this.manager.deleteRequest(url,params);
    }
}
