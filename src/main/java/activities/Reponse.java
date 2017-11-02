package activities;

import dao.MembreDao;
import DAO.ReclamationDao;
import entities.Membre;
import entities.Reclamation;
import entities.Service;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import fragments.GestionReclamation;
import fragments.ListeMembre1;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.util.Duration;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import org.kairos.components.MaterialButton;
import org.kairos.core.Activity;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 *
 * @author Wael
 */
public class Reponse extends Activity {

    @FXML
    public JFXTextField sujet;
    @FXML
    public JFXTextArea message;
    @FXML
    public MaterialButton envoyer;

    @Override
    public void onCreate() {
        super.onCreate();
        setContentView(getClass().getResource("/fxml/ReponseAdmin.fxml"));
    }

    @FXML
    public void envoyermail(ActionEvent event) throws ParseException, MessagingException, SQLException {
        MembreDao md = new MembreDao();
        ReclamationDao dao = new ReclamationDao();
        MembreDao mdao = new MembreDao();
        Reclamation rec = new Reclamation();
        String msgg = message.getText();
        int id_s = GestionReclamation.rec.getIdrecl().getService().getId();
        int id_m = GestionReclamation.rec.getIdrecl().getMembre().getId();
        Service se = new Service();
        Membre me = new Membre();
        se.setId(id_m);
        rec.setService(se);
        me.setId(id_s);
        rec.setMembre(me);

        String s = sujet.getText();
        String sujet_service = GestionReclamation.rec.getIdrecl().getSujet();
        String m = message.getText();
        Service a = GestionReclamation.rec.getIdrecl().getService();
        if (s.equals("") || m.equals("") || a == null) {
            JOptionPane.showMessageDialog(null, "Veuillez remplir tout les champs et choisir un service");
        } else {
            Membre proprio = GestionReclamation.rec.getIdrecl().getMembre();

            String msg = "Bonjour,\n Concernant la réclamation que vous avez ajouté sur le service " + a.getNom()
                    + " et dont le sujet est : "
                    + sujet_service + "\n La réponse de l'adminstration est :\n" + m
                    + "\n Merci pour votre attention \n Cordialement";
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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Envoie du reponse");
            alert.setContentText("Vous voulez confirmer l'envoie ");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                try {
                    try {

                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress("allfordeal1@gmail.com"));

                        InternetAddress[] addressTo = new InternetAddress[1];
                        System.out.println("aaabbbccc" + id_m + " azdasad " + id_s);
                        addressTo[0] = new InternetAddress(mdao.findMailById(id_s).getMail());

                        message.setRecipients(Message.RecipientType.TO, addressTo);
                        message.setSubject(s);
                        message.setText(msg);
                        Transport.send(message);
                        dao.insertReponse(rec, m);
                        NotificationType type = NotificationType.SUCCESS;
                        TrayNotification tray = new TrayNotification();
                        tray.setTitle("Succés");
                        tray.setMessage("Envoie Reussi");
                        tray.setNotificationType(type);
                        tray.setAnimationType(AnimationType.POPUP);
                        tray.showAndDismiss(Duration.seconds(10));
                        ((Node) (event.getSource())).getScene().getWindow().hide();
                    } catch (MessagingException ex) {
                        Logger.getLogger(Reponse.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ListeMembre1.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
        }
    }

}
