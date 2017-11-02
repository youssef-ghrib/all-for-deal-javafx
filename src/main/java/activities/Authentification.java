package activities;

import dao.AuthentificationDao;
import dao.MembreDao;
import entities.Membre;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.kairos.core.Activity;
import org.kairos.core.Intent;
import sun.security.util.Password;

public class Authentification extends Activity {

    AuthentificationDao AuDao = new AuthentificationDao();
    MembreDao Mdao = new MembreDao();

    @FXML
    public TextField txtNom;
    @FXML
    public PasswordField txtPwd;


    String idmm;

    @Override
    public void onCreate() {
        super.onCreate();
        setContentView(getClass().getResource("/fxml/authentification.fxml"));
    }

    public void login() {

        String username = txtNom.getText();
        String password = (txtPwd.getText());

        if (username.trim().equals("") || password.trim().equals("")) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Mot de passe et/ou login est vide");
            alert.showAndWait();

        } else if (Mdao.getUser(username, password) == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Mot de passe et/ou login erroné(s)");
            alert.showAndWait();

        } else if (Mdao.findTypeUser(username).equals("a:0:{}")) {

            User = true;

            membre = Mdao.getUser(username, password);

            HashMap<String, Object> args = new HashMap<>();
            args.put("admin", false);
            args.put("user", true);

            args.put("membre", membre);
            Intent i = new Intent(args, Application.class);
            i.putExtras(args);

            startActivity(i);

        } else {

            Admin = true;
            membre = Mdao.getUser(username, password);

            HashMap<String, Object> args = new HashMap<>();
            args.put("admin", true);
            args.put("user", false);
            
            args.put("membre", membre);

            Intent i = new Intent(args, Application.class);
            i.putExtras(args);

            startActivity(i);

        }
    }

    @FXML
    public void Inscription() {
        startActivity(Inscription.class);
    }
}
