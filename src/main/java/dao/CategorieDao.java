/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Categorie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataSource;

/**
 *
 * @author joseph
 */
public class CategorieDao {

    private final MembreDao membreDao;
    private final Connection connection;
    private PreparedStatement pst;
    private ResultSet rs;

    public CategorieDao() {
        connection = DataSource.getInstance().getConnection();
        membreDao = new MembreDao();
    }

    public int findCategoriebyNOm(String nom) {
        int L = 0;
        try {
            String requete = "select id from categorie where nom = ?";

            pst = connection.prepareStatement(requete);
            pst.setString(1, nom);

            ResultSet rst = pst.executeQuery();

            while (rst.next()) {

                L = rst.getInt(1);
                System.out.println(L);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CategorieDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return L;

    }

    public Categorie findById(int id) {
        String requete = "select * from categorie where id=?";
        Categorie c = new Categorie();

        try {

            PreparedStatement ps = connection.prepareStatement(requete);
            ResultSet resultat = ps.executeQuery();

            while (resultat.next()) {

                c.setId(resultat.getInt(1));
                c.setNom(resultat.getString(2));
            }

        } catch (SQLException ex) {
            Logger.getLogger(CategorieDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

}
