/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fragments;

import com.jfoenix.controls.JFXButton;
import dao.MembreDao;
import dao.ProduitDao;
import entities.Commande;
import entities.LigneCmd;
import entities.Membre;
import entities.Produit;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.joda.time.DateTime;
import org.kairos.components.MaterialButton;
import org.kairos.core.ActivityFactory;
import org.kairos.core.Fragment;
import org.kairos.core.FragmentTransaction;
import org.kairos.core.Intent;

/**
 *
 * @author joseph
 */
public class ListeProduits extends Fragment implements Initializable {

    private ObservableList<Produit> data;
    List<LigneCmd> lignesCmd = new ArrayList<>();
    private boolean existe = false;
    Membre m = new Membre();
    MembreDao membreDao = new MembreDao();
    Commande cmd = new Commande();

    @FXML
    JFXButton btnFermer;

    @FXML
    TableView<Produit> tabid;

    @FXML
    private TableColumn<Produit, String> nomid;
    @FXML
    private TableColumn<Produit, Number> prixid;
    @FXML
    private TableColumn<Produit, Number> reductionid;

    @FXML
    private TableColumn<Produit, String> couleurid;
    @FXML
    private TableColumn<Produit, String> categorieid;

    @FXML
    private TableColumn<Produit, Number> pointid;
    @FXML
    private TableColumn<Produit, String> sous_categorie;
    @FXML
    private TableColumn<Produit, String> dateid;

    public void buildData() {
        data = FXCollections.observableArrayList();
        try {
            ProduitDao pDao = new ProduitDao();
            for (int i = 0; i < pDao.DisplayAllProduit().size(); i++) {
                data.add(pDao.DisplayAllProduit().get(i));
            }

            nomid.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prixid.setCellValueFactory(new PropertyValueFactory<>("prix"));
            couleurid.setCellValueFactory(new PropertyValueFactory<>("couleur"));
            categorieid.setCellValueFactory(new PropertyValueFactory<>("categorie"));
            reductionid.setCellValueFactory(new PropertyValueFactory<>("reduction"));
            pointid.setCellValueFactory(new PropertyValueFactory<>("points"));
            sous_categorie.setCellValueFactory(new PropertyValueFactory<>("souCategorie"));
            dateid.setCellValueFactory(new PropertyValueFactory<>("dateLancement"));

            tabid.setItems(data);
        } catch (Exception e) {
            System.out.println("Error on Building Data");
        }
    }

    public void ajout() {
        DateTime dateTime = new DateTime();
        String date = dateTime.toString().substring(0, 10);
        Produit p = tabid.getSelectionModel().getSelectedItem();

        for (LigneCmd l : lignesCmd) {
            if (l.getProduit().equals(p)) {

                existe = true;

                Notifications
                        .create()
                        .text("Cet produit éxiste déja dans le panier")
                        .hideAfter(Duration.seconds(2))
                        .showInformation();
            }
        }

        if (existe == false) {
            LigneCmd cmdCliProd = new LigneCmd();
            cmdCliProd.setProduit(p);

            lignesCmd.add(cmdCliProd);
            Membre m = membreDao.findById(1);
            cmd.setConsommateur(m);
            cmd.setDate(date);
            cmd.setLignesCmd(lignesCmd);
            btnFermer.setDisable(false);
        }

        existe = false;
    }

    public void btnFermerAction() throws IOException {

        HashMap<String, Object> map = new HashMap<>();
        map.put("cmd", cmd);
        map.put("lignes", lignesCmd);

        try {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            Fragment fragment = Panier2.class.newInstance();
            fragment.setArguments(map);
            transaction.replace("content", fragment);
            transaction.commit();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ConsulterProduit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildData();
        nomid.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prixid.setCellValueFactory(new PropertyValueFactory<>("prix"));
        couleurid.setCellValueFactory(new PropertyValueFactory<>("couleur"));
        categorieid.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        reductionid.setCellValueFactory(new PropertyValueFactory<>("reduction"));
        pointid.setCellValueFactory(new PropertyValueFactory<>("points"));
        sous_categorie.setCellValueFactory(new PropertyValueFactory<>("souCategorie"));
        dateid.setCellValueFactory(new PropertyValueFactory<>("dateLancement"));

    }

    @Override
    public void onCreateView(FXMLLoader fxmll) {
        super.onCreate();

        fxmll.setLocation(getClass().getResource("/fxml/listeProduits.fxml"));

        try {
            fxmll.load();
        } catch (IOException ex) {
            Logger.getLogger(AppelsOffreMembre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
