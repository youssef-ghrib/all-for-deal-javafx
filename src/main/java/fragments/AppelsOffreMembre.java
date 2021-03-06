package fragments;

import activities.Authentification;
import activities.ConsulterAppelOffre;
import dao.AppelOffreDao;
import dao.MembreDao;
import entities.AppelOffre;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.kairos.components.MaterialButton;
import org.kairos.core.ActivityFactory;
import org.kairos.core.Fragment;
import org.kairos.core.FragmentTransaction;
import org.kairos.core.Intent;

public class AppelsOffreMembre extends Fragment implements Initializable {

    ObservableList<AppelOffre> data = FXCollections.observableArrayList();

    AppelOffreDao appelOffreDao = new AppelOffreDao();

    MembreDao membreDao = new MembreDao();

//    @FXML
//    IconButton btnAjouter;
    @FXML
    VBox vbox;

    @FXML
    TextField txtFiltre;

    @FXML
    MaterialButton btnConsulter;

    @FXML
    MaterialButton btnModifier;

    @FXML
    MaterialButton btnSupprimer;

    @FXML
    TableView<AppelOffre> table;

    @FXML
    TableColumn<AppelOffre, String> sujet;

    @FXML
    TableColumn<AppelOffre, String> description;

    @FXML
    TableColumn<AppelOffre, String> dateDebut;

    @FXML
    TableColumn<AppelOffre, String> dateFin;

    @FXML
    TableColumn<AppelOffre, String> lieu;

    @FXML
    TableColumn<AppelOffre, String> remarques;

    @Override
    public void onCreateView(FXMLLoader loader) {
        loader.setLocation(getClass().getResource("../fxml/appelsOffreMembre.fxml"));

        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(AppelsOffreMembre.class.getName()).log(Level.SEVERE, null, ex);
        }

//        btnAjouter.setIcon(getClass().getResourceAsStream("../img/plus.svg"));
    }

    public void filtrer() {
        data.clear();
        String filtre = txtFiltre.getText().trim();
        List<AppelOffre> resultat = appelOffreDao.findByFilter(filtre);
        resultat.stream().forEach((a) -> {
            data.add(a);
        });
        table.setItems(data);
    }

    public void modifier() {
        AppelOffre a = table.getSelectionModel().getSelectedItem();
        HashMap<String, AppelOffre> args = new HashMap<>();

        try {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            Fragment fragment = ModifierAppelOffre.class.newInstance();
            args.put("a", a);
            fragment.setArguments(args);
            transaction.replace("content", fragment);
            transaction.commit();
//                toolbar.setTitle(newValue.title);
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(AppelsOffre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void supprimer() {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText("Voulez-vous vrément supprimer cet appel d'offre?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            AppelOffre a = table.getSelectionModel().getSelectedItem();
            appelOffreDao.delete(a);

            Notifications
                    .create()
                    .text("Appel d'offre supprimé avec succès")
                    .hideAfter(Duration.seconds(5))
                    .showInformation();
        }

        filtrer();
    }

    public void consulter(ActionEvent event) {

//        vbox.setEffect(new GaussianBlur());
        Stage stage = new Stage();

        StackPane root = new StackPane(); // Create root pane
        stage.setScene(new Scene(root)); // Set the scene in the stage

        stage.setResizable(false);

        // this object represent the stack  of activities  in your application
        ActivityFactory factory = new ActivityFactory(stage);

        HashMap<String, Object> extras = new HashMap<>();
        AppelOffre a = table.getSelectionModel().getSelectedItem();
        extras.put("a", a);

        Intent intent = new Intent(extras, ConsulterAppelOffre.class);

        // set the material design style in your applicat
        stage.getScene().getStylesheets().add(MaterialButton.class.getResource("controls.css").toExternalForm());
        stage.getScene().getStylesheets().add(getClass().getResource("../css/styles.css").toExternalForm());

        factory.startActivity(intent); // start the activity

        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.setRowFactory((TableView<AppelOffre> p) -> {
            final TableRow<AppelOffre> row = new TableRow<>();
            row.setOnMousePressed((MouseEvent t) -> {
                btnConsulter.setDisable(false);
                btnModifier.setDisable(false);
                btnSupprimer.setDisable(false);
            });
            return row;
        });

        sujet.setCellValueFactory(new PropertyValueFactory<>("sujet"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        dateFin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        lieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));

        data.clear();

        List<AppelOffre> resultat = appelOffreDao.displayByMember(membreDao.findById(1));

        resultat.stream().forEach((a) -> {
            data.add(a);
        });
        table.setItems(data);
    }

}
