/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fragments;

import dao.servicedao;
import entities.Service;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.kairos.components.MaterialButton;
import org.kairos.core.Fragment;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 *
 * @author Wael
 */
public class GestionServices extends Fragment implements Initializable {

    @FXML
    public MaterialButton btnconsulter;
    @FXML
    public MaterialButton btnsupprimer;
    @FXML
    public TableView<Service> table;
    @FXML
    public TableColumn<Service, String> nom;
    @FXML
    public TableColumn<Service, String> fournisseur;
    @FXML
    public TableColumn<Service, Integer> categorie;

    private ObservableList<Service> data;

    @FXML
    public void AffichageServices() {
        data = FXCollections.observableArrayList();
        try {
            servicedao dd = new servicedao();
            for (int i = 0; i < dd.DisplayAllServices1().size(); i++) {
                data.add(dd.DisplayAllServices1().get(i));
            }

            nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            fournisseur.setCellValueFactory(new PropertyValueFactory<>("membre"));
            categorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
            table.setItems(data);

        } catch (SQLException ex) {
            Logger.getLogger(GestionServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     @FXML
    public void SupprimerService()
    {
        int ind =table.getSelectionModel().getSelectedIndex();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Suppression de Service");
        alert.setContentText("Vous etes sure de supprimer ce service "+data.get(ind).getNom());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
             servicedao dao = new servicedao();

                try {
                    int idB=  data.get(ind).getId();
                    dao.deleteService(idB);
                    AffichageServices();
                NotificationType type = NotificationType.SUCCESS;
                TrayNotification tray = new TrayNotification();
                tray.setTitle("Succes");
                tray.setMessage("Suppression Reussi");
                tray.setNotificationType(type);
                tray.setAnimationType(AnimationType.POPUP);
                tray.showAndDismiss(Duration.seconds(10));
                } catch (Exception ex) {
                    Logger.getLogger(ListeMembre1.class.getName()).log(Level.SEVERE, null, ex);
                }
        } else {
            // ... user chose CANCEL or closed the dialog
        }
}
        @FXML
    public void ConsulterService() throws SQLException
    {
        servicedao dd = new servicedao();
        int ind =table.getSelectionModel().getSelectedIndex();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informations");
        alert.setHeaderText("Informations Service");
        alert.setContentText("Nom du Service : "+data.get(ind).getNom()+"\n Categorie : "+data.get(ind).getCategorie()+"\n Description : "+data.get(ind).getDescription()
                +"\n Fournisseur : "+data.get(ind).getId_fournisseur().getNom()+"\n Etat : "+dd.verifEtatService(data.get(ind).getId()));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
                alert.close();
        } 
    }

    @Override
    public void onCreateView(FXMLLoader loader) {
        loader.setLocation(getClass().getResource("/fxml/GestionServices.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(entities.Service.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        fournisseur.setCellValueFactory(new PropertyValueFactory<>("membre"));
        categorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        AffichageServices();
        
       
    }

}
