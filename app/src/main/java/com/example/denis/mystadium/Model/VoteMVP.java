package com.example.denis.mystadium.Model;

/**
 * Created by denis on 13-12-16.
 */

public class VoteMVP {
    private int idUtilisateur;
    private int idMembre;
    private int idRencontre;

    public VoteMVP() {
    }

    public VoteMVP(int idUtilisateur, int idMembre, int idRencontre) {
        this.idUtilisateur = idUtilisateur;
        this.idMembre = idMembre;
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
     * @return the idMembre
     */
    public int getIdMembre() {
        return idMembre;
    }

    /**
     * @param idMembre the idMembre to set
     */
    public void setIdMembre(int idMembre) {
        this.idMembre = idMembre;
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
