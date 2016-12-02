package com.example.denis.mystadium.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * Created by denis on 02-12-16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class InfoMembre extends Membre{
    private String nomClub;
    private String categorieAge;
    private String strRole;

    public InfoMembre() {
    }

    public InfoMembre(int id, int idUtilisateur, String nom, String prenom, Date dateNaissance, String nationalite, int numero, String matricule, int taille, int poids, String GD, String poste, String genre, int idRole, int idEquipe, String categorieAge, String nomClub, String role) {
        super(id, idUtilisateur, nom, prenom, dateNaissance, nationalite, numero, matricule, taille, poids, GD, poste, genre, idRole, idEquipe);
        this.nomClub = nomClub;
        this.categorieAge = categorieAge;
        this.strRole = role;
    }

    /**
     * @return the nomClub
     */
    public String getNomClub() {
        return nomClub;
    }

    /**
     * @param nomClub the nomClub to set
     */
    public void setNomClub(String nomClub) {
        this.nomClub = nomClub;
    }

    /**
     * @return the categorieAge
     */
    public String getCategorieAge() {
        return categorieAge;
    }

    /**
     * @param categorieAge the categorieAge to set
     */
    public void setCategorieAge(String categorieAge) {
        this.categorieAge = categorieAge;
    }

    public Membre transformToMembre(){
        return new Membre(getId(), getIdUtilisateur(), getNom(), getPrenom(),getDateNaissance(), getNationalite(), getNumero(), getMatricule(), getTaille(), getPoids(), getGD(), getPoste(), getGenre(), getIdRole(), getIdEquipe());
    }

    /**
     * @return the strRole
     */
    public String getStrRole() {
        return strRole;
    }

    /**
     * @param strRole the strRole to set
     */
    public void setStrRole(String strRole) {
        this.strRole = strRole;
    }

    @Override
    public String toString(){
        return getNom() + " " +getPrenom();
    }
}
