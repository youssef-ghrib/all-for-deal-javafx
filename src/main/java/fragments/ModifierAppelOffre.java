package fragments;

import dao.AppelOffreDao;
import entities.AppelOffre;
import entities.Membre;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.kairos.core.Fragment;

public class ModifierAppelOffre extends Fragment {

    AppelOffreDao appelOffreDao = new AppelOffreDao();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    AppelOffre a;

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
    TextArea txtRemarques;

    @Override
    public void onCreateView(FXMLLoader loader) {
        try {
            loader.setLocation(getClass().getResource("../fxml/modifierAppelOffre.fxml"));
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ALaUne.class.getName()).log(Level.SEVERE, null, ex);
        }

        HashMap<String, AppelOffre> args = getArguments();
        a = args.get("a");

        txtSujet.setText(a.getSujet());
        txtDescription.setText(a.getDescription());
        txtDateDebut.setValue(LocalDate.parse(a.getDateDebut(), formatter));
        txtDateFin.setValue(LocalDate.parse(a.getDateFin(), formatter));
        txtLieu.setText(a.getLieu());
        txtRemarques.setText(a.getRemarques());
    }

    public void modifier() {
        int id = a.getId();
        String sujet = txtSujet.getText();
        String description = txtDescription.getText();
        String dateDebut = txtDateDebut.getValue().toString();
        String dateFin = txtDateFin.getValue().toString();
        String lieu = txtLieu.getText();
        String remarques = txtRemarques.getText();
        Membre membre = a.getConsommateur();

        AppelOffre appelOffre = new AppelOffre(id, sujet, description, dateDebut, dateFin, lieu, remarques, membre);

        System.out.println(appelOffre);
        appelOffreDao.update(appelOffre);
    }

}
