package DAO;

import entities.Reclamation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataSource;

public class ReclamationDao {

    private Connection connection ;
    
    public ReclamationDao()
    {
        connection = DataSource.getInstance().getConnection();
    }
    
    public void insertReclamation(Reclamation R) {
        
        try {
        String req="insert into reclamation (`id_service`, `id_membre`, `sujet`, `message`, `date`) VALUES (?,?,?,?,?)";
 
          PreparedStatement ps = connection.prepareStatement(req);
          ps.setInt(1,R.getMembre().getId());
          ps.setInt(2,R.getService().getId());
          ps.setString(3,R.getSujet());
          ps.setString(4, R.getMessage());
          ps.setString(5, R.getDate());

            ps.executeUpdate();
     } catch (SQLException ex) {
         Logger.getLogger(ReclamationDao.class.getName()).log(Level.SEVERE, null, ex);
     }
  
    }
    
    public void insertReponse(Reclamation r,String reponse) {
        
        String req="UPDATE reclamation SET reponse=? WHERE id_service= ? and id_membre = ?";
 
          try {
         PreparedStatement  ps = connection.prepareStatement(req);
         ps.setString(1,reponse);
         ps.setInt(2,r.getService().getId());
         ps.setInt(3, r.getMembre().getId());
         ps.executeUpdate();
     }   catch (SQLException ex) {
         Logger.getLogger(ReclamationDao.class.getName()).log(Level.SEVERE, null, ex);
     }  
    }
    


}
