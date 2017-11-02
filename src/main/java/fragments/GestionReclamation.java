package fragments;

import DAO.ReponseDao;

import entities.Reclamation;
import activities.Reponse;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.kairos.components.MaterialButton;
import org.kairos.core.ActivityFactory;
import org.kairos.core.Fragment;

/**
 *
 * @author Wael
 */
public class GestionReclamation extends Fragment implements Initializable {

    public static GestionReclamation rec;

    @FXML
    public TableView<Reclamation> table;
    @FXML
    public TableColumn<Reclamation, String> membre;
    @FXML
    public TableColumn<Reclamation, String> service;
    @FXML
    public TableColumn<Reclamation, String> sujet;
    @FXML
    public TableColumn<Reclamation, String> date;
    @FXML
    public MaterialButton btnsupprimer;
    @FXML
    public MaterialButton btnconsulter;
    @FXML
    public MaterialButton btnrepondre;
    @FXML
    public JFXTextField find;

    private ObservableList<Reclamation> data;

    @FXML
    public void buildData() {
        data = FXCollections.observableArrayList();
        try {
            ReponseDao dd = new ReponseDao();
            for (int i = 0; i < dd.DisplayAllReclamation().size(); i++) {
                data.add(dd.DisplayAllReclamation().get(i));
            }

            membre.setCellValueFactory(new PropertyValueFactory<>("membre"));
            service.setCellValueFactory(new PropertyValueFactory<>("service"));
            sujet.setCellValueFactory(new PropertyValueFactory<>("sujet"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            table.setItems(data);



        } catch (SQLException ex) {
            Logger.getLogger(GestionReclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void SupprimerReclamation() {
        int ind = table.getSelectionModel().getSelectedIndex();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression");
        alert.setHeaderText("Suppression de la réclamation");
        alert.setContentText("Vous etes sure de supprimer la réclamation ");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ReponseDao dao = new ReponseDao();

            try {
                String sB = data.get(ind).getSujet();
                dao.deleteReclamation(sB);
                buildData();
            } catch (Exception ex) {
                Logger.getLogger(ListeMembre1.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    public Reclamation getRecl() {
        Reclamation r = table.getSelectionModel().getSelectedItem();
        return r;
    }

    @FXML
    public void ConsulterReclamation() {
        int ind = table.getSelectionModel().getSelectedIndex();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informations");
        alert.setHeaderText("Informations sur la réclamation");
        alert.setContentText("ID membre :" + data.get(ind).getMembre().getNom() + "\n ID service réclamé : " + data.get(ind).getService().getNom()
                + "\n Sujet : " + data.get(ind).getSujet() + "\n Date : " + data.get(ind).getDate() + "\n Message : " + data.get(ind).getMessage());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            alert.close();
        }
    }

    @FXML
    public void RepondreReclamation() {
        Stage primaryStage = new Stage();
        StackPane root = new StackPane(); // Create root pane
        primaryStage.setScene(new Scene(root)); // Set the scene in the stage
        primaryStage.setResizable(false);
        // this object represent the stack  of activities  in your application
        ActivityFactory factory = new ActivityFactory(primaryStage);

        factory.startActivity(Reponse.class); // start the activity
        primaryStage.show();
    }

    @Override
    public void onCreateView(FXMLLoader loader) {
        loader.setLocation(getClass().getResource("/fxml/GestionReclamations.fxml"));
        rec = this;
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(Reclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.setRowFactory((TableView<Reclamation> p) -> {
            final TableRow<Reclamation> row = new TableRow<>();
            row.setOnMousePressed((MouseEvent t) -> {
                btnconsulter.setDisable(false);
                btnrepondre.setDisable(false);
                btnsupprimer.setDisable(false);

            });
            return row;
        });
        buildData();

        find.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                data = FXCollections.observableArrayList();

                ReponseDao dao = new ReponseDao();

                String rech = find.getText();

                try {
                    for (int i = 0; i < dao.findReclamationBySujet(rech).size(); i++) {
                        data.add(dao.findReclamationBySujet(rech).get(i));
                    }
                    table.setItems(data);

                } catch (SQLException ex) {
                    Logger.getLogger(GestionReclamation.class.getName()).log(Level.SEVERE, null, ex);
                }

                table.getSelectionModel().clearSelection();

            }
        });

    }

    public Reclamation getIdrecl() {
        Reclamation a = table.getSelectionModel().getSelectedItem();
        return a;
    }

    public String getEmail() {
        String e = table.getSelectionModel().getSelectedItem().getMembre().getMail();
        return e;
    }
}
