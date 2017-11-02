package activities;

import com.dropbox.core.*;

import dao.MembreDao;
import entities.Membre;
import fragments.AjouterAppelOffre;
import fragments.AppelsOffre;
import fragments.AppelsOffreMembre;
import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


import java.time.LocalDate;
import java.time.ZoneId;

import static java.time.temporal.TemporalQueries.localDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javax.swing.JOptionPane;
import javax.swing.plaf.synth.Region;
import org.kairos.ActionBarDrawerToggle;
import org.kairos.Toolbar;
import org.kairos.core.Activity;
import org.kairos.layouts.DrawerLayout;
import org.kairos.layouts.RecyclerView;
import org.kairos.layouts.SlidingTabLayout;
import org.kairos.layouts.ViewPager;

public class Inscription extends Activity {

    @FXML
    TextField nom;
    @FXML
    Button valider;
    @FXML
    Button image;
    @FXML
    TextField prenom;
    @FXML
    DatePicker date;
    @FXML
    TextField adresse;
    @FXML
    CheckBox male;
    @FXML
    CheckBox femelle;

    @FXML
    TextField login;
    @FXML
    TextField password;
    @FXML
    TextField mail;
    @FXML
    Label info;
     @FXML
    TextField num;

    File image_path=null;

    @Override
    public void onCreate() {
        super.onCreate();
        setContentView(getClass().getResource("../fxml/Inscription.fxml"));
    }

    public void images(ActionEvent event) {
        FileChooser f = new FileChooser();
        File s = f.showOpenDialog(null);
        if (!s.getPath().equals(null)) {
            image_path = s;
            info.setText(s.getPath());
        }

    }

    public void Valider() throws IOException, DbxException {
        MembreDao x = new MembreDao();
        String Nom = nom.getText();
        String Prenom = prenom.getText();
        LocalDate valeur = date.getValue();
        String Adresse = adresse.getText();
        String Login = login.getText();
        String Password = password.getText();
        String Mail = mail.getText();
        int Num= Integer.parseInt(num.getText());
        

        

        int reply;

        if (male.isSelected() && femelle.isSelected()) {
            JOptionPane.showMessageDialog(null, "Veuillez choisir un seul sexe soit homme ou femme");
        } else if (Nom.equals("") || Prenom.equals("") || Adresse.equals("") || Login.equals("") || Password.equals("") || Mail.equals("")) {
            JOptionPane.showMessageDialog(null, "un ou plusieurs champs obligatoires sont vide");
        } else if (valeur == null) {
            JOptionPane.showMessageDialog(null, "Veuillez choisir une date de naissance");
        }else if(image_path==null)
        {
            JOptionPane.showMessageDialog(null, "Veuillez choisir une image de profil");
        }  
            else {

            Date date = java.sql.Date.valueOf(valeur);
            

            if (male.isSelected()) {
                System.out.println(Nom + Prenom + Adresse + Login + Password);
                System.out.println("omaromar"+image_path.getName());
                Membre m = new Membre(Nom, Prenom, date, "Homme", "Normal", Adresse, Login, Password, Mail,Num,image_path.getName());
                reply = JOptionPane.showConfirmDialog(null, "Etes vous sûr que vos données sont corrects", null, JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    
                    x.add(m);
                    DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString());

                    String accessToken = "tKqn9aG0UZAAAAAAAAAACMXrAofgnDqtNNSoX5gVYYeJCioYsaVLRnFcsdhQmWsC";

                    DbxClient client = new DbxClient(config, accessToken);

                    int id_membre = x.getId(Nom, Adresse);
                    System.out.println(id_membre);
                    File inputFile = image_path;
                    FileInputStream inputStream = new FileInputStream(inputFile);
                  
                    try {
                        DbxEntry.File uploadedFile = client.uploadFile("/" + id_membre+"/"+image_path.getName(),
                                DbxWriteMode.add(), inputFile.length(), inputStream);
                        System.out.println("Uploaded: " + uploadedFile.toString());
                    } finally {
                        inputStream.close();
                    }
                    startActivity(Authentification.class);
                }
            }
            if (femelle.isSelected()) {
                Membre m = new Membre(Nom, Prenom, date, "femme", "Normal", Adresse, Login, Password, Mail,Num,image_path.getName());
                reply = JOptionPane.showConfirmDialog(null, "voulez-vous vraiment supprimer cette session ?", null, JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    x.add(m);

                    DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString());

                    String accessToken = "tKqn9aG0UZAAAAAAAAAACMXrAofgnDqtNNSoX5gVYYeJCioYsaVLRnFcsdhQmWsC";

                    DbxClient client = new DbxClient(config, accessToken);

                    int id_membre = x.getId(Nom, Adresse);
                    System.out.println(id_membre);
                    File inputFile = image_path;
                    FileInputStream inputStream = new FileInputStream(inputFile);
                  
                    try {
                        DbxEntry.File uploadedFile = client.uploadFile("/" + id_membre+"/profil.jpg",
                                DbxWriteMode.add(), inputFile.length(), inputStream);
                        System.out.println("Uploaded: " + uploadedFile.toString());
                    } finally {
                        inputStream.close();
                    }

                    startActivity(Authentification.class);
                }
            }

        }
    }

}
