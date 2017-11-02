package dao;

import dao.CategorieDao;
import fragments.AjoutProd;
import fragments.SupprimerProd;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JComboBox;
import utils.DataSource;

import entities.Produit;

import entities.Categorie;
import entities.Membre;
import entities.Sous_categorie;
import enums.SousCategorie;

/**
 *
 * @author daly
 */
public class ProduitDao {

    private Connection connection;
    private PreparedStatement pst;
    private ResultSet rs;
    MembreDao membreDao = new MembreDao();
    SousCategorieDao sousCategorieDao = new SousCategorieDao();

    public ProduitDao() {
        connection = DataSource.getInstance().getConnection();
    }

    public Produit findById(int id) {
        String requete = "select * from produit where id=?";
        Produit p = new Produit();

        try {

            PreparedStatement ps = connection.prepareStatement(requete);
            ps.setInt(1, id);
            ResultSet resultat = ps.executeQuery();

            while (resultat.next()) {

                p.setId(resultat.getInt(1));

                Sous_categorie sc = sousCategorieDao.findById(resultat.getInt(2));
                p.setSous_categorie(sc);

                Membre m = membreDao.findById(resultat.getInt(3));
                p.setFournisseur(m);

                p.setNom(resultat.getString(4));
                p.setPrix(resultat.getFloat(5));
                p.setReduction(resultat.getInt(6));
                p.setPoints(resultat.getInt(7));
                p.setEtat(resultat.getString(8));
                p.setDate_lancement(resultat.getString(9));
                p.setCouleur(resultat.getString(10));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    public void insertProduitd(Produit p, int b) {
        String req = "insert into produit (points,id_fournisseur,id_sous_categorie,nom,prix,reduction,couleur,etat,date_lancement) values (?,?,?,?,?,?,?,?,?)";
        try {
            pst = connection.prepareStatement(req);

            pst.setInt(1, 11);
            pst.setInt(2, 6);
            pst.setInt(3, b);
            pst.setString(4, p.getNom());
            pst.setFloat(5, (float) p.getPrix());
            pst.setInt(6, p.getReduction());
            pst.setString(7, p.getCouleur());

            pst.setString(8, "En cours");

            java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
            pst.setDate(9, sqlDate);

            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int LastRowADDed() {
        int L = 0;
        try {
            String requete = "SELECT id FROM produit ORDER BY id DESC LIMIT 1 ";

            pst = connection.prepareStatement(requete);

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

    public void updateProduit(Produit p) {

        String req = "UPDATE `produit` SET `nom`=?,`prix`=?,`reduction`=?,`couleur`=? WHERE `id`=?";
        try {

            pst = connection.prepareStatement(req);

            pst.setString(1, p.getNom());
            pst.setFloat(2, (float) p.getPrix());
            pst.setString(4, p.getCouleur());
            pst.setInt(3, p.getReduction());
            pst.setInt(5, p.getId());

            pst.executeUpdate();
            System.out.println("Mise à jour effectuée avec succès");

        } catch (SQLException ex) {
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List< Produit> findProduitByColorAndNom(String nom, String couleur) throws SQLException {
        String requete = "SELECT i.*, u.nom ,c.nom FROM produit i inner JOIN sous_categorie u ON (i.id_sous_categorie = u.id) inner JOIN categorie c ON (u.id_categorie=c.id) where i.nom like ? or i.couleur like ?";
        List<Produit> L = new ArrayList<>();

        pst = connection.prepareStatement(requete);
        pst.setString(1, nom + "%");
        pst.setString(2, couleur + "%");

        ResultSet rst = pst.executeQuery();

        while (rst.next()) {
            Sous_categorie sc = new Sous_categorie();
            Categorie c = new Categorie();
            sc.setNom(rst.getString(13));
            c.setNom(rst.getString(14));
            int id = rst.getInt(1);
            int points = rst.getInt(5);
            String nompdt = rst.getString(2);
            String date = rst.getString(7);
            String couleurpdt = rst.getString(8);
            int reduction = rst.getInt(4);
            float prixpdt = rst.getFloat(3);

            Produit p = new Produit(id, nom, prixpdt, reduction, points, date, date, couleur);
            L.add(p);
        }
        return L;

    }

    public List<Produit> displayLatest() {
        String req = "select * from produit order by date_lancement desc limit 8";
        List<Produit> produits = new ArrayList<>();
        try {
            pst = connection.prepareStatement(req);
            rs = pst.executeQuery();
            while (rs.next()) {
                Produit p = new Produit(rs.getInt(1), rs.getString(4), rs.getFloat(5), rs.getInt(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getString(10), membreDao.findById(rs.getInt(3)), sousCategorieDao.findById(rs.getInt(2)));
               
                produits.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AppelOffreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return produits;
    }

    public List<Produit> getBestSellers() {

        String req = "select p.* from produit p inner join ligne_cmd l on p.id=l.id_produit group by p.id order by sum(l.qte) desc limit 8";
        List<Produit> produits = new ArrayList<>();
        try {
            pst = connection.prepareStatement(req);
            rs = pst.executeQuery();
            while (rs.next()) {
                Produit p = new Produit(rs.getInt(1), rs.getString(4), rs.getFloat(5), rs.getInt(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getString(10), membreDao.findById(rs.getInt(3)), sousCategorieDao.findById(rs.getInt(2)));
                produits.add(p);

            }
        } catch (SQLException ex) {
            Logger.getLogger(AppelOffreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return produits;
    }

    public List<Produit> DisplayAllProduit() {

        List<Produit> produits = new ArrayList<>();
        try {
//            String req = "select * from produit";
            String req = "SELECT i.*, u.nom ,c.nom FROM produit i inner JOIN sous_categorie u ON (i.id_sous_categorie = u.id) inner JOIN categorie c ON (u.id_categorie=c.id)";
            pst = connection.prepareStatement(req);
            rs = pst.executeQuery();
            while (rs.next()) {
                Sous_categorie sc = new Sous_categorie();
                Categorie c = new Categorie();
                sc.setNom(rs.getString(11));
                c.setNom(rs.getString(12));
                Produit p = new Produit(rs.getInt(1), rs.getString(4), rs.getFloat(5), rs.getInt(6), rs.getInt(7), sc, rs.getString(9), rs.getString(10), c);
                produits.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return produits;
    }

    public void deleteProduit(int id) {
        String requete = "delete from produit where id=?";
        try {
            pst = connection.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Suppression effectuée avec succès");

        } catch (SQLException ex) {
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteCommentaire(int id) {
        String requete = "delete from commentaire_prod where id_produit=?";
        try {
            pst = connection.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Suppression comment avec succès");

        } catch (SQLException ex) {
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteImage(int id) {
        String requete = "delete from image where id_produit=?";
        try {
            pst = connection.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Suppression comment avec succès");

        } catch (SQLException ex) {
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteEvaluation(int id) {
        String requete = "delete from evaluation_prod where id_produit=?";
        try {
            pst = connection.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Suppression rating avec succès");

        } catch (SQLException ex) {
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int verifierComment(int id, int id1) {
        int nb = 0;
        String requete = "select count(*)as nb from commentaire_prod where id_produit=? and id_membre=?";
        try {
            pst = connection.prepareStatement(requete);
            pst.setInt(1, id);
            pst.setInt(2, id1);
            pst.executeQuery();
            nb = rs.getInt("nb");
            System.out.println("nombre rating avec succès");

        } catch (SQLException ex) {
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nb;
    }

    public Produit consulter(int id) {
        Produit m;
        String req = "select nom,prix,reduction,couleur from produit where id like ?";
        try {
            pst = connection.prepareStatement(req);
            pst.setInt(1, id);

            rs = pst.executeQuery();

            if (rs.next()) {

                m = new Produit(rs.getString("nom"), rs.getInt("prix"), rs.getInt("reduction"), rs.getString("couleur"));
                return m;

            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitDao.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public List<String> findcateg() {
        List<String> Lsc = new ArrayList<>();
        try {

            String req = "select nom from categorie";
            pst = connection.prepareStatement(req);
            rs = pst.executeQuery();
            while (rs.next()) {
                String nom = rs.getString(1);

                Lsc.add(nom);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Lsc;
    }

    public List<String> souscat(int a) {

        try {
            List<String> Lsc = new ArrayList<>();
            AjoutProd p = new AjoutProd();

            String req = "select nom from sous_categorie where id_categorie =? ";
            pst = connection.prepareStatement(req);
            pst.setInt(1, a);
            rs = pst.executeQuery();
            while (rs.next()) {
                String nom = rs.getString(1);

                Lsc.add(nom);
            }

            return Lsc;
        } catch (SQLException ex) {
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<String> findsouscateg() {
        List<String> Lsc = new ArrayList<>();
        try {

            String req = "select nom from sous_categorie";
            pst = connection.prepareStatement(req);
            rs = pst.executeQuery();
            while (rs.next()) {
                String nom = rs.getString(1);

                Lsc.add(nom);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Lsc;
    }

    public int returnidcateg(String nomcateg) {
        try {
            String req = "select id from categorie where nom like nomcateg";
            pst = connection.prepareStatement(req);
            rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);

                return id;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);

        }
        return 0;
    }

    public void insertProduit(Produit p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void updateProduit(Produit p, int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
