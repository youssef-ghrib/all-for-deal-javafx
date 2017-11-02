/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activities;

import dao.MembreDao;
import entities.Membre;
import entities.Service;
import static java.lang.ProcessBuilder.Redirect.to;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.ParseException;
import javax.swing.JOptionPane;
import org.kairos.core.Activity;

/**
 *
 * @author omar
 */
public class mail extends Activity {

    @FXML
    TextField sujet;
    @FXML
    TextArea message;
    @FXML
    TableView<Service> table;
    @FXML
    private TableColumn<Service, String> nom;
    @FXML
    private TableColumn<Service, String> categorie;
        @FXML
    private TableColumn<Service, String> description;
    @FXML
    TextField service;

    @Override
    public void onCreate() {
        super.onCreate();
        setContentView(getClass().getResource("../fxml/mail.fxml"));
        MembreDao m = new MembreDao();
        List<Service> l = new ArrayList<>();
        HashMap<String, Object> args = intent.getExtras();
        
        try {
            l = m.Recherche_service((int)args.get("y"));
        } catch (SQLException ex) {
            Logger.getLogger(mail.class.getName()).log(Level.SEVERE, null, ex);
        }
        final ObservableList<Service> data = FXCollections.observableArrayList();
        for (Service m1 : l) {

            data.add(m1);

        }
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        categorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
                description.setCellValueFactory(new PropertyValueFactory<>("description"));

        table.setItems(data);
    }

    public void recherche() {
        MembreDao m = new MembreDao();

        List<Service> ls = new ArrayList<>();

        HashMap<String, Object> args = intent.getExtras();
        try {
            String s = service.getText();

            ls = m.Recherche_serviceByNom((int)args.get("y"),s);

        } catch (SQLException ex) {
            Logger.getLogger(mail.class.getName()).log(Level.SEVERE, null, ex);
        }
        final ObservableList<Service> data = FXCollections.observableArrayList();
        for (Service m1 : ls) {

            data.add(m1);
       
        }
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        categorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));

        table.setItems(data);
    }

    public void envoyer() throws ParseException {
        MembreDao md=new MembreDao();
        String s = sujet.getText();
        String m = message.getText();
        Service a = table.getSelectionModel().getSelectedItem();
      if(s.equals("") || m.equals("") || a==null)
      {
                      JOptionPane.showMessageDialog(null, "Veuillez remplir tout les champs et choisir un service");
      }
      else {
        Membre proprio=new Membre();
        try {
            proprio=md.getUser1(a);
        } catch (SQLException ex) {
            Logger.getLogger(mail.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      String msg="Bonjour,\n vous avez une requéte de la part de "+proprio.getNom()+" "+proprio.getPrenom()+" et c'est à proppos du service que vous avez posté sur Allfordeal et qui est le "+a.getNom()+"\nEt voila le contenu de son message:\n"+m+"\n vous pouvez le/la contacter sur son adresse mail qui est: "+proprio.getMail()+"\nmerci pour votre fidélité";
        final String username = "allfordeal1@gmail.com";
        final String password = "devmasters";

        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("allfordeal1@gmail.com"));

            InternetAddress[] addressTo = new InternetAddress[1];

            addressTo[0] = new InternetAddress("omar.riahi@esprit.tn");

            message.setRecipients(Message.RecipientType.TO, addressTo);
            message.setSubject(s);
            message.setText(msg);
            Transport.send(message);

        } catch (MessagingException ex) {
            Logger.getLogger(mail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }}

}
