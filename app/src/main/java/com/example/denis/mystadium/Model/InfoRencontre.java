package com.example.denis.mystadium.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by e140677 on 05/12/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InfoRencontre extends Rencontre implements Parcelable{

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
    public InfoRencontre(Parcel in){
        String[] data = new String[6];
        in.readStringArray(data);
        setIdRencontre(Integer.parseInt(data[0]));
        setNomEquipeDomicile(data[1]);
        setNomEquipeExterieur(data[2]);
        setScoreFinalDomicile(Integer.parseInt(data[3]));
        setScoreFinalExterieur(Integer.parseInt(data[4]));
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy-hh-mm");
        try{
            Date date = format.parse(data[5]);
            setDateHeure(date);
        }catch(Exception e){}

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy-hh-mm");
        String date = format.format(getDateHeure());
        parcel.writeStringArray(new String[]{
                ""+getIdRencontre(),
                getNomEquipeDomicile(),
                getNomEquipeExterieur(),
                ""+getScoreFinalDomicile(),
                ""+getScoreFinalExterieur(),
                date
        });
    }
    @Override
    public String toString(){
        return getNomEquipeDomicile()+"  "+getScoreFinalDomicile()+" - "+getScoreFinalExterieur()+"  "+getNomEquipeExterieur() ;
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public InfoRencontre createFromParcel(Parcel in) {
            return new InfoRencontre(in);
        }

        public InfoRencontre[] newArray(int size) {
            return new InfoRencontre[size];
        }
    };
}
