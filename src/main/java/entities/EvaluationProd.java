/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author daly
 */
public class EvaluationProd {
      private int id_membre;
    private int id_produit;
    private int rating;

    public EvaluationProd(int id_membre, int id_produit, int rating) {
        this.id_membre = id_membre;
        this.id_produit = id_produit;
        this.rating = rating;
    }

    public EvaluationProd(int rating) {
        this.rating = rating;
    }

    
    
    public int getId_membre() {
        return id_membre;
    }

    public void setId_membre(int id_membre) {
        this.id_membre = id_membre;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    
    
}
