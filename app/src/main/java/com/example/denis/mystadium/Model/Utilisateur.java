package com.example.denis.mystadium.Model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by denis on 01-12-16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private String login;
    private String pass;
    private String email;
    private int nbrBonScore;
    private int idRole;

    public Utilisateur(){};

    public Utilisateur (int id, String nom, String prenom, String login, String pass, String email, int nbrBonScore, int idRole){
        this.setId(id);
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setLogin(login);
        this.setPass(pass);
        this.setEmail(email);
        this.setNbrBonScore(nbrBonScore);
        this.setIdRole(idRole);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNbrBonScore() {
        return nbrBonScore;
    }

    public void setNbrBonScore(int nbrBonScore) {
        this.nbrBonScore = nbrBonScore;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }
}
