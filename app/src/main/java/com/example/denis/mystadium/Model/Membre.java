package com.example.denis.mystadium.Model;

import java.util.Date;

/**
 * Created by denis on 02-12-16.
 */

public class Membre extends Personne{
        private int idUtilisateur;
        private Date dateNaissance;
        private String nationalite;
        private int numero;
        private String matricule;
        private int taille;
        private int poids;
        private String GD;
        private String poste;
        private int idRole;
        private int idEquipe;
        private String genre;

        public Membre() {
        }

        public Membre(int id, int idUtilisateur, String nom, String prenom, Date dateNaissance, String nationalite, int numero, String matricule, int taille, int poids, String GD, String poste, String genre, int idRole,  int idEquipe) {
            super(id, nom, prenom, idRole);
            this.idUtilisateur = idUtilisateur;
            this.dateNaissance = dateNaissance;
            this.nationalite = nationalite;
            this.numero = numero;
            this.matricule = matricule;
            this.taille = taille;
            this.poids = poids;
            this.GD = GD;
            this.poste = poste;
            this.idRole = idRole;
            this.idEquipe = idEquipe;
            this.genre = genre;
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
         * @return the dateNaissance
         */
        public Date getDateNaissance() {
            return dateNaissance;
        }

        /**
         * @param dateNaissance the dateNaissance to set
         */
        public void setDateNaissance(Date dateNaissance) {
            this.dateNaissance = dateNaissance;
        }

        /**
         * @return the nationalite
         */
        public String getNationalite() {
            return nationalite;
        }

        /**
         * @param nationalite the nationalite to set
         */
        public void setNationalite(String nationalite) {
            this.nationalite = nationalite;
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
         * @return the matricule
         */
        public String getMatricule() {
            return matricule;
        }

        /**
         * @param matricule the matricule to set
         */
        public void setMatricule(String matricule) {
            this.matricule = matricule;
        }

        /**
         * @return the taille
         */
        public int getTaille() {
            return taille;
        }

        /**
         * @param taille the taille to set
         */
        public void setTaille(int taille) {
            this.taille = taille;
        }

        /**
         * @return the poids
         */
        public int getPoids() {
            return poids;
        }

        /**
         * @param poids the poids to set
         */
        public void setPoids(int poids) {
            this.poids = poids;
        }

        /**
         * @return the GD
         */
        public String getGD() {
            return GD;
        }

        /**
         * @param GD the GD to set
         */
        public void setGD(String GD) {
            this.GD = GD;
        }

        /**
         * @return the poste
         */
        public String getPoste() {
            return poste;
        }

        /**
         * @param poste the poste to set
         */
        public void setPoste(String poste) {
            this.poste = poste;
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
}
