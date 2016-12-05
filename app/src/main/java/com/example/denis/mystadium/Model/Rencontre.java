package com.example.denis.mystadium.Model;

import java.util.Date;

/**
 * Created by e140677 on 05/12/2016.
 */
public class Rencontre {
    private int idRencontre;
    private String niveau;
    private int scoreFinalDomicile;
    private int scoreFinalExterieur;
    private int journee;
    private Date dateHeure;
    private int idChampionnat;
    private int idEquipeDomicile;
    private int idEquipeExterieur;
    private int idTerrain;

    public Rencontre() {
    }

    public Rencontre(int idRencontre, String niveau, int scoreDomicile, int scoreExterieur, int journee, Date dateDebut, int idChampionnat, int idEquipeDomicile, int idEquipeExterieur, int idTerrain) {
        this.idRencontre = idRencontre;
        this.niveau = niveau;
        this.scoreFinalDomicile = scoreDomicile;
        this.scoreFinalExterieur = scoreExterieur;
        this.journee = journee;
        this.dateHeure = dateDebut;
        this.idChampionnat = idChampionnat;
        this.idEquipeDomicile = idEquipeDomicile;
        this.idEquipeExterieur = idEquipeExterieur;
        this.idTerrain = idTerrain;
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
     * @return the niveau
     */
    public String getNiveau() {
        return niveau;
    }

    /**
     * @param niveau the niveau to set
     */
    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }



    /**
     * @return the journee
     */
    public int getJournee() {
        return journee;
    }

    /**
     * @param journee the journee to set
     */
    public void setJournee(int journee) {
        this.journee = journee;
    }

    /**
     * @return the dateDebut
     */
    public Date getDateHeure() {
        return dateHeure;
    }

    /**
     * @param dateDebut the dateDebut to set
     */
    public void setDateHeure(Date dateDebut) {
        this.dateHeure = dateDebut;
    }

    /**
     * @return the idEquipeDomicile
     */
    public int getIdEquipeDomicile() {
        return idEquipeDomicile;
    }

    /**
     * @param idEquipeDomicile the idEquipeDomicile to set
     */
    public void setIdEquipeDomicile(int idEquipeDomicile) {
        this.idEquipeDomicile = idEquipeDomicile;
    }

    /**
     * @return the idEquipeExterieur
     */
    public int getIdEquipeExterieur() {
        return idEquipeExterieur;
    }

    /**
     * @param idEquipeExterieur the idEquipeExterieur to set
     */
    public void setIdEquipeExterieur(int idEquipeExterieur) {
        this.idEquipeExterieur = idEquipeExterieur;
    }

    /**
     * @return the idTerrain
     */
    public int getIdTerrain() {
        return idTerrain;
    }

    /**
     * @param idTerrain the idTerrain to set
     */
    public void setIdTerrain(int idTerrain) {
        this.idTerrain = idTerrain;
    }

    /**
     * @return the idChampionnat
     */
    public int getIdChampionnat() {
        return idChampionnat;
    }

    /**
     * @param idChampionnat the idChampionnat to set
     */
    public void setIdChampionnat(int idChampionnat) {
        this.idChampionnat = idChampionnat;
    }

    /**
     * @return the scoreFinalDomicile
     */
    public int getScoreFinalDomicile() {
        return scoreFinalDomicile;
    }

    /**
     * @param scoreFinalDomicile the scoreFinalDomicile to set
     */
    public void setScoreFinalDomicile(int scoreFinalDomicile) {
        this.scoreFinalDomicile = scoreFinalDomicile;
    }

    /**
     * @return the scoreFinalExterieur
     */
    public int getScoreFinalExterieur() {
        return scoreFinalExterieur;
    }

    /**
     * @param scoreFinalExterieur the scoreFinalExterieur to set
     */
    public void setScoreFinalExterieur(int scoreFinalExterieur) {
        this.scoreFinalExterieur = scoreFinalExterieur;
    }


}
