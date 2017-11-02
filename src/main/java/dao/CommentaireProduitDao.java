/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import entities.CommentaireProd;
import entities.EvaluationProd;
import entities.Produit;
import utils.DataSource;

/**
 *
 * @author daly
 */
public class CommentaireProduitDao {
           final  private Connection connection;
    private PreparedStatement pst;
            private ResultSet rs;


    public CommentaireProduitDao() {
        connection = DataSource.getInstance().getConnection();
    }

    public void addCommentaire(CommentaireProd c) {
      
            String req ="INSERT INTO `commentaire_Prod`(`id_membre`,`id_produit`,`texte`) VALUES (?,?,?)";
            try {
            pst = connection.prepareStatement(req);
            pst.setInt(1,c.getId_membre());
            pst.setInt(2,c.getId_produit());
            pst.setString(3, c.getTexte());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CommentaireProduitDao.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    
       public void addRating(EvaluationProd e) {
      
            String req ="INSERT INTO `evaluation_prod`(`id_membre`,`id_produit`,`rating`) VALUES (?,?,?)";
            try {
            pst = connection.prepareStatement(req);
            pst.setInt(1,e.getId_membre() );
            pst.setInt(2, e.getId_produit());
            pst.setInt(3, e.getRating());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CommentaireProduitDao.class.getName()).log(Level.SEVERE, null, ex);
    }
}
       public List<CommentaireProd> AfficheComment(int id)
       {        List<CommentaireProd> s = new ArrayList<>();    
           try {
           String req="select texte from commentaire_prod where id_produit=?";
           pst = connection.prepareStatement(req);
           pst.setInt(1,id);
           rs = pst.executeQuery();
           while (rs.next()) {
               CommentaireProd cmp=new CommentaireProd(rs.getString(1));
            s.add(cmp);
            
           }       } catch (SQLException ex) {
                   Logger.getLogger(CommentaireProduitDao.class.getName()).log(Level.SEVERE, null, ex);
               }
       return s;
}
      public int calculRating(int id)
       {        //List<EvaluationProd> s = new ArrayList<>();  
          int x = 0;
           try {
           String req="select round(avg(rating)) from evaluation_prod where id_produit=?";
           pst = connection.prepareStatement(req);
           pst.setInt(1,id);
           rs = pst.executeQuery();
           while (rs.next()) {
               //EvaluationProd cmp=new EvaluationProd(rs.getInt(2));
           // s.add(cmp);
               x= rs.getInt(1);
            
           }       } catch (SQLException ex) {
                   Logger.getLogger(CommentaireProduitDao.class.getName()).log(Level.SEVERE, null, ex);
               }
       return x;
}
      public String getImageName (int id)
      {
      
       String x = null;
           try {
           String req="select imageName from image where id_produit=?";
           pst = connection.prepareStatement(req);
           pst.setInt(1,id);
           rs = pst.executeQuery();
           while (rs.next()) {
               //EvaluationProd cmp=new EvaluationProd(rs.getInt(2));
           // s.add(cmp);
               x= rs.getString(1);
            
           }       } catch (SQLException ex) {
                   Logger.getLogger(CommentaireProduitDao.class.getName()).log(Level.SEVERE, null, ex);
               }
       return x;
      }
      
      
}