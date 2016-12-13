package com.example.denis.mystadium.Model;

import java.util.Date;

/**
 * Created by denis on 10-12-16.
 */

public class Score {
    private int idScore;
    private int scoreDomicile;
    private int scoreExterieur;
    private Date heure;
    // private boolean certifie;
    private int idRencontre;
    private int idUtilisateur;


    public Score() {
    }

    public Score(int idScore, int scoreDomicile, int scoreExterieur, Date heure, boolean certifie, int idRencontre, int idUtilisateur) {
        this.idScore = idScore;
        this.scoreDomicile = scoreDomicile;
        this.scoreExterieur = scoreExterieur;
        //this.certifie = certifie;
        this.heure = heure;
        this.idRencontre = idRencontre;
        this.idUtilisateur = idUtilisateur;
    }

    /**
     * @return the idScore
     */
    public int getIdScore() {
        return idScore;
    }

    /**
     * @param idScore the idScore to set
     */
    public void setIdScore(int idScore) {
        this.idScore = idScore;
    }

    /**
     * @return the scoreDomicile
     */
    public int getScoreDomicile() {
        return scoreDomicile;
    }

    /**
     * @param scoreDomicile the scoreDomicile to set
     */
    public void setScoreDomicile(int scoreDomicile) {
        this.scoreDomicile = scoreDomicile;
    }

    /**
     * @return the scoreExterieur
     */
    public int getScoreExterieur() {
        return scoreExterieur;
    }

    /**
     * @param scoreExterieur the scoreExterieur to set
     */
    public void setScoreExterieur(int scoreExterieur) {
        this.scoreExterieur = scoreExterieur;
    }

    /**
     * @return the heure
     */
    public Date getHeure() {
        return heure;
    }

    /**
     * @param heure the heure to set
     */
    public void setHeure(Date heure) {
        this.heure = heure;
    }


    /**
     * @return the idRencontre
     */
    public int getIdRencontre() {
        return idRencontre;
    }

    /**
     * @param idRencontre the idRencontre to set
     */
    public void setIdRencontre(int idRencontre) {
        this.idRencontre = idRencontre;
    }

    /**
     * @return the idUtilisateur
     */
    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    /**
     * @param idUtilisateur the idUtilisateur to set
     */
    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }



    /**
     * @return the certifie
     */

    /*
    public boolean getCertifie() {
        return certifie;
    }

    /**
     * @param certifie the certifie to set
     */
    /*
    public void setCertifie(boolean certifie) {
        this.certifie = certifie;
    }*/

}
