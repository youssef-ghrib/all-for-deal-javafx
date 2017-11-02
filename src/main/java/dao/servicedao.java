/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Membre;
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

/**
 *
 * @author Pato
 */
public class servicedao {

    private Connection connection;
    MembreDao mdao = new MembreDao();

    public servicedao() {
        connection = DataSource.getInstance().getConnection();

    }

    public Service findById(int id) {

        String requete = "select * from service where id=?";

        Service s = new Service();

        try {

            PreparedStatement ps = connection.prepareStatement(requete);
            ps.setInt(1, id);
            ResultSet resultat = ps.executeQuery();

            while (resultat.next()) {

                s.setId(resultat.getInt(1));
                s.setNom(resultat.getString(2));
                s.setCategorie(resultat.getString(3));
                s.setDescription(resultat.getString(4));
                s.setId_fournisseur(mdao.findById(resultat.getInt(5)));

            }

        } catch (SQLException ex) {
            Logger.getLogger(servicedao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

    public void insertService(Service e, Membre m) {

        try {
            String req = "insert into service (`id_fournisseur`, `nom`, `categorie`, `description`) values (?,?,?,?)";
            connection = DataSource.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, m.getId());
            ps.setString(2, e.getNom());
            ps.setString(3, e.getCategorie());
            ps.setString(4, e.getDescription());

            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(servicedao.class.getName()).log(Level.SEVERE, null, ex);
        }
//To change body of generated methods, choose Tools | Templates.
    }

    public void updateService(Service e) throws SQLException {
        String req = "update service  set nom = ? , categorie = ? , description = ?  where id = ?";
        connection = DataSource.getInstance().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            System.out.println("----------------------:" + e.getId());
            ps.setString(1, e.getNom());
            ps.setString(2, e.getDescription());
            ps.setString(3, e.getCategorie());
            ps.setInt(4, e.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(servicedao.class.getName()).log(Level.SEVERE, null, ex);
        }
        //To change body of generated methods, choose Tools | Templates.
    }

    public void deleteService(int id) {
        String req = "delete from service where id=?";

        connection = DataSource.getInstance().getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(servicedao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteEvualuation(int id) {
        String req = "delete from evaluation_service where id_service=?";

        connection = DataSource.getInstance().getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(servicedao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteCommentaire(int id) {
        String req = "delete from commentaire_service where id_service=?";

        connection = DataSource.getInstance().getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(servicedao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteReclamation(int id) {
        String req = "delete from reclamation where id_service=?";

        connection = DataSource.getInstance().getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(servicedao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deletedemande(int id) {
        String req = "delete from demande where id_service=?";

        connection = DataSource.getInstance().getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(servicedao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//    

//    @Override
//    public service findServiceById(int id) throws SQLException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    public List<Service> findServiceByNom(String nom) throws SQLException {
        String req = "select c.*,b.* from service c inner join membre b on c.id_fournisseur=b.id where c.nom like ?  ; ";
        List<Service> L = new ArrayList<>();

        PreparedStatement pst = connection.prepareStatement(req);
        pst.setString(1, nom + "%");
        ResultSet rst = pst.executeQuery();
        while (rst.next()) {
//          System.out.println(rst.getString("b.nom"));
            int id_service = rst.getInt("c.id");

            String nom1 = rst.getString("c.nom");
            String description = rst.getString("c.description");
            String categorie = rst.getString("c.categorie");

            Membre four = new Membre();
//              
//              four.setId(rst.getInt("c.id"));
            four.setNom(rst.getString("b.nom"));
            four.setAdresse(rst.getString("b.adresse"));
            four.setPrenom(rst.getString("b.prenom"));
//              four.setDateNaissance(rst.getString("b.age"));
//              four.setId(rst.getInt("b.id"));
            System.out.println(rst.getString("b.adresse"));

//                System.out.println(id_service);
//          Membre b = new Membre();
//            b.setId(id);
//            b.setNom(nom_bloc);
//            b.setType(type_bloc);
//           
            Service ser = new Service(id_service, nom1, categorie, description, four);
//                System.out.println(four.toString());
            L.add(ser);
        }

        return L;
    }

    public List<Service> findServiceByidMembre(int id_mem) throws SQLException {
        List<Service> L = new ArrayList<>();
        try {

            String req = "select c.*,b.nom from service c inner join membre b on c.id_fournisseur=b.id where c.nom like ?  ;  ";

            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, id_mem);
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {

                int i = rst.getInt(1);
                String nom = rst.getString(2);
                String categorie = rst.getString(3);
                String description = rst.getString(4);
                int iii = rst.getInt(2);

                Membre bl = new Membre();
                bl.setId(iii);
                Service f = new Service(i, nom, categorie, description, bl);
                System.out.println(f.toString());
                L.add(f);
            }

        } catch (SQLException ex) {
            Logger.getLogger(servicedao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return L;
    }

    public List<Service> DisplayAllService() throws SQLException {
        List<Service> L = new ArrayList<>();
        try {

            String req = "select c.*,b.nom,b.id from service c inner join membre b on c.id_fournisseur=b.id  ; ";
            connection = DataSource.getInstance().getConnection();
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rst = pst.executeQuery();

            while (rst.next()) {
//                System.out.println(rst.getString("b.nom"));
                int id_service = rst.getInt("c.id");

                String nom = rst.getString("c.nom");
                String description = rst.getString("c.description");
                String categorie = rst.getString("c.categorie");

                Membre four = new Membre();
//              
                four.setId(rst.getInt("b.id"));
                four.setNom(rst.getString("b.nom"));
//                System.out.println(rst.getString("b.nom"));
//                System.out.println("66:"+rst.getString(6));
//                 System.out.println("77:"+rst.getString(7));

//                System.out.println(id_service);
//          Membre b = new Membre();
//            b.setId(id);
//            b.setNom(nom_bloc);
//            b.setType(type_bloc);
//           
                Service ser = new Service(id_service, nom, categorie, description, four);
//                System.out.println(four.toString());
                L.add(ser);
            }

        } catch (SQLException ex) {
            Logger.getLogger(servicedao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return L;
    }

    //To change body of generated methods, choose Tools | Templates.

    public List<Service> DisplayAllServices() throws SQLException {
        List<Service> L = new ArrayList<>();

        String req = "SELECT i.*, u.nom, u.prenom FROM service i LEFT JOIN membre u ON (i.id_fournisseur = u.id)";

        PreparedStatement pst = connection.prepareStatement(req);
        ResultSet rst = pst.executeQuery();
        while (rst.next()) {
            Membre membre = new Membre();
            int nom_mem = rst.getInt(2);
            membre.setId(nom_mem);
            membre.setNom(rst.getString(6));
            membre.setPrenom(rst.getString(7));
            int id = rst.getInt(1);
            String nom = rst.getString(3);
            String categorie = rst.getString(4);
            String description = rst.getString(5);
            Service s = new Service();
            s.setId(id);
            s.setId_fournisseur(membre);
            s.setNom(nom);
            s.setCategorie(categorie);
            s.setCategorie(categorie);
            s.setDescription(description);
            L.add(s);
        }
        return L;
    }

    public List<Service> DisplayAllServices1() throws SQLException {
        List<Service> L = new ArrayList<>();

        String req = "SELECT i.*, u.nom, u.prenom FROM service i LEFT JOIN membre u ON (i.id_fournisseur = u.id)";

        PreparedStatement pst = connection.prepareStatement(req);
        ResultSet rst = pst.executeQuery();
        while (rst.next()) {
            Membre membre = new Membre();
            int nom_mem = rst.getInt(2);
            membre.setId(nom_mem);
            membre.setNom(rst.getString(6));
            membre.setPrenom(rst.getString(7));
            int id = rst.getInt(1);
            String nom = rst.getString(3);
            String categorie = rst.getString(4);
            String description = rst.getString(5);
            Service s = new Service();
            s.setId(id);
            s.setId_fournisseur(membre);
            s.setNom(nom);
            s.setCategorie(categorie);
            s.setCategorie(categorie);
            s.setDescription(description);
            L.add(s);
        }
        return L;
    }

    public String verifEtatService(int id) throws SQLException {
        int re = 0;
        String etat;
        String req = "select count(*) as c from reclamation where id_service= ?";
        PreparedStatement pst = connection.prepareStatement(req);

        pst.setInt(1, id);

        ResultSet rst = pst.executeQuery();

        while (rst.next()) {
            re = rst.getInt("c");
        }

        System.out.println("REEEEE   " + re);
        if (re == 0) {
            etat = "Non Réclamé";
        } else {
            etat = "Réclamé";
        }
        return etat;
    }

    public void deleteServicead(int m) {

        String req = "delete from service where id=?";

        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, m);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(servicedao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Service findServiceById(int id) throws SQLException {
        String req = "select * from service where id=? ";

        Service s = new Service();

        PreparedStatement pst = connection.prepareStatement(req);
        pst.setInt(1, id);
        ResultSet rst = pst.executeQuery();

        while (rst.next()) {
            int id_m = rst.getInt(1);
            String nom_m = rst.getString(3);

            s.setId(id_m);
            s.setNom(nom_m);
            //System.out.println(P.toString());

        }
        return s;
    }
}
