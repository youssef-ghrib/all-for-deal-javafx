package dao;

import entities.Membre;
import entities.Service;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataSource;

public class MembreDao {
    
    private final Connection connection;
    private PreparedStatement pst;
    private ResultSet rs;
    
    public MembreDao() {
        connection = DataSource.getInstance().getConnection();
    }
    
    public Membre findById(int id) {
        
        String requete = "select * from membre where id=?";
        
        Membre m = new Membre();
        
        try {
            
            PreparedStatement ps = connection.prepareStatement(requete);
            ps.setInt(1, id);
            ResultSet resultat = ps.executeQuery();
            
            while (resultat.next()) {
                
                m.setId(resultat.getInt(1));
                m.setNom(resultat.getString(18));
                m.setPrenom(resultat.getString(19));
                m.setDateNaissance(resultat.getDate(20));
                m.setGenre(resultat.getString(22));
                m.setAdresse(resultat.getString(23));
                m.setType(resultat.getString(24));
                m.setPoints(resultat.getInt(25));
                m.setLogin(resultat.getString(2));
                m.setPassword(resultat.getString(8));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return m;
    }
    
    public void afficher() {
        String req = "SELECT * FROM `categorie` ";
        
        try {
            
            pst = connection.prepareStatement(req);
            
            rs = pst.executeQuery();
            
            if (rs.next()) {
                System.out.println(rs.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void add(Membre t) {
        
        String req = "insert into membre(nom,prenom,age,genre,adresse,type,points,username,username_canonical,password,email,email_canonical,enabled,roles,salt,locked,expired,credentials_expired,tel,imageName) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,0,0,0,?,?)";
        try {
            System.out.println(t.getDateNaissance());
            pst = connection.prepareStatement(req);
            pst.setString(1, t.getNom());
            pst.setString(2, t.getPrenom());
            pst.setDate(3, (Date) t.getDateNaissance());
            pst.setString(4, t.getGenre());
            pst.setString(5, t.getAdresse());
            pst.setString(6, t.getType());
            pst.setInt(7, 1000);
            pst.setString(8, t.getLogin());
            pst.setString(9, t.getLogin());
            pst.setString(10, t.getPassword());
            pst.setString(11, t.getMail());
            pst.setString(12, t.getMail());
            pst.setInt(13, 1);
            pst.setString(14, "a:0:{}");
            pst.setString(15, "");
            pst.setInt(16, t.getNum());
            pst.setString(17, t.getImageName());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteById(int t) {
        String req = "delete from membre where id=?";
        try {
            pst = connection.prepareStatement(req);
            pst.setInt(1, t);
            
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void update(Membre t, int id) {
        String req = "update membre set nom=?, prenom=?, age=?, adresse=?, email=?, username=?, tel=?, username_canonical=?, email_canonical=? where id=? ";
        try {
            pst = connection.prepareStatement(req);
            pst.setString(1, t.getNom());
            pst.setString(2, t.getPrenom());
            pst.setDate(3, (Date) t.getDateNaissance());
            
            pst.setString(4, t.getAdresse());
            
            pst.setString(6, t.getLogin());
            pst.setString(5, t.getMail());
            pst.setInt(7, t.getNum());
            pst.setString(8, t.getLogin());
            pst.setString(9, t.getMail());
            
            pst.setInt(10, id);
            
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Membre consulter(int id) {
        Membre m;
        String req = "select * from membre where id=?";
        try {
            pst = connection.prepareStatement(req);
            pst.setInt(1, id);
            
            rs = pst.executeQuery();
            
            if (rs.next()) {
                m = new Membre(rs.getString(18), rs.getString(19), rs.getDate(20), rs.getString(22), rs.getInt(25), rs.getString(23), rs.getString(2), rs.getString(4), rs.getInt(21), rs.getString(26));
                
                return m;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public List<Membre> list() {
        
        String req = "select * from membre";
        ArrayList<Membre> listp = new ArrayList<>();
        try {
            pst = connection.prepareStatement(req);
            rs = pst.executeQuery();
            while (rs.next()) {
                
                Membre p = new Membre(rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5), rs.getInt(8), rs.getString(6), rs.getString(9), rs.getString(11), rs.getInt(21), rs.getString(26));
                
                listp.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listp;
        
    }
    
    public List<Service> Recherche_service(int a) throws SQLException {
        
        String req = "select * from service where id_fournisseur = ?";
        pst = connection.prepareStatement(req);
        
        pst.setInt(1, 5);
        
        List<Service> listp = new ArrayList<>();
        
        try {
            
            rs = pst.executeQuery();
            
            while (rs.next()) {
                
                Service s = new Service(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getString(5), findById(rs.getInt(2)));
                listp.add(s);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listp;
        
    }
    
    public List<Service> Recherche_serviceByNom(int a, String nom) throws SQLException {
        
        String req = "select * from service where id_fournisseur = ? and nom like ?";
        pst = connection.prepareStatement(req);
        
        pst.setInt(1, 5);
        pst.setString(2, nom + "%");
        
        List<Service> listp = new ArrayList<>();
        
        try {
            
            rs = pst.executeQuery();
            
            while (rs.next()) {
                
                Service s = new Service(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), findById(rs.getInt(2)));
                listp.add(s);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listp;
        
    }

    /* public boolean authentifier(String login,String mdp)
     { 
     String req="select password from membre where login=?";
     try {
     pst=connection.prepareStatement(req);
     pst.setString(1, login);
           
     rs=pst.executeQuery();
     if(mdp.equals(rs.getString(0)))
     return true;
     } catch (SQLException ex) {
     Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
     }
     return false;
     }*/
    public boolean verifier_pwd(Membre t, String mdp) {
        int id = 63;
        String x = "";
        String req = "select password from membre where id=?";
        try {
            pst = connection.prepareStatement(req);
            pst.setInt(1, id);
            
            rs = pst.executeQuery();
            if (rs.next()) {
                x = rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (mdp.equals(x)) {
            return true;
        } else {
            return false;
        }
    }
    
    public void modifier_pwd(Membre t, String pwd) {
        int id = 63;
        String req = "update membre set password=? where id=? ";
        try {
            pst = connection.prepareStatement(req);
            pst.setString(1, pwd);
            pst.setInt(2, id);
            
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void modifier_imageName(int id, String img) {
        
        String req = "update membre set imageName=? where id=? ";
        try {
            pst = connection.prepareStatement(req);
            pst.setString(1, img);
            pst.setInt(2, id);
            
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Membre findMembreById(int id) throws SQLException {
        String req = "select * from membre where id=? ";
        
        Membre m = new Membre();
        
        PreparedStatement pst = connection.prepareStatement(req);
        pst.setInt(1, id);
        ResultSet rst = pst.executeQuery();
        
        while (rst.next()) {
            int id_m = rst.getInt(1);
            String nom_m = rst.getString(18);
            String prenom_m = rst.getString(19);
            String email = rst.getString(4);
            String type = rst.getString(23);
            
            m.setId(id_m);
            m.setNom(nom_m);
            m.setPrenom(prenom_m);
            m.setMail(email);
            m.setType(type);
            //System.out.println(P.toString());

        }
        return m;
    }
    
    public Membre getUser1(Service s) throws SQLException {
        String req = "select id_fournisseur from service where id=?";
        pst = connection.prepareStatement(req);
        int id_fournisseur = 0;
        System.out.println("omar");
        pst.setInt(1, s.getId());
        System.out.println("omar");
        try {
            
            rs = pst.executeQuery();
            
            while (rs.next()) {
                
                id_fournisseur = rs.getInt(1);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        Membre p = new Membre();
        String req1 = "select * from membre where id=?";
        pst = connection.prepareStatement(req1);
        
        pst.setInt(1, id_fournisseur);
        
        try {
            
            rs = pst.executeQuery();
            
            while (rs.next()) {
                
                id_fournisseur = rs.getInt(1);
                p = new Membre(rs.getString(18), rs.getString(19), rs.getDate(20), rs.getString(22), rs.getInt(25), rs.getString(23), rs.getString(2), rs.getString(4), rs.getInt(25), rs.getString(26));
                //       p = new Membre(rs.getString(18), rs.getString(19), rs.getDate(20), rs.getString(22), rs.getInt(24), rs.getString(23) , rs.getString(2), rs.getString(8),rs.getString(4),rs.getInt(21),rs.getString(26));

            }
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    
    public int getId(String nom, String adresse) {
        int id = 0;
        String req = "select * from membre where nom=? and adresse=?";
        
        try {
            pst = connection.prepareStatement(req);
            pst.setString(1, nom);
            pst.setString(2, adresse);
            rs = pst.executeQuery();
            while (rs.next()) {
                
                id = rs.getInt(1);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
        
    }
    
    public List<Membre> DisplayAllMembre() throws SQLException {
        List<Membre> L = new ArrayList<>();
        try {
            
            String req = "select * from membre ";
            PreparedStatement ps = connection.prepareStatement(req);
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {
                int id = rst.getInt("id");
                String nom = rst.getString("nom");
                String prenom = rst.getString("prenom");
                String login = rst.getString("login");
                String password = rst.getString("password");
                int num = rst.getInt("num");
                Membre m = new Membre(id, nom, prenom, login, password, num);
                //System.out.println(m.getMot_de_passe());
                //System.out.println(m.getLogin());
                //System.out.println(pro.toString());
                L.add(m);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return L;
    }
    
    public List<Membre> DisplayAllMembre1() throws SQLException {
        List<Membre> L = new ArrayList<>();
        try {
            
            String req = "select * from membre ";
            PreparedStatement ps = connection.prepareStatement(req);
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {
                int id = rst.getInt(1);
                String nom = rst.getString(18);
                String prenom = rst.getString(19);
                int points = rst.getInt(25);
                String pass = rst.getString(8);
                String genre = rst.getString(22);
                String mail = rst.getString(4);
                String type = rst.getString(24);
                
                Membre m = new Membre();
                m.setId(id);
                m.setNom(nom);
                m.setPrenom(prenom);
                m.setPoints(points);
                m.setPassword(pass);
                m.setGenre(genre);
                m.setMail(mail);
                m.setType(type);

                //System.out.println(pro.toString());
                L.add(m);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return L;
    }
    
    public void deleteMembre(int m) {
        
        String req = "delete from membre where id=?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setLong(1, m);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public List<Membre> findMembreByName(String nom) throws SQLException {
        String req = "select * from membre where nom like ? ";
        List<Membre> L = new ArrayList<>();
        
        PreparedStatement pst = connection.prepareStatement(req);
        pst.setString(1, nom + "%");
        ResultSet rst = pst.executeQuery();
        
        while (rst.next()) {
            int id = rst.getInt(1);
            String nom1 = rst.getString(18);
            String prenom = rst.getString(19);
            int points = rst.getInt(24);
            String genre = rst.getString(21);
            String mail = rst.getString(4);
            String type = rst.getString(23);
            
            Membre m = new Membre();
            m.setId(id);
            m.setNom(nom1);
            m.setPrenom(prenom);
            m.setPoints(points);
            m.setGenre(genre);
            m.setMail(mail);
            m.setType(type);

            //System.out.println(pro.toString());
            L.add(m);
        }
        return L;
        
    }
    
    public Membre getUser1(String login) {
        String req = "SELECT * FROM `membre` WHERE `login`=? ";
        Membre m = null;
        try {
            
            pst = connection.prepareStatement(req);
            
            pst.setString(1, login);
            
            rs = pst.executeQuery();
            
            if (rs.next()) {
                m = new Membre();
                m.setId(rs.getInt(1));
                m.setNom(rs.getString(2));
                m.setPrenom(rs.getString(3));
                m.setDateNaissance(rs.getDate(4));
                m.setGenre(rs.getString(5));
                m.setAdresse(rs.getString(6));
                m.setType(rs.getString(7));
                m.setPoints(rs.getInt(8));
                m.setLogin(rs.getString(9));
                m.setPassword(rs.getString(10));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AuthentificationDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return m;
    }
    
    public String findTypeUser(String login) {
        String L = "";
        try {
            String requete = "select roles from membre where username = ? ";
            
            pst = connection.prepareStatement(requete);
            pst.setString(1, login);
            
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                
                L = rst.getString("roles");
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return L;
        
    }
    
    public Membre getUser(String login, String password) {
        String req = "SELECT * FROM `membre` WHERE `username`=? and `password`=?";
        Membre m = null;
        try {
            
            pst = connection.prepareStatement(req);
            
            pst.setString(1, login);
            pst.setString(2, password);
            
            rs = pst.executeQuery();
            
            if (rs.next()) {
                m = new Membre(rs.getInt(1), rs.getString(18), rs.getString(19), rs.getDate(20), rs.getString(22), rs.getString(23), rs.getString(24), rs.getInt(25), rs.getString(2), rs.getString(8), rs.getInt(21), rs.getString(26));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AuthentificationDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return m;
    }

    /////////////////////////////////////////////////
    public void deleteAppelOffre(int m) {
        String req = "DELETE FROM `appel_offre_membre` WHERE `id_fournisseur`=?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setLong(1, m);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteAppelOffre1(int m) {
        String req = "Delete FROM `appel_offre` WHERE `id_consommateur`=?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setLong(1, m);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteCommande(int m) {
        String req = "Delete FROM `commande` WHERE `id_consommateur`=?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setLong(1, m);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteDemande(int m) {
        String req = "DELETE FROM `demande` WHERE `id_membre`=?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setLong(1, m);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteEvaluationProd(int m) {
        String req = "DELETE FROM `evaluation_prod` WHERE `id_membre`=?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setLong(1, m);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteEvaluationService(int m) {
        String req = "Delete FROM `evaluation_service` WHERE `id_membre`=?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setLong(1, m);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteProduit(int m) {
        String req = "DELETE FROM `produit` WHERE `id_fournisseur`=?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setLong(1, m);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteReclamation(int m) {
        String req = "DELETE FROM `reclamation` WHERE `id_membre`=?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setLong(1, m);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteService(int m) {
        String req = "DELETE FROM `service` WHERE `id_fournisseur`=?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setLong(1, m);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int selectIdPROD(int m) {
        int o = 0;
        try {
            String req = "select id from produit where id_fournisseur= ?";
            
            PreparedStatement ps = connection.prepareStatement(req);
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {
                o = rst.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return o;
    }
    
    public void deleteligne(int m) {
        String req = "delete from ligne_cmd where id_produit in (select id from produit where id_fournisseur= ?)";
        
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setLong(1, m);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteImage(int m) {
        String req = "delete from image where id_produit in (select id from produit where id_fournisseur=?)";
        
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setLong(1, m);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteAdresse(int m) {
        String req = "DELETE FROM `adresse` WHERE `id_membre` = ?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setLong(1, m);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MembreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Membre findMailById(int id) throws SQLException {
        String req = "select email from membre where id=? ";
        Membre m = new Membre();
        PreparedStatement pst = connection.prepareStatement(req);
        pst.setInt(1, id);
        ResultSet rst = pst.executeQuery();
        while (rst.next()) {
            String email = rst.getString(1);
            m.setMail(email);
        }
        return m;
    }
    
}
