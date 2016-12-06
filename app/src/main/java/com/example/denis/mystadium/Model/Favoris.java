package com.example.denis.mystadium.Model;

/**
 * Created by Utilisateur on 02-12-16.
 */

public class Favoris {
    private int idUtilisateur;
    private int idEquipe;

    public Favoris(){}

    public Favoris(int idU, int idE){
        this.idEquipe = idE;
        this.idUtilisateur = idU;
    }

    public int getIdUtilisateur(){return this.idUtilisateur;}

    public int getIdEquipe(){return this.idEquipe;}

    public void setIdUtilisateur(int id){this.idUtilisateur = id;}

    public void setIdEquipe(int id){this.idEquipe = id;}


}
