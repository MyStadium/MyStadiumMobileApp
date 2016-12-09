package com.example.denis.mystadium.Model;

/**
 * Created by denis on 09-12-16.
 */

public class Media {
    private int idMedia;
    private String URLMedia;
    private String commentaire;
    private int idUtilisateur;
    private int idRencontre;

    public Media() {
    }

    public Media(int idMedia, String URLMedia, String commentaire, int idUtilisateur, int idRencontre) {
        this.idMedia = idMedia;
        this.URLMedia = URLMedia;
        this.commentaire = commentaire;
        this.idUtilisateur = idUtilisateur;
        this.idRencontre = idRencontre;
    }

    /**
     * @return the idMedia
     */
    public int getIdMedia() {
        return idMedia;
    }

    /**
     * @param idMedia the idMedia to set
     */
    public void setIdMedia(int idMedia) {
        this.idMedia = idMedia;
    }

    /**
     * @return the URLMedia
     */
    public String getURLMedia() {
        return URLMedia;
    }

    /**
     * @param URLMedia the URLMedia to set
     */
    public void setURLMedia(String URLMedia) {
        this.URLMedia = URLMedia;
    }

    /**
     * @return the commentaire
     */
    public String getCommentaire() {
        return commentaire;
    }

    /**
     * @param commentaire the commentaire to set
     */
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
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
