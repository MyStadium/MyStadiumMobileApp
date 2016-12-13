package com.example.denis.mystadium.Model;

/**
 * Created by denis on 09-12-16.
 */

public class Vote {
    private int niveauJeu;
    private int fairPlay;
    private int ambiance;
    private int idUtilisateur;
    private int idRencontre;

    public Vote() {
    }

    public Vote(int niveauJeu, int fairPlay, int ambiance, int idUtilisateur, int idRencontre) {
        this.niveauJeu = niveauJeu;
        this.fairPlay = fairPlay;
        this.ambiance = ambiance;
        this.idUtilisateur = idUtilisateur;
        this.idRencontre = idRencontre;
    }


    /**
     * @return the niveauJeu
     */
    public int getNiveauJeu() {
        return niveauJeu;
    }

    /**
     * @param niveauJeu the niveauJeu to set
     */
    public void setNiveauJeu(int niveauJeu) {
        this.niveauJeu = niveauJeu;
    }

    /**
     * @return the fairPlay
     */
    public int getFairPlay() {
        return fairPlay;
    }

    /**
     * @param fairPlay the fairPlay to set
     */
    public void setFairPlay(int fairPlay) {
        this.fairPlay = fairPlay;
    }

    /**
     * @return the ambiance
     */
    public int getAmbiance() {
        return ambiance;
    }

    /**
     * @param ambiance the ambiance to set
     */
    public void setAmbiance(int ambiance) {
        this.ambiance = ambiance;
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
}
