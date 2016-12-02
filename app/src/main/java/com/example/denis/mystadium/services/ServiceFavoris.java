package com.example.denis.mystadium.services;

import com.example.denis.mystadium.models.Favoris;
import com.example.denis.mystadium.restrequest.RequestManager;

import java.util.Map;

/**
 * Created by Utilisateur on 02-12-16.
 */

public class ServiceFavoris {
    private RequestManager manager;

    public ServiceFavoris() {
        this.manager = new RequestManager();
    }
    public void postRequest(String url, Favoris fav){
        this.manager.postRequest(url,fav);
    }
    public void deleteRequest(String url,Map<String,String> params){
        this.manager.deleteRequest(url,params);
    }


}
