package com.example.denis.mystadium.Model;

/**
 * Created by denis on 06-12-16.
 */

public class Equipe {
    private int idEquipe;
    private int matricule;
    private int nbrPoints;
    private int nbrGagnes;
    private int nbrNuls;
    private int nbrPerdus;
    private int nbrMarque;
    private int nbrEncaisse;
    private int idClub;
    private String genre;
    private int idCategorie;


    public Equipe(){}

    public Equipe(int idEquipe, int matricule, int nbrPoints, int nbrGagnes, int nbrNuls, int nbrPerdus, int nbrMarque, int nbrEncaisse, String genre,int idClub, int idCategorie) {
        this.idEquipe = idEquipe;
        this.matricule = matricule;
        this.nbrPoints = nbrPoints;
        this.nbrGagnes = nbrGagnes;
        this.nbrNuls = nbrNuls;
        this.nbrPerdus = nbrPerdus;
        this.nbrMarque = nbrMarque;
        this.nbrEncaisse = nbrEncaisse;
        this.idClub = idClub;
        this.genre = genre;
        this.idCategorie = idCategorie;
    }

    /**
     * @return the idEquipe
     */
    public int getIdEquipe() {
        return idEquipe;
    }

    /**
     * @param idEquipe the idEquipe to set
     */
    public void setIdEquipe(int idEquipe) {
        this.idEquipe = idEquipe;
    }


    /**
     * @return the matricule
     */
    public int getMatricule() {
        return matricule;
    }

    /**
     * @param matricule the matricule to set
     */
    public void setMatricule(int matricule) {
        this.matricule = matricule;
    }

    /**
     * @return the nbrPoints
     */
    public int getNbrPoints() {
        return nbrPoints;
    }

    /**
     * @param nbrPoints the nbrPoints to set
     */
    public void setNbrPoints(int nbrPoints) {
        this.nbrPoints = nbrPoints;
    }

    /**
     * @return the nbrGagnes
     */
    public int getNbrGagnes() {
        return nbrGagnes;
    }

    /**
     * @param nbrGagnes the nbrGagnes to set
     */
    public void setNbrGagnes(int nbrGagnes) {
        this.nbrGagnes = nbrGagnes;
    }

    /**
     * @return the nbrNuls
     */
    public int getNbrNuls() {
        return nbrNuls;
    }

    /**
     * @param nbrNuls the nbrNuls to set
     */
    public void setNbrNuls(int nbrNuls) {
        this.nbrNuls = nbrNuls;
    }

    /**
     * @return the nbrPerdus
     */
    public int getNbrPerdus() {
        return nbrPerdus;
    }

    /**
     * @param nbrPerdus the nbrPerdus to set
     */
    public void setNbrPerdus(int nbrPerdus) {
        this.nbrPerdus = nbrPerdus;
    }

    /**
     * @return the nbrMarque
     */
    public int getNbrMarque() {
        return nbrMarque;
    }

    /**
     * @param nbrMarque the nbrMarque to set
     */
    public void setNbrMarque(int nbrMarque) {
        this.nbrMarque = nbrMarque;
    }

    /**
     * @return the nbrEncaisse
     */
    public int getNbrEncaisse() {
        return nbrEncaisse;
    }

    /**
     * @param nbrEncaisse the nbrEncaisse to set
     */
    public void setNbrEncaisse(int nbrEncaisse) {
        this.nbrEncaisse = nbrEncaisse;
    }

    /**
     * @return the idClub
     */
    public int getIdClub() {
        return idClub;
    }

    /**
     * @param idClub the idClub to set
     */
    public void setIdClub(int idClub) {
        this.idClub = idClub;
    }

    /**
     * @return the genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * @param genre the genre to set
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * @return the idCategorie
     */
    public int getIdCategorie() {
        return idCategorie;
    }

    /**
     * @param idCategorie the idCategorie to set
     */
    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }
}
