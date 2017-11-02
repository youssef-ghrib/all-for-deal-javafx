/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Date;

/**
 *
 * @author joseph
 */
public class Image {

    private int id;
    private String imageName;
    private Produit produit;
    private Date updatedAt;

    public Image() {
    }

    public Image(int id, String imageName, Produit produit, Date updatedAt) {
        this.id = id;
        this.imageName = imageName;
        this.produit = produit;
        this.updatedAt = updatedAt;
    }

    public Image(String imageName, Produit produit, Date updatedAt) {
        this.imageName = imageName;
        this.produit = produit;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}
