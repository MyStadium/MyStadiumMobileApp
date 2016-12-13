package com.example.denis.mystadium.Model;

/**
 * Created by denis on 12-12-16.
 */

public class Sport {
    private int idSport;
    private String nom;
    private int nbrPeriodes;
    private int tempsPeriode;
    private int nbrJoueur;

    public Sport() {
    }

    public Sport(int idSport, String nom, int nbrPeriodes, int tempsPeriode, int nbrJoueur) {
        this.idSport = idSport;
        this.nom = nom;
        this.nbrPeriodes = nbrPeriodes;
        this.tempsPeriode = tempsPeriode;
        this.nbrJoueur = nbrJoueur;
    }

    /**
     * @return the idSport
     */
    public int getIdSport() {
        return idSport;
    }

    /**
     * @param idSport the idSport to set
     */
    public void setIdSport(int idSport) {
        this.idSport = idSport;
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
     * @return the nbrPeriodes
     */
    public int getNbrPeriodes() {
        return nbrPeriodes;
    }

    /**
     * @param nbrPeriodes the nbrPeriodes to set
     */
    public void setNbrPeriodes(int nbrPeriodes) {
        this.nbrPeriodes = nbrPeriodes;
    }

    /**
     * @return the tempsPeriode
     */
    public int getTempsPeriode() {
        return tempsPeriode;
    }

    /**
     * @param tempsPeriode the tempsPeriode to set
     */
    public void setTempsPeriode(int tempsPeriode) {
        this.tempsPeriode = tempsPeriode;
    }

    /**
     * @return the nbrJoueur
     */
    public int getNbrJoueur() {
        return nbrJoueur;
    }

    /**
     * @param nbrJoueur the nbrJoueur to set
     */
    public void setNbrJoueur(int nbrJoueur) {
        this.nbrJoueur = nbrJoueur;
    }
}
