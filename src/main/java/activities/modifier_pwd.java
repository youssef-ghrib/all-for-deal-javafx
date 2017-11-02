/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activities;

import dao.MembreDao;
import entities.Membre;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.kairos.core.Activity;

/**
 *
 * @author omar
 */
public class modifier_pwd extends Activity {
    @FXML
    TextField pwd;
    
    @FXML
    TextField pwd1;
    
    @FXML
    TextField pwd2;
    @FXML
    Label msg;
    
    
    @Override
    public void onCreate() {
      
        super.onCreate();
        setContentView(getClass().getResource("../fxml/modifier_pwd.fxml"));
         
    }
    
    public void valider()
    {  MembreDao m=new MembreDao();
    Membre m1=m.consulter(63);
        String a=pwd.getText();

          int reply;
      

        if(pwd2.getText().equals("") || pwd.getText().equals("") || pwd1.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Veuillez remplir tout les champs");
        }
        else {
       if( m.verifier_pwd(m1, a))
                { 
                    if(pwd1.getText().equals(pwd2.getText()))
                    {  reply = JOptionPane.showConfirmDialog(null, "Etes vous sûr de vouloir changer votre mot de passe ?", null,JOptionPane.YES_NO_OPTION); 
if (reply == JOptionPane.YES_OPTION) {
                        m.modifier_pwd(m1, pwd1.getText());
                               Stage s = (Stage) pwd.getScene().getWindow();
        s.close();
                    }}
                    else 
                   msg.setText("veuillez entrer le méme mot de passe pour le changer");
                }
       else msg.setText("votre mot de passe est incorrect");
          
       
        
    }
            
        }
}
