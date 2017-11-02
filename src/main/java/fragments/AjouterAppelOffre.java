package fragments;

import dao.AppelOffreDao;
import dao.MembreDao;
import entities.AppelOffre;
import entities.Membre;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.kairos.core.Fragment;
import org.kairos.layouts.ViewPager;

public class AjouterAppelOffre extends Fragment {

    MembreDao membreDao = new MembreDao();
    AppelOffreDao appelOffreDao = new AppelOffreDao();

    @FXML
    TextField txtSujet;

    @FXML
    TextArea txtDescription;

    @FXML
    DatePicker txtDateDebut;

    @FXML
    DatePicker txtDateFin;

    @FXML
    TextField txtLieu;

    @FXML
    TextArea txtRemarque;

    @Override
    public void onCreateView(FXMLLoader loader) {
        loader.setLocation(getClass().getResource("../fxml/ajouterAppelOffre.fxml"));

        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(AppelsOffreMembre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ajouter(ActionEvent event) {

        String sujet = txtSujet.getText();
        String description = txtDescription.getText();
        String dateDebut = txtDateDebut.getValue().toString();
        String dateFin = txtDateFin.getValue().toString();
        String lieu = txtLieu.getText();
        String remarque = txtRemarque.getText();
        Membre consommateur = membreDao.findById(1);

        AppelOffre appelOffre = new AppelOffre(sujet, description, dateDebut, dateFin, lieu, remarque, consommateur);

        appelOffreDao.add(appelOffre);

        Notifications
                .create()
                .text("Appel d'offre ajouté avec succès")
                .hideAfter(Duration.seconds(5))
                .showInformation();

        ViewPager viewPager = (ViewPager) getParent();
        viewPager.setCurrentItem(0);
    }

}
