package com.example.denis.mystadium.Model;

/**
 * Created by denis on 02-12-16.
 */

public class Personne {
    private int id;
    private String nom;
    private String prenom;
    private int idRole;

    public Personne() {
    }

    public Personne(int id, String nom, String prenom, int idRole) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.idRole = idRole;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @param prenom the prenom to set
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * @return the idRole
     */
    public int getIdRole() {
        return idRole;
    }

    /**
     * @param idRole the idRole to set
     */
    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }
}
