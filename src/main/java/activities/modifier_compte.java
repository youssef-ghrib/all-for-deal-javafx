/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activities;

import dao.MembreDao;
import entities.Membre;
import fragments.Compte;
import static java.lang.Integer.toString;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.kairos.core.Activity;
import org.kairos.core.ActivityFactory;

/**
 *
 * @author omar
 */
public class modifier_compte extends Activity {

    @FXML
    TextField nom;
    @FXML
    TextField prenom;
    @FXML
    DatePicker age;
    @FXML
    TextField adresse;
    @FXML
    TextField login;
    @FXML
    TextField mail;
     @FXML
    TextField num;
  int id = 63;
    @Override
    public void onCreate() {
      
        MembreDao md = new MembreDao();
        Membre m = md.consulter(id);
        super.onCreate();
        setContentView(getClass().getResource("../fxml/Modifier_compte.fxml"));
        nom.setText(m.getNom());
        prenom.setText(m.getPrenom());
        
        adresse.setText(m.getGenre());
        login.setText(m.getLogin());
        mail.setText(m.getMail());
        num.setText(Integer.toString(m.getNum()));

    }

    public void valider(ActionEvent event) {
    
        MembreDao md = new MembreDao();
        System.out.println(login.getText());
       
        String Nom = nom.getText();
        String Prenom = prenom.getText();
        LocalDate valeur= age.getValue();
        String Adresse = adresse.getText();
        String Login = login.getText();
        String Mail = mail.getText();
        int Num=24282103;
        
            String Age ="";
                
           int reply;
           if(Nom.equals("") || Prenom.equals("") || Adresse.equals("") || Login.equals("") ||  Mail.equals(""))
    {
        JOptionPane.showMessageDialog(null, "un ou plusieurs champs obligatoires sont vide");
    }
           else if(valeur==null)
        {
            JOptionPane.showMessageDialog(null, "Veuillez choisir une date de naissance");
        }
    else { 
      
reply = JOptionPane.showConfirmDialog(null, "Etes vous sûr de vouloir modifier vos données ?", null,JOptionPane.YES_NO_OPTION); 
if (reply == JOptionPane.YES_OPTION) {
       
                  Date date = java.sql.Date.valueOf(valeur);
        Membre m = new Membre(Nom, Prenom, date, Adresse, Login, Mail,Num);
        md.update(m, id);
 Stage s = (Stage) nom.getScene().getWindow();
        s.close();


    }}
           
    }

}
