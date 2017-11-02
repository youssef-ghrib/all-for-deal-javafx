/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Image;
import entities.Produit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataSource;

/**
 *
 * @author daly
 */
public class imageDao {
    
    ProduitDao produitDao = new ProduitDao();
    
       private Connection connection;
    private PreparedStatement pst;
    private ResultSet rs;

    public imageDao() {
        connection = DataSource.getInstance().getConnection();
    }
    
     public void insertImage(int b,String name) {
        String req = "insert into image (id_produit,imageName,updatedAt) values (?,?,?)";
        try {
            pst = connection.prepareStatement(req);
            pst.setInt(1,b);
            pst.setString(2,name);
   java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
            pst.setDate(3, sqlDate);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     public List<Image> findByProduct(Produit p) {
        String requete = "select * from image where id_produit=?";
        List<Image> images = new ArrayList<>();

        try {
            System.out.println("id" + p.getId());
            PreparedStatement ps = connection.prepareStatement(requete);
            ps.setInt(1, p.getId());
            ResultSet resultat = ps.executeQuery();

            while (resultat.next()) {

                Image i = new Image();

                i.setId(resultat.getInt(1));
                i.setImageName(resultat.getString(3));

                Produit produit = produitDao.findById(resultat.getInt(2));
                i.setProduit(produit);

                i.setUpdatedAt(resultat.getDate(4));
                images.add(i);
            }

        } catch (SQLException ex) {
            Logger.getLogger(imageDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return images;
    }
}
