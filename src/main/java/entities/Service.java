/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author omar
 */
public class Service {
    private int id;
    private String nom;
    private String categorie;
    private String description;
    private Membre id_fournisseur;

    public Service() {
    }
    
    public Service(int id, String nom, String categorie, String description) {
        this.id = id;
        this.nom = nom;
        this.categorie = categorie;
        this.description = description;
    }

    public Service(int id, String nom, String categorie, String description, Membre id_fournisseur) {
        this.id = id;
        this.nom = nom;
        this.categorie = categorie;
        this.description = description;
        this.id_fournisseur = id_fournisseur;
    }

    @Override
    public String toString() {
        return this.nom;
    }

    public String getCategorie() {
        return categorie;
    }

    public String getDescription() {
        return description;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setId_fournisseur(Membre id_fournisseur) {
        this.id_fournisseur = id_fournisseur;
    }

    public Membre getId_fournisseur() {
        return id_fournisseur;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
}
