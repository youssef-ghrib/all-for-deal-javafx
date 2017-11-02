/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fragments;

import activities.Application;
import entities.AppelOffre;
import entities.Produit;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;
import org.controlsfx.control.Notifications;
import org.kairos.components.MaterialButton;
import org.kairos.core.ActivityFactory;
import org.kairos.core.Fragment;
import org.kairos.core.FragmentTransaction;
import org.kairos.core.Intent;

/**
 *
 * @author joseph
 */
public class Panier extends Fragment implements Initializable {

    ObservableList<Object> data = FXCollections.observableArrayList();
    HashMap<Integer, Integer> map = new HashMap<>();

    @FXML
    Label lblMontant;

    @FXML
    TableView<Object> table;

    @FXML
    TableColumn<Object, String> nom;

    @FXML
    TableColumn<Object, Float> prix;

    @FXML
    TableColumn<Object, Integer> qte;

    @Override
    public void onCreateView(FXMLLoader fxmll) {

        fxmll.setLocation(getClass().getResource("../fxml/panier.fxml"));

        try {
            fxmll.load();
        } catch (IOException ex) {
            Logger.getLogger(AppelsOffreMembre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void valider() {

    }

    public void ajouter() {

        try {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            Fragment fragment = ListeProduits.class.newInstance();
            fragment.setArguments(map);
            System.out.println(map);
            transaction.replace("content", fragment);
            transaction.commit();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Panier.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        qte.setCellValueFactory(new PropertyValueFactory<>("qte"));
        prix.setCellValueFactory(new PropertyValueFactory<>("prix"));

        qte.setCellFactory(TextFieldTableCell.<Object, Integer>forTableColumn(new IntegerStringConverter()));
        qte.setOnEditCommit((CellEditEvent<Object, Integer> t) -> {
            if (t.getNewValue() <= 0) {
                Notifications
                        .create()
                        .text("Veuillez insérer une quantité positive")
                        .hideAfter(Duration.seconds(2))
                        .showInformation();
            } else {
                map.put(t.getTablePosition().getRow(), t.getNewValue());
                float montant = prix.getCellData(t.getTablePosition().getRow()) * t.getNewValue();
                lblMontant.setText(String.valueOf((Float.parseFloat(lblMontant.getText()) + montant)));
            }
        });

        data.clear();
        HashMap<String, Object> args = getArguments();
        List<Produit> resultat = new ArrayList<>();
        resultat.add((Produit) args.get("produit"));
        resultat.stream().forEach((a) -> {
            System.out.println(a);
            data.add(a);
        });
        table.setItems(data);
    }

}
