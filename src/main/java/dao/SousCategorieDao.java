package dao;

import entities.Sous_categorie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataSource;

public class SousCategorieDao {

    MembreDao membreDao = new MembreDao();
    CategorieDao categorieDao = new CategorieDao();

    private final Connection connection;
    private PreparedStatement pst;
    private ResultSet rs;

    public SousCategorieDao() {
        connection = DataSource.getInstance().getConnection();
    }

    public int findSousCategoriebyNOm(String nom) {
        int L = 0;
        try {
            String requete = "select id from sous_categorie where nom = ?";

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

    public Sous_categorie findById(int id) {
        String requete = "select * from sous_categorie where id=?";

        Sous_categorie sc = new Sous_categorie();

        try {

            PreparedStatement ps = connection.prepareStatement(requete);
            ps.setInt(1, id);
            ResultSet resultat = ps.executeQuery();

            while (resultat.next()) {
                sc.setId(resultat.getInt(1));
                sc.setNom(resultat.getString(2));
//            sc.setId_categorie(categorieDao.findById(resultat.getInt(3)));
            }

        } catch (SQLException ex) {
            Logger.getLogger(SousCategorieDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sc;
    }

}
