/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import entities.Membre;
import entities.Reclamation;
import entities.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataSource;

import dao.MembreDao;
import dao.servicedao;

/**
 *
 * @author Wael
 */
public class ReponseDao  {

    private Connection connection;

    public ReponseDao() {
        connection = DataSource.getInstance().getConnection();
    }

    public List<Reclamation> DisplayAllReclamation() throws SQLException {
        List<Reclamation> L = new ArrayList<>();
        try {
            String req = "SELECT i.*, u.nom, u.prenom , cc.nom FROM reclamation i LEFT JOIN membre u ON (i.id_membre = u.id)LEFT JOIN service cc ON (i.id_service = cc.id)";

            PreparedStatement ps = connection.prepareStatement(req);
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {
                Membre m = new Membre();
                Service s = new Service();
                int id_membre = rst.getInt(1);
                int id_service = rst.getInt(2);
                String sujet = rst.getString(3);
                String message = rst.getString(4);
                String date = rst.getString(5);

                m.setId(id_membre);
                s.setId(id_service);

                m.setNom(rst.getString(7));
                m.setPrenom(rst.getString(8));
                s.setNom(rst.getString(9));

                Reclamation r = new Reclamation(m, s, sujet, message, date);

                L.add(r);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReponseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return L;
    }

    public void deleteReclamation(String s) {
        String req = "delete from reclamation where sujet=?";

        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, s);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ReponseDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<Reclamation> findReclamationBySujet(String sujet) throws SQLException {
        String req = "select * from reclamation where sujet like ? ";
        List<Reclamation> L = new ArrayList<>();

        PreparedStatement pst = connection.prepareStatement(req);
        pst.setString(1, sujet + "%");
        ResultSet rst = pst.executeQuery();

        while (rst.next()) {
            int id_s = rst.getInt(1);
            int id_m = rst.getInt(2);
            String sujet1 = rst.getString(3);
            String message = rst.getString(4);
            String date = rst.getString(5);

            MembreDao dao = new MembreDao();
            servicedao sdao = new servicedao();
            Membre me = dao.findMembreById(id_m);
            Service se = sdao.findServiceById(id_s);

            Reclamation r = new Reclamation(me, se, sujet1, message, date);
            //System.out.println(P.toString());
            L.add(r);
        }
        return L;

    }
}
