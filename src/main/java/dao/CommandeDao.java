/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Commande;
import entities.LigneCmd;
import entities.Produit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataSource;

/**
 *
 * @author joseph
 */
public class CommandeDao {

    private final Connection connection;
    private PreparedStatement pst;
    private ResultSet rs;

    public CommandeDao() {
        connection = DataSource.getInstance().getConnection();
    }

    public void add1(int id, int idC, String date, float total) {

        LigneCmd l = new LigneCmd();
        String req1 = "INSERT INTO `commande`(`id`,`id_consommateur`, `date`, `total`) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(req1);
            ps.setInt(1, id);
            ps.setInt(2, idC);
            ps.setString(3, date);
            ps.setFloat(4, total);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LigneCommandeDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    
//    public LigneCmd consulter(int idc, int idp) {
//        LigneCmd m = null;
//        String req = "SELECT `id_commande`, `id_produit`, `qte`, `total` FROM `ligne_cmd` where id_commande=? and id_panier=?";
//        try {
//            pst = connection.prepareStatement(req);
//            pst.setInt(1, idc);
//            pst.setInt(2, idp);
//
//            rs = pst.executeQuery();
//
//            if (rs.next()) {
//                Commande c = new Commande();
//                c.setId(idc);
//                Produit p = new Produit();
//                p.setId(idp);
//
////            m=new LigneCmd(c,p,rs.getInt(3),rs.getFloat(4));
//                return m;
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(LigneCommandeDao.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return null;
//    }
}
