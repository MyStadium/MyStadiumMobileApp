package com.example.denis.mystadium.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by denis on 06-12-16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class InfoEquipe extends Equipe implements Parcelable{
    private String nomClub;
    private String categorieAge;

    public InfoEquipe() {
    }
    public InfoEquipe(int idEquipe, int matricule, int nbrPoints, int nbrGagnes, int nbrNuls, int nbrPerdus, int nbrMarque,  int nbrEncaisse,String genre, int idClub, int idCategorie, String categorie, String nomClub) {
        super(idEquipe,matricule,nbrPoints,nbrGagnes,nbrNuls, nbrPerdus, nbrMarque,  nbrEncaisse, genre,idClub, idCategorie);
        this.nomClub = nomClub;
        this.categorieAge = categorie;
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

    @Override
    public String toString(){
        return this.categorieAge + "-" + this.nomClub;
    }

    public InfoEquipe(Parcel in){
        String[] data = new String[3];

        in.readStringArray(data);
        setIdEquipe(Integer.parseInt(data[0]));
        setCategorieAge(data[1]);
        setNomClub(data[2]);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                ""+getIdEquipe(),
                getCategorieAge(),
                getNomClub()
        });
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public InfoEquipe createFromParcel(Parcel in) {
            return new InfoEquipe(in);
        }

        public InfoEquipe[] newArray(int size) {
            return new InfoEquipe[size];
        }
    };
}
