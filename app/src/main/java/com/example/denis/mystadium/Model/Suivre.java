package com.example.denis.mystadium.Model;

/**
 * Created by Utilisateur on 02-12-16.
 */

public class Suivre {
    private int idUtilisateur;
    private int idMembre;

    public Suivre(){}

    public Suivre(int idMembre, int idUtilisateur) {
        this.idMembre = idMembre;
        this.idUtilisateur = idUtilisateur;
    }

    public int getIdMembre() {
        return idMembre;
    }

    public void setIdMembre(int idMembre) {
        this.idMembre = idMembre;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
}
