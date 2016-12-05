package com.example.denis.mystadium.Model;

import java.util.Date;

/**
 * Created by e140677 on 05/12/2016.
 */
public class InfoRencontre extends Rencontre{

    private String libelleChampionnat;
    private String nomStade;
    private String rue;
    private int numero;
    private String localite;
    private String nomEquipeDomicile;
    private String nomEquipeExterieur;

    public InfoRencontre() {
    }

    public InfoRencontre(int idRencontre, String niveau, int scoreFinalDomicile, int scoreFinalExterieur, int journee, Date dateDebut, int idChampionnat, int idEquipeDomicile, int idEquipeExterieur, int idTerrain, String libelleChampionnat, String nomStade, String rue, int numero, String localite, String nomEquipeDomicile, String nomEquipeExterieur) {
        super(idRencontre, niveau, scoreFinalDomicile, scoreFinalExterieur, journee, dateDebut, idChampionnat, idEquipeDomicile, idEquipeExterieur, idTerrain);
        this.libelleChampionnat = libelleChampionnat;
        this.nomStade = nomStade;
        this.rue = rue;
        this.numero = numero;
        this.localite = localite;
        this.nomEquipeDomicile = nomEquipeDomicile;
        this.nomEquipeExterieur = nomEquipeExterieur;
    }

    /**
     * @return the libelleChampionnat
     */
    public String getLibelleChampionnat() {
        return libelleChampionnat;
    }

    /**
     * @param libelleChampionnat the libelleChampionnat to set
     */
    public void setLibelleChampionnat(String libelleChampionnat) {
        this.libelleChampionnat = libelleChampionnat;
    }

    /**
     * @return the nomStade
     */
    public String getNomStade() {
        return nomStade;
    }

    /**
     * @param nomStade the nomStade to set
     */
    public void setNomStade(String nomStade) {
        this.nomStade = nomStade;
    }

    /**
     * @return the rue
     */
    public String getRue() {
        return rue;
    }

    /**
     * @param rue the rue to set
     */
    public void setRue(String rue) {
        this.rue = rue;
    }

    /**
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * @return the localite
     */
    public String getLocalite() {
        return localite;
    }

    /**
     * @param localite the localite to set
     */
    public void setLocalite(String localite) {
        this.localite = localite;
    }

    /**
     * @return the nomEquipeDomicile
     */
    public String getNomEquipeDomicile() {
        return nomEquipeDomicile;
    }

    /**
     * @param nomEquipeDomicile the nomEquipeDomicile to set
     */
    public void setNomEquipeDomicile(String nomEquipeDomicile) {
        this.nomEquipeDomicile = nomEquipeDomicile;
    }

    /**
     * @return the nomEquipeExterieur
     */
    public String getNomEquipeExterieur() {
        return nomEquipeExterieur;
    }

    /**
     * @param nomEquipeExterieur the nomEquipeExterieur to set
     */
    public void setNomEquipeExterieur(String nomEquipeExterieur) {
        this.nomEquipeExterieur = nomEquipeExterieur;
    }


}
