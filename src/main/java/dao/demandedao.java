/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.AppelOffre;
import entities.Membre;
import entities.Service;
import entities.demande;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataSource;

public class demandedao {
private Connection connection;
    public demandedao() {
        connection = DataSource.getInstance().getConnection();
    }
    
    public void add(demande t) {
        String req ="INSERT INTO `demande`(`id_membre`, `id_service`, `date_debut`, `date_fin`, `lieu`, `message`) VALUES (?,?,?,?,?,?)";
        try {
          PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, t.getId_membre().getId());
            pst.setInt(2, t.getId_service().getId());
            pst.setString(3, t.getDatdebu());
            pst.setString(4, t.getDatefin());
            pst.setString(5, t.getLieu());
            pst.setString(6, t.getMessage());
           
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AppelOffreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertdemande(demande e) {
            
       try
       {
       String req = "insert into demande  values (?,?,?,?,?,?)";
            connection = DataSource.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement(req);
           
            ps.setInt(1,e.getId_service().getId());
             ps.setInt(2,e.getId_membre().getId());
              System.out.println("dao-id-service:"+e.getId_service().getId());
             System.out.println("dao-id-membre:"+e.getId_membre().getId());
          
            ps.setString(3,e.getDatdebu());
            ps.setString(4,e.getDatefin());
             ps.setString(5,e.getLieu());
            ps.setString(6,e.getMessage());
            System.out.println(e.toString());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(demandedao.class.getName()).log(Level.SEVERE, null, ex);
        }        
//To change body of generated methods, choose Tools | Templates.
    }

    public void deletedemande(int id) {
               String req="delete from demande where id_membre=? ";
      
      connection = DataSource.getInstance().getConnection();
            
     
        
     try {
         PreparedStatement  ps = connection.prepareStatement(req);
          ps.setLong(1,id);
//             ps.setLong(2,id_service2);
         ps.executeUpdate();
     } catch (SQLException ex) {
         Logger.getLogger(demandedao.class.getName()).log(Level.SEVERE, null, ex);
     }
        
    }

    public List<demande> findreclamationBylieu(String lieu) throws SQLException {
                   String req = "select c.*,b.nom,a.nom from demande c  inner join membre b on c.id_membre=b.id   INNER JOIN service a ON  a.id = c.id_service where c.lieu like ? ; ";
             List<demande> L = new ArrayList<>();

        PreparedStatement pst = connection.prepareStatement(req);
       
        ResultSet rst = pst.executeQuery();
            while(rst.next()){
                    System.out.println(rst.getString("b.nom"));
//              int id_membre = rst.getInt("c.id_membre");
//                 int id_service = rst.getInt("c.id_service");
           
            String df = rst.getString("c.date_debut");
            String lieu1 = rst.getString("c.date_fin");
             String dd = rst.getString("c.lieu");
            String msg = rst.getString("c.message");
              Membre four = new Membre();
                Service sev = new Service();
//              
//              four.setId(rst.getInt("c.id"));
                four.setId(rst.getInt("b.id"));
              four.setNom(rst.getString("b.nom"));
               sev.setId(rst.getInt("a.id"));
              sev.setNom(rst.getString("a.nom"));
                System.out.println(rst.getString("b.nom"));
//                System.out.println("66:"+rst.getString(6));
//                 System.out.println("77:"+rst.getString(7));
              
//                System.out.println(id_service);
//          Membre b = new Membre();
//            b.setId(id);
//            b.setNom(nom_bloc);
//            b.setType(type_bloc);
//           
              
//              
            demande ser=new demande(four,sev, df, lieu1,dd,msg);
           L.add(ser);
            }
            
        
    return L;
    }
  
    public List<demande> DisplayAll_demande() throws SQLException {
      
                          String req = "select c.*,b.nom,a.nom from demande c inner join membre b on c.id_membre=b.id   INNER JOIN service a ON  a.id = c.id_service ;  ";
        List<demande> L = new ArrayList<>();

        PreparedStatement pst = connection.prepareStatement(req);
       
        ResultSet rst = pst.executeQuery();
            while(rst.next()){
                    System.out.println(rst.getString("b.nom"));
//              int id_membre = rst.getInt("c.id_membre");
//                 int id_service = rst.getInt("c.id_service");
           
            String df = rst.getString("c.date_debut");
            String lieu1 = rst.getString("c.date_fin");
             String dd = rst.getString("c.lieu");
            String msg = rst.getString("c.message");
              Membre four = new Membre();
                Service sev = new Service();
//              
//              four.setId(rst.getInt("c.id"));
                 four.setId(rst.getInt("b.id"));
              four.setNom(rst.getString("b.nom")); 
              sev.setNom(rst.getString("a.nom"));
              sev.setId(rst.getInt("a.id"));
                System.out.println(rst.getString("b.nom"));
//                System.out.println("66:"+rst.getString(6));
//                 System.out.println("77:"+rst.getString(7));
              
//                System.out.println(id_service);
//          Membre b = new Membre();
//            b.setId(id);
//            b.setNom(nom_bloc);
//            b.setType(type_bloc);
//           
              
//              
            demande ser=new demande(four,sev, df, lieu1,dd,msg);
           L.add(ser);
            }
            
        
    return L;
    }

    
    
    
    
    
    
    
    
    
}