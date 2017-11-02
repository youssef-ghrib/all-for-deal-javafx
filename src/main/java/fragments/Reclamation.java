/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fragments;

import DAO.ReclamationDao;
import entities.Membre;
import entities.Service;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.kairos.core.Fragment;

/**
 *
 * @author Wael
 */
public class Reclamation extends Fragment {

    @FXML
    public TextField sujet;
    @FXML
    public TextArea message;

    @FXML
    private void addReclamation() {
        ReclamationDao rec = new ReclamationDao();
        Membre m = new Membre();
//        m.setId(Authentification.id_membre);
        Service s = new Service();
        m.setId(4);
        s.setId(8);
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        Date dateobj = new Date();
        System.out.println(df.format(dateobj));
        String dattte = df.format(dateobj).toString();
        entities.Reclamation r = new entities.Reclamation(m, s, sujet.getText(), message.getText(), dattte,"");
        rec.insertReclamation(r);
        message.setText(null);
        sujet.setText(null);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Merci");
        alert.setHeaderText("Reclamation envoyée !");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

        } else {
            // ... user chose CANCEL or closed the dialog
        }

    }

    @Override
    public void onCreateView(FXMLLoader loader) {
        loader.setLocation(getClass().getResource("/fxml/Reclamation.fxml"));
        
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(Reclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
