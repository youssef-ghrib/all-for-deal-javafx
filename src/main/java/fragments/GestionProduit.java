/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fragments;

import dao.GestionProduitDao;
import entities.Produit;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import org.kairos.components.MaterialRadioButton;
import org.kairos.core.Fragment;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class GestionProduit extends Fragment implements Initializable {

    @FXML
    public TableView<Produit> table;
    @FXML
    public TableColumn<Produit, String> s_cat;
    @FXML
    public TableColumn<Produit, String> nom;
    @FXML
    public TableColumn<Produit, Integer> points;
    @FXML
    public TableColumn<Produit, String> etat;
    @FXML
    public MaterialRadioButton valide;
    @FXML
    public MaterialRadioButton refuse;
    @FXML
    public MaterialRadioButton encours;

    @FXML
    public JFXTextField find;

    private ObservableList<Produit> data;

    @FXML
    public void buildData() {
        data = FXCollections.observableArrayList();
        try {
            GestionProduitDao dd = new GestionProduitDao();
            for (int i = 0; i < dd.DisplayAllProduit().size(); i++) {
                data.add(dd.DisplayAllProduit().get(i));
            }
            nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            s_cat.setCellValueFactory(new PropertyValueFactory<>("categorie"));
            points.setCellValueFactory(new PropertyValueFactory<>("points"));
            etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
            table.setItems(data);

        } catch (SQLException ex) {
            Logger.getLogger(GestionProduit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void Valider() {
        try {
            String e = etat.getText();
            {
                GestionProduitDao dao = new GestionProduitDao();
                String nom = table.getSelectionModel().getSelectedItem().getNom();
                int ind = table.getSelectionModel().getSelectedIndex();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Validation");
                alert.setHeaderText("validation du produit");
                alert.setContentText("Vous etes sure de valider ce produit ");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        int id = data.get(ind).getId();
                        Produit p = new Produit();
                        p.setId(id);
                        dao.ValiderProduit(p);
                        NotificationType type = NotificationType.SUCCESS;
                        TrayNotification tray = new TrayNotification();
                        tray.setTitle("Bravo");
                        tray.setMessage("Le Produit " + nom + " est Validé !");
                        tray.setNotificationType(type);
                        tray.setAnimationType(AnimationType.POPUP);
                        tray.showAndDismiss(Duration.seconds(10));
                    } catch (Exception ex) {
                        Logger.getLogger(ListeMembre1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    alert.close();
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(GestionProduitDao.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    @FXML
    void Refuser() {
        try {
            String e = etat.getText();
            {
                GestionProduitDao dao = new GestionProduitDao();
                int ind = table.getSelectionModel().getSelectedIndex();
                String nom = table.getSelectionModel().getSelectedItem().getNom();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Refus");
                alert.setHeaderText("Refus du produit");
                alert.setContentText("Vous etes sure de refuser ce produit ");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        int id = data.get(ind).getId();
                        Produit p = new Produit();
                        p.setId(id);
                        dao.RefuserProduit(p);
                        NotificationType type = NotificationType.SUCCESS;
                        TrayNotification tray = new TrayNotification();
                        tray.setTitle("Bravo");
                        tray.setMessage("Le Produit " + nom + " est réfusé !");
                        tray.setNotificationType(type);
                        tray.setAnimationType(AnimationType.POPUP);
                        tray.showAndDismiss(Duration.seconds(10));
                    } catch (Exception ex) {
                        Logger.getLogger(ListeMembre1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    alert.close();
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(GestionProduitDao.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    @FXML
    public void handleRadioButton1() {
        if ((valide.isSelected() == true) && (refuse.isSelected() == false) && (encours.isSelected() == false)) {
            refuse.setDisable(true);
            encours.setDisable(true);
        } else {
            valide.setDisable(false);
            refuse.setDisable(false);
            encours.setDisable(false);
            buildData();
        }
    }

    @FXML
    public void handleRadioButton2() {
        if ((valide.isSelected() == false) && (refuse.isSelected() == true) && (encours.isSelected() == false)) {
            valide.setDisable(true);
            encours.setDisable(true);
        } else {
            valide.setDisable(false);
            refuse.setDisable(false);
            encours.setDisable(false);
            buildData();
        }
    }

    @FXML
    public void handleRadioButton3() {
        if ((valide.isSelected() == false) && (refuse.isSelected() == false) && (encours.isSelected() == true)) {
            refuse.setDisable(true);
            valide.setDisable(true);
        } else {
            valide.setDisable(false);
            refuse.setDisable(false);
            encours.setDisable(false);
            buildData();
        }
    }

    @Override
    public void onCreateView(FXMLLoader loader) {
        loader.setLocation(getClass().getResource("/fxml/GestionProduit.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(entities.Reclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void ConsulterProduit() {
        int ind = table.getSelectionModel().getSelectedIndex();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informations");
        alert.setHeaderText("Informations sur le produit");
        alert.setContentText("ID produit :" + data.get(ind).getId() + "\n Nom du produit : " + data.get(ind).getNom()
                + "\n Prix : " + data.get(ind).getPrix() + "\n Reduction : " + data.get(ind).getReduction() + "\n Points : " + data.get(ind).getPoints()
                + "\n Etat : " + data.get(ind).getEtat() + "\n Date Lancement : " + data.get(ind).getDate_lancement() + "\n Couleur : " + data.get(ind).getCouleur()
                + "\n Categorie : " + data.get(ind).getCategorie().getNom());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            alert.close();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            s_cat.setCellValueFactory(new PropertyValueFactory<>("categorie"));
            points.setCellValueFactory(new PropertyValueFactory<>("points"));
            etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        buildData();

        valide.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                refuse.setDisable(true);
                encours.setDisable(true);
                data = FXCollections.observableArrayList();
                GestionProduitDao dao = new GestionProduitDao();
                try {
                    for (int i = 0; i < dao.findProduitByEtat("valide").size(); i++) {
                        data.add(dao.findProduitByEtat("valide").get(i));
                    }
                    table.setItems(data);

                } catch (SQLException ex) {
                    Logger.getLogger(GestionReclamation.class.getName()).log(Level.SEVERE, null, ex);
                }

                table.getSelectionModel().clearSelection();
                etat.setStyle("-fx-border-color: #3AF24B;");
            }
        });

        refuse.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                valide.setDisable(true);
                encours.setDisable(true);
                data = FXCollections.observableArrayList();
                GestionProduitDao dao = new GestionProduitDao();
                try {
                    for (int i = 0; i < dao.findProduitByEtat("refuse").size(); i++) {
                        data.add(dao.findProduitByEtat("refuse").get(i));
                    }
                    table.setItems(data);

                } catch (SQLException ex) {
                    Logger.getLogger(GestionReclamation.class.getName()).log(Level.SEVERE, null, ex);
                }

                table.getSelectionModel().clearSelection();

                etat.setStyle("");
                etat.setStyle("-fx-border-color: #FD2A00;");
            }
        });

        encours.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                refuse.setDisable(true);
                valide.setDisable(true);
                data = FXCollections.observableArrayList();
                GestionProduitDao dao = new GestionProduitDao();
                try {
                    for (int i = 0; i < dao.findProduitByEtat("encours").size(); i++) {
                        data.add(dao.findProduitByEtat("encours").get(i));
                    }
                    table.setItems(data);

                } catch (SQLException ex) {
                    Logger.getLogger(GestionReclamation.class.getName()).log(Level.SEVERE, null, ex);
                }

                table.getSelectionModel().clearSelection();
                etat.setStyle("");
                etat.setStyle("-fx-border-color:#6C63E9;");
            }
        });

//        find.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
//                data = FXCollections.observableArrayList();
//
//                IGestionProduit dao = new GestionProduitDao();
//
//                String rech = find.getText();
//
//                try {
//                    for (int i = 0; i < dao.findProduitByEtat(rech).size(); i++) {
//                        data.add(dao.findProduitByEtat(rech).get(i));
//                    }
//                    table.setItems(data);
//
//                } catch (SQLException ex) {
//                    Logger.getLogger(GestionReclamation.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//                table.getSelectionModel().clearSelection();
//
////            }
//        });
    }

}
