/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fragments;

import activities.Application;
import activities.Authentification;
import activities.modif_image;
import activities.modifier_compte;
import activities.modifier_pwd;
import com.dropbox.core.*;
import dao.MembreDao;
import entities.Membre;
import java.io.File;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.kairos.components.MaterialButton;
import org.kairos.core.ActivityFactory;

import org.kairos.core.Fragment;
import org.kairos.core.Intent;

/**
 *
 * @author omar
 */
public class Compte extends Fragment {

    Membre m;

    @FXML
    ImageView images;

    @FXML
    Button modifier;
    @FXML
    Label nom;
    @FXML
    Label prenom;
    @FXML
    Label age;
    @FXML
    Label adresse;
    @FXML
    Label sexe;

    @FXML
    Label login;
    @FXML
    Label points;
    @FXML
    Label mail;

    int reply;

    public Compte() {

        m = Application.app.m;
    }

    @Override
    public void onCreateView(FXMLLoader loader) {
        loader.setLocation(getClass().getResource("../fxml/Compte.fxml"));

        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(AppelsOffreMembre.class.getName()).log(Level.SEVERE, null, ex);
        }

        nom.setText(m.getNom());
        prenom.setText(m.getPrenom());
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String Age = df.format(m.getDateNaissance());
        age.setText(Age);
        adresse.setText(m.getAdresse());
        login.setText(m.getLogin());
        points.setText(String.valueOf(m.getPoints()));
        sexe.setText(m.getGenre());
        mail.setText(m.getMail());

        if (!(m.getImageName() == null)) {
            DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString());

            String accessToken = "tKqn9aG0UZAAAAAAAAAACMXrAofgnDqtNNSoX5gVYYeJCioYsaVLRnFcsdhQmWsC";

            DbxClient client = new DbxClient(config, accessToken);

            try {

                String url = client.createTemporaryDirectUrl("/" + m.getId() + "/" + m.getImageName()).url;

                Image image = new Image(url, 180, 180, true, true, true);
       // final String imageURI = new File("C://banner.jpg").toURI().toString(); 
//final Image image = new Image(imageURI);

                images.setImage(image);

            } catch (DbxException ex) {
                Logger.getLogger(Compte.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void modifier_image() {
        Stage primaryStage = new Stage();
        StackPane root = new StackPane(); // Create root pane
        primaryStage.setScene(new Scene(root)); // Set the scene in the stage

        ActivityFactory factory = new ActivityFactory(primaryStage);
        primaryStage.getScene().getStylesheets().add(MaterialButton.class.getResource("controls.css").toExternalForm());

        Intent i = new Intent(getActivity().getIntent().getExtras(), modif_image.class);

        factory.startActivity(i); // start the activity
        primaryStage.show();
    }

    public void desactiver() {
        reply = JOptionPane.showConfirmDialog(null, "voulez-vous vraiment désactiver votre compte ?", null, JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            MembreDao dao = new MembreDao();
            int idB = m.getId();
            dao.deleteImage(idB);
            dao.deleteEvaluationProd(idB);
            dao.deleteEvaluationProd(idB);

            dao.deleteDemande(idB);
            dao.deleteReclamation(idB);
            dao.deleteService(idB);
            dao.deleteAppelOffre(idB);
            dao.deleteAppelOffre1(idB);
            dao.deleteCommande(idB);
            dao.deleteligne(idB);
            dao.deleteAdresse(idB);
            dao.deleteProduit(idB);

            dao.deleteMembre(idB);
            dao.deleteById(idB);
        }
    }

    public void modifier() {
        Stage primaryStage = new Stage();
        StackPane root = new StackPane(); // Create root pane
        primaryStage.setScene(new Scene(root)); // Set the scene in the stage

        ActivityFactory factory = new ActivityFactory(primaryStage);
        primaryStage.getScene().getStylesheets().add(MaterialButton.class.getResource("controls.css").toExternalForm());

        Intent i = new Intent(getActivity().getIntent().getExtras(), modifier_compte.class);

        factory.startActivity(i); // start the activity
        primaryStage.show();
    }

    public void modifier_pwd() {
        Stage primaryStage = new Stage();
        StackPane root = new StackPane(); // Create root pane
        primaryStage.setScene(new Scene(root)); // Set the scene in the stage

        // this object represent the stack  of activities  in your application
        ActivityFactory factory = new ActivityFactory(primaryStage);
        primaryStage.getScene().getStylesheets().add(MaterialButton.class.getResource("controls.css").toExternalForm());

        factory.startActivity(modifier_pwd.class); // start the activity
        primaryStage.show();

    }

}
