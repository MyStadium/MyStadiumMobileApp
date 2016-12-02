package com.example.denis.mystadium.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by Utilisateur on 02-12-16.
 */

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Favoris {
    private int idUtilisateur;
    private int idEquipe;

    public Favoris(int idUtilisateur, int idEquipe) {
        this.idUtilisateur = idUtilisateur;
        this.idEquipe = idEquipe;
    }

    public int getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(int idEquipe) {
        this.idEquipe = idEquipe;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
}
