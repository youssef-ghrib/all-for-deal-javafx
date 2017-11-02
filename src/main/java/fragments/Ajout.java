package fragments;

import entities.Commande;
import entities.LigneCmd;
import entities.Produit;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.kairos.core.Fragment;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;

public class Ajout extends Fragment implements Initializable {

    private ObservableList<Commande> data;
    private ObservableList<Produit> data1;
    public static Ajout ajout;

    @FXML
    private TableView<Commande> tableau;

    @FXML
    private TableColumn<Commande, Integer> id_panier;
    @FXML
    private TableColumn<Commande, Float> total;

    @FXML
    private TableColumn<Commande, Number> qte;

    @FXML
    private TextField recherche;

    public static Commande cmd;
    public LigneCmd lc;
    public LigneCmd cmd1;

    @Override
    public void onCreateView(FXMLLoader loader) {

        loader.setLocation(getClass().getResource("../fxml/ajouter.fxml"));
        ajout = this;
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(Ajout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void commander() {

    }

    @FXML
    public void modifier() {

    }

    @FXML
    public void supprimer() {

    }

    public void buildData() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
