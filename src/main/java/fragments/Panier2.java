/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fragments;

import activities.Application;
import dao.CommandeDao;
import dao.LigneCommandeDao;
import entities.AppelOffre;
import entities.Commande;
import entities.LigneCmd;
import entities.Produit;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
public class Panier2 extends Fragment implements Initializable {

    ObservableList<Object> data = FXCollections.observableArrayList();
    HashMap<Integer, Integer> map = new HashMap<>();

    int i = 0;

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

    LigneCommandeDao ligneDAO = new LigneCommandeDao();
    CommandeDao cmdDao = new CommandeDao();

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
        Commande cmd = (Commande) getArguments().get("cmd");
        List<Produit> produits = new ArrayList<Produit>();
        for (LigneCmd p : (List<LigneCmd>) getArguments().get("lignes")) {
            Produit produit = p.getProduit();

            produits.add(produit);

            cmd.getLignesCmd().get(i).setQte(map.get(i));
            cmd.getLignesCmd().get(i).setProduit(p.getProduit());
            cmd.getLignesCmd().get(i).setCmd(cmd);
            i++;
        }

        cmd.setTotal(Float.parseFloat(lblMontant.getText()));
        Random random = new Random();
        int j = random.nextInt(300 - 200 + 1) + 200;
        cmdDao.add1(j, 6, cmd.getDate(), cmd.getTotal());

        for (LigneCmd l : (List<LigneCmd>) getArguments().get("lignes")) {

            ligneDAO.add1(j, l.getProduit().getId(), l.getQte());
        }

    }

    public void ajouter() {

        try {
            Stage primaryStage = new Stage();
            StackPane root = new StackPane(); // Create root pane
            primaryStage.setScene(new Scene(root, 600, 800)); // Set the scene in the stage
//        primaryStage.setResizable(false);
// this object represent the stack  of activities  in your application
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            Fragment fragment = ListeProduits.class.newInstance();
            fragment.setArguments(map);
            transaction.replace("content", fragment);
            transaction.commit();
        } catch (InstantiationException ex) {
            Logger.getLogger(Panier.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
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
                lblMontant.setText(Float.toString((Float.parseFloat(lblMontant.getText()) + montant)));
                
                Notifications
                        .create()
                        .text("Commande validée avec succès")
                        .hideAfter(Duration.seconds(2))
                        .showInformation();
            }
        });

        data.clear();
        HashMap<String, Object> args = getArguments();
        List<Produit> resultat = new ArrayList<>();
        List<LigneCmd> ligneCmds = (List<LigneCmd>) args.get("lignes");

        for (int i = 0; i < ligneCmds.size(); i++) {
            resultat.add(ligneCmds.get(i).getProduit());
        }

        resultat.stream().forEach((a) -> {
            System.out.println(a);
            data.add(a);
        });
        table.setItems(data);
    }

}
