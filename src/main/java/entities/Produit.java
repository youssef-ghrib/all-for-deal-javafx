/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;


import java.util.Date;

/**
 *
 * @author daly
 */
public class Produit {
    private int id;
    private String nom;
    private float prix;
    private int reduction;
    private int points;
    private String etat;
    private String date_lancement;
    private String couleur;
    private String images;
    private Membre fournisseur;
    private Sous_categorie sous_categorie;
    private Categorie categorie;
    public Produit() {
    }

   

    public Produit(String nom, float prix, int reduction, int points, String etat, String date_lancement, String couleur, String images, Membre fournisseur, Sous_categorie sous_categorie,Categorie categorie) {
        this.nom = nom;
        this.prix = prix;
        this.reduction = reduction;
        this.points = points;
        this.etat = etat;
        this.date_lancement = date_lancement;
        this.couleur = couleur;
        this.images = images;
        this.fournisseur = fournisseur;
        this.sous_categorie = sous_categorie;
        this.categorie = categorie;
    
    }

    public Produit(String nom, float prix, String couleur, String images, int  reduction,String etat,int points) {
      this.nom = nom;
    this.couleur=couleur;
    this.images=images;
    this.prix=prix;
    this.reduction=reduction;
    this.etat=etat;
//    this.date_lancement=date_lancement;
    this.points=points;
    };
    
    
      public Produit(String nom, float prix, int reduction, String couleur) {
        this.nom = nom;
        this.prix = prix;
        this.reduction = reduction;
        this.couleur = couleur;

    }

    public Produit(int id, String nom, float prix, int reduction, int points, Sous_categorie sous_categorie, String date_lancement, String couleur, Categorie categorie) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.reduction = reduction;
        this.points = points;
        this.sous_categorie = sous_categorie;
        this.date_lancement = date_lancement;
        this.couleur = couleur;
        this.categorie = categorie;
    }

    public Produit(int id, String nom, float prix, int reduction, int points, String etat, String date_lancement, String couleur, Categorie categorie) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.reduction = reduction;
        this.points = points;
        this.etat = etat;
        this.date_lancement = date_lancement;
        this.couleur = couleur;
        this.categorie = categorie;
    }

    public Produit(int id, String nom, float prix, int reduction, int points, String etat, String date_lancement, String couleur) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.reduction = reduction;
        this.points = points;
        this.etat = etat;
        this.date_lancement = date_lancement;
        this.couleur = couleur;
    }

    public Produit(int id, String nom, float prix, int reduction, int points, String etat, String dateLancement, String couleur, Membre fournisseur, Sous_categorie souCategorie) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.reduction = reduction;
        this.points = points;
        this.etat = etat;
        this.date_lancement = dateLancement;
        this.couleur = couleur;
        this.fournisseur = fournisseur;
        this.sous_categorie = souCategorie;
    }

    public Produit(String nom, float prix, int reduction, int points, String etat, String dateLancement, String couleur, Membre fournisseur, Sous_categorie souCategorie) {
        this.nom = nom;
        this.prix = prix;
        this.reduction = reduction;
        this.points = points;
        this.etat = etat;
        this.date_lancement = dateLancement;
        this.couleur = couleur;
        this.fournisseur = fournisseur;
        this.sous_categorie = souCategorie;
    }

    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", nom=" + nom + ", prix=" + prix + ", reduction=" + reduction + ", points=" + points + ", etat=" + etat + ", dateLancement=" + date_lancement + ", couleur=" + couleur + ", fournisseur=" + fournisseur + ", souCategorie=" + sous_categorie + '}';
    }
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public double getPrix() {
        return prix;
    }

    public int getReduction() {
        return reduction;
    }

    public int getPoints() {
        return points;
    }

    public String getEtat() {
        return etat;
    }

    public String getDate_lancement() {
        return date_lancement;
    }

    public String getCouleur() {
        return couleur;
    }

    public String getImages() {
        return images;
    }

    public Membre getFournisseur() {
        return fournisseur;
    }

    public Sous_categorie getSous_categorie() {
        return sous_categorie;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public void setReduction(int reduction) {
        this.reduction = reduction;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setDate_lancement(String date_lancement) {
        this.date_lancement = date_lancement;
    }
    

    public void setImages(String images) {
        this.images = images;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFournisseur(Membre fournisseur) {
        this.fournisseur = fournisseur;
    }

    public void setSous_categorie(Sous_categorie sous_categorie) {
        this.sous_categorie = sous_categorie;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Produit(int id, String nom, float prix, int reduction, int points, String etat, String date_lancement, String couleur, String images, Membre fournisseur, Sous_categorie sous_categorie, Categorie categorie) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.reduction = reduction;
        this.points = points;
        this.etat = etat;
        this.date_lancement = date_lancement;
        this.couleur = couleur;
        this.images = images;
        this.fournisseur = fournisseur;
        this.sous_categorie = sous_categorie;
        this.categorie = categorie;
    }

  

}

