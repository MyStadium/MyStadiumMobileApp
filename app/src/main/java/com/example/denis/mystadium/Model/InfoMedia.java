package com.example.denis.mystadium.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by denis on 09-12-16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class InfoMedia extends Media{
    private String loginUser;

    public InfoMedia() {
    }

    public InfoMedia(int idMedia, String URLMedia, String commentaire, int idUtilisateur, int idRencontre, String loginUser) {
        super(idMedia, URLMedia, commentaire, idUtilisateur, idRencontre);
        this.loginUser = loginUser;
    }

    /**
     * @return the loginUser
     */
    public String getLoginUser() {
        return loginUser;
    }

    /**
     * @param loginUser the loginUser to set
     */
    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }
}
