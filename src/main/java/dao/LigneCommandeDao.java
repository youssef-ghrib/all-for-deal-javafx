package dao;

import entities.Commande;
import entities.LigneCmd;
import entities.Produit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataSource;

public class LigneCommandeDao {

    private final Connection connection;
    private PreparedStatement pst;
    private ResultSet rs;

    public LigneCommandeDao() {
        connection = DataSource.getInstance().getConnection();
    }

    public void afficher() {
        String req = "SELECT * FROM `ligne_cmd` ";

        try {

            pst = connection.prepareStatement(req);

            rs = pst.executeQuery();

            if (rs.next()) {
                System.out.println(rs.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LigneCommandeDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void add1(int x1, int x2, int x3) {

        LigneCmd l = new LigneCmd();
        String req1 = "INSERT INTO `ligne_cmd`(`id_commande`, `id_produit`, `qte`) VALUES (?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(req1);
            ps.setInt(1, x1);
            ps.setInt(2, x2);
            ps.setInt(3, x3);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LigneCommandeDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void add(LigneCmd l) {

        String req1 = "INSERT INTO `ligne_cmd`(`id_commande`, `id_produit`, `qte`, `total`) VALUES (?,?,?,?)";
        try {
            pst = connection.prepareStatement(req1);
            pst.setInt(1, l.getCmd().getId());
            pst.setInt(2, l.getCmd().getId());
            pst.setInt(3, l.getQte());
//            pst.setFloat(4,l.getTotal());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LigneCommandeDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<LigneCmd> findAllcmd() {
        List<LigneCmd> listecmd = new ArrayList<>();
        String requete = "select * from ligne_cmd";
        try {
            Statement statement = connection
                    .createStatement();
            ResultSet resultat = statement.executeQuery(requete);

            while (resultat.next()) {
                Produit id = new Produit();
                id.setId(rs.getInt(1));

                Commande id_panier = new Commande();
                id_panier.setId(rs.getInt(2));

                LigneCmd c = new LigneCmd();
                c.setCmd(id_panier);
                c.setProduit(id);
                c.setQte(resultat.getInt(3));

                listecmd.add(c);
            }

        } catch (SQLException ex) {
            Logger.getLogger(LigneCommandeDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listecmd;
    }

    public LigneCmd consulter(int idc, int idp) {
        LigneCmd m = null;
        String req = "SELECT `id_commande`, `id_produit`, `qte`, `total` FROM `ligne_cmd` where id_commande=? and id_panier=?";
        try {
            pst = connection.prepareStatement(req);
            pst.setInt(1, idc);
            pst.setInt(2, idp);

            rs = pst.executeQuery();

            if (rs.next()) {
                Commande c = new Commande();
                c.setId(idc);
                Produit p = new Produit();
                p.setId(idp);

//            m=new LigneCmd(c,p,rs.getInt(3),rs.getFloat(4));
                return m;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LigneCommandeDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
