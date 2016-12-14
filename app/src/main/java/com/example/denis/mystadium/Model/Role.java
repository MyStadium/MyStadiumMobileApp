package com.example.denis.mystadium.Model;

/**
 * Created by denis on 14-12-16.
 */

public class Role {
    private int idRole;
    private String libelle;

    public Role() {
    }

    public Role(int idRole, String libelle) {
        this.idRole = idRole;
        this.libelle = libelle;
    }

    /**
     * @return the idRole
     */
    public int getIdRole() {
        return idRole;
    }

    /**
     * @param idRole the idRole to set
     */
    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    /**
     * @return the libelle
     */
    public String getLibelle() {
        return libelle;
    }

    /**
     * @param libelle the libelle to set
     */
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
