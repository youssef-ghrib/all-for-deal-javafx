package dao;

import entities.AppelOffre;
import entities.Membre;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataSource;

public class AppelOffreDao {

    private final MembreDao membreDao;
    private final Connection connection;
    private PreparedStatement pst;
    private ResultSet rs;

    public AppelOffreDao() {
        connection = DataSource.getInstance().getConnection();
        membreDao = new MembreDao();
    }

    public void add(AppelOffre t) {
        String req = "INSERT INTO `appel_offre`(`sujet`, `description`, `dateDebut`, `dateFin`, `lieu`, `remarque`, `id_consommateur`) VALUES (?,?,?,?,?,?,?)";
        try {
            pst = connection.prepareStatement(req);
            pst.setString(1, t.getSujet());
            pst.setString(2, t.getDescription());
            pst.setString(3, t.getDateDebut());
            pst.setString(4, t.getDateFin());
            pst.setString(5, t.getLieu());
            pst.setString(6, t.getRemarques());
            pst.setInt(7, t.getConsommateur().getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AppelOffreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public AppelOffre findById(int id) {
        String requete = "select * from appel_offre where id=?";
        AppelOffre a = new AppelOffre();

        try {

            PreparedStatement ps = connection.prepareStatement(requete);
            ResultSet resultat = ps.executeQuery();

            while (resultat.next()) {

                a.setId(resultat.getInt(1));
                a.setSujet(resultat.getString(2));
                a.setDescription(resultat.getString(3));
                a.setDateDebut(resultat.getString(4));
                a.setDateFin(resultat.getString(5));
                a.setLieu(resultat.getString(6));
                a.setRemarques(resultat.getString(7));
                a.setConsommateur(membreDao.findById(resultat.getInt(8)));
            }

        } catch (SQLException ex) {
            Logger.getLogger(AppelOffreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a;
    }

    public void update(AppelOffre t) {
        String requete = "UPDATE `appel_offre` SET `sujet`=?,`description`=?,`dateDebut`=?,`dateFin`=?,`lieu`=?,`remarque`=? WHERE `id`=?";
        try {
            pst = connection.prepareStatement(requete);
            pst.setString(1, t.getSujet());
            pst.setString(2, t.getDescription());
            pst.setString(3, t.getDateDebut());
            pst.setString(4, t.getDateFin());
            pst.setString(5, t.getLieu());
            pst.setString(6, t.getRemarques());
            pst.setInt(7, t.getId());
            pst.executeUpdate();
            System.out.println("Mise à jour effectuée avec succès");

        } catch (SQLException ex) {
            Logger.getLogger(AppelOffreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<AppelOffre> displayOther(Membre m) {
        String req = "select * from appel_offre where id_consommateur!=?";
        List<AppelOffre> appelsOffre = new ArrayList<>();
        try {
            pst = connection.prepareStatement(req);
            pst.setInt(1, m.getId());
            rs = pst.executeQuery();
            while (rs.next()) {
                AppelOffre p = new AppelOffre(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), membreDao.findById(rs.getInt(8)));
                appelsOffre.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AppelOffreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return appelsOffre;
    }

    public List<AppelOffre> displayByMember(Membre m) {
        String req = "select * from appel_offre where id_consommateur=?";
        List<AppelOffre> appelsOffre = new ArrayList<>();
        try {
            pst = connection.prepareStatement(req);
            pst.setInt(1, m.getId());
            rs = pst.executeQuery();
            while (rs.next()) {
                AppelOffre p = new AppelOffre(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), membreDao.findById(rs.getInt(2)));
                appelsOffre.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AppelOffreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return appelsOffre;
    }

    public void delete(AppelOffre a) {
        String requete = "delete from appel_offre where id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(requete);
            ps.setInt(1, a.getId());
            ps.executeUpdate();
            System.out.println("Suppression effectuée avec succès");

        } catch (SQLException ex) {
            Logger.getLogger(AppelOffreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<AppelOffre> findByFilter(String filtre) {
        String req = "select * from appel_offre where sujet like ? or description like ? or dateDebut like ? or dateFin like ? or lieu like ? or remarque like ?";
        List<AppelOffre> appelsOffre = new ArrayList<>();
        try {
            pst = connection.prepareStatement(req);

            pst.setString(1, "%" + filtre + "%");
            pst.setString(2, "%" + filtre + "%");
            pst.setString(3, "%" + filtre + "%");
            pst.setString(4, "%" + filtre + "%");
            pst.setString(5, "%" + filtre + "%");
            pst.setString(6, "%" + filtre + "%");

            rs = pst.executeQuery();
            while (rs.next()) {
                AppelOffre p = new AppelOffre(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), membreDao.findById(rs.getInt(8)));
                appelsOffre.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AppelOffreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return appelsOffre;
    }

    public void repondre(Membre m, AppelOffre a) {

        String req = "INSERT INTO `appel_offre_membre` VALUES (?,?)";
        try {
            pst = connection.prepareStatement(req);
            pst.setInt(1, m.getId());
            pst.setInt(2, a.getId());

            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AppelOffreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getResponseCount(Membre m) {
        String req = "select count(a.id) from appel_offre_membre r inner join appel_offre a on r.id_appel_offre=a.id where a.id_consommateur=?";
        int count = 0;
        try {
            pst = connection.prepareStatement(req);
            pst.setInt(1, m.getId());

            rs = pst.executeQuery();

            while (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AppelOffreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    public List<AppelOffre> displayAll() {
        String req = "select * from appel_offre";
        List<AppelOffre> appelsOffre = new ArrayList<>();
        try {
            pst = connection.prepareStatement(req);
            rs = pst.executeQuery();
            while (rs.next()) {
                AppelOffre p = new AppelOffre(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), membreDao.findById(rs.getInt(8)));
                appelsOffre.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AppelOffreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return appelsOffre;
    }

}
