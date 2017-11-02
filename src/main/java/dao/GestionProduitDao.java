/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Categorie;
import entities.Membre;
import entities.Produit;
import entities.Sous_categorie;
import utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wael
 */
public class GestionProduitDao  {

    private Connection connection;

    public GestionProduitDao() {
        connection = DataSource.getInstance().getConnection();
    }

    public void ValiderProduit(Produit p) {
        String req = "update produit  set etat=?  where id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, "valide");
            ps.setInt(2, p.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GestionProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public void RefuserProduit(Produit p) {
        String req = "update produit  set etat=?  where id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, "refuse");
            ps.setInt(2, p.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GestionProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Produit> DisplayAllProduit() throws SQLException {
        List<Produit> L = new ArrayList<>();

        String req = "SELECT i.*, u.nom , cc.nom FROM produit i LEFT JOIN sous_categorie u ON (i.id_sous_categorie = u.id)"
                + " LEFT JOIN membre cc ON (i.id_fournisseur = cc.id)";

        PreparedStatement pst = connection.prepareStatement(req);
        ResultSet rst = pst.executeQuery();
        while (rst.next()) {
            Sous_categorie s_categorie = new Sous_categorie();
            Membre membre = new Membre();
            int nom_cat = rst.getInt(2);
            int nom_mem = rst.getInt(3);
            s_categorie.setId(nom_cat);
            s_categorie.setNom(rst.getString(11));
            membre.setId(nom_mem);
            membre.setNom(rst.getString(12));
            int id = rst.getInt(1);
            String nom = rst.getString(4);
            float prix = rst.getFloat(5);
            int reduction = rst.getInt(6);
            int points = rst.getInt(7);
            String etat = rst.getString(8);
            String date_lancement = rst.getString(9);
            String couleur = rst.getString(10);
            Produit p = new Produit();
            p.setId(id);
//            p.setCategorie(s_categorie);
            p.setFournisseur(membre);
            p.setNom(nom);
            p.setPrix(prix);
            p.setReduction(reduction);
            p.setPoints(points);
            p.setEtat(etat);
            p.setDate_lancement(date_lancement);
            p.setCouleur(couleur);
            L.add(p);
        }
        return L;
    }

       public List<Produit> findProduitByEtat(String etat) throws SQLException {
        String req = "select * from produit where etat like ? ";
        List<Produit> L = new ArrayList<>();

        PreparedStatement pst = connection.prepareStatement(req);
        pst.setString(1, etat + "%");
        ResultSet rst = pst.executeQuery();

        while (rst.next()) {
            int id = rst.getInt(1);
            String nom = rst.getString(4);
            float prix = rst.getFloat(5);
            int reduction = rst.getInt(6);
            int points = rst.getInt(7);
            String etat1 = rst.getString(8);
            String date_lancement = rst.getString(9);
            String couleur = rst.getString(10);

            Produit p = new Produit();
            p.setId(id);
            p.setNom(nom);
            p.setPrix(prix);
            p.setReduction(reduction);
            p.setPoints(points);
            p.setEtat(etat1);
            p.setDate_lancement(date_lancement);
            p.setCouleur(couleur);
            L.add(p);
        }
        return L;
    }
 
    public void changerNbrPointsById(int nbrPoints, int id) {
        String req = "UPDATE produit SET points=? where id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, nbrPoints);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GestionProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    public int GetStatProd(int mois) throws SQLException {
        int re = 0;
        String req = "SELECT count(*) FROM produit WHERE MONTH(STR_TO_DATE(`date_lancement`,'%Y-%m')) = ?";

        PreparedStatement pst = connection.prepareStatement(req);

        pst.setInt(1, mois);

        ResultSet rst = pst.executeQuery();

        while (rst.next()) {
            re = rst.getInt(1);
        }

        return re;
    }

}
