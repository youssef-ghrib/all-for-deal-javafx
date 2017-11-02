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
public class EvaluationProduit extends Fragment implements Initializable {

    @FXML
    public TableView<Produit> table;
    @FXML
    public TableColumn<Produit, String> nom;
    @FXML
    public TableColumn<Produit, String> categorie;
    @FXML
    public TableColumn<Produit, Float> prix;
    @FXML
    public TableColumn<Produit, Integer> point;
    @FXML
    public MaterialButton evaluer;
    @FXML
    public MaterialButton consulter;
    @FXML
    public JFXTextField txtPoints;

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
            categorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
            prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
            point.setCellValueFactory(new PropertyValueFactory<>("points"));
            table.setItems(data);

        } catch (SQLException ex) {
            Logger.getLogger(EvaluationProduit.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    @FXML
    void GetSelectedProduit()
        {
     
           //   Rdv rd = tabRdv.getSelectionModel().getSelectedItem();
              int ind = table.getSelectionModel().getSelectedIndex();

              txtPoints.setText(String.valueOf(data.get(ind).getPoints()));

              evaluer.setVisible(true);
              consulter.setVisible(true);
          
        }
    
  @FXML
    public void evaluer()
    {
        GestionProduitDao pdao = new GestionProduitDao();
       int pointss= Integer.parseInt(txtPoints.getText()) ;

       
       int ind =table.getSelectionModel().getSelectedIndex();
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Evaluation de Produit");
        alert.setContentText("Evaluation du produit "+data.get(ind).getNom());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        try {
             int idP=  data.get(ind).getId();
             pdao.changerNbrPointsById(pointss,idP);
                NotificationType type = NotificationType.SUCCESS;
                TrayNotification tray = new TrayNotification();
                tray.setTitle("Evaluation");
                tray.setMessage("Evaluation Reussi");
                tray.setNotificationType(type);
                tray.setAnimationType(AnimationType.POPUP);
                tray.showAndDismiss(Duration.seconds(10));
        } catch (Exception ex) {
            Logger.getLogger(EvaluationProduit.class.getName()).log(Level.SEVERE, null, ex);
        }
        refresh();
        }else {
            refresh();
        }
    }
        @FXML
    void refresh()
    {
        txtPoints.setText(null); 
        consulter.setVisible(true);
        evaluer.setVisible(false);
        buildData();
    } 
    
    @FXML
    public void ConsulterReclamation()
    {
        int ind =table.getSelectionModel().getSelectedIndex();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informations");
        alert.setHeaderText("Informations sur le produit");
        alert.setContentText("ID produit :"+data.get(ind).getId()+"\n Nom du produit : "+data.get(ind).getNom()+
        "\n Prix : "+data.get(ind).getPrix()+"\n Reduction : "+data.get(ind).getReduction()+"\n Points : "+data.get(ind).getPoints()+
        "\n Etat : "+data.get(ind).getEtat()+"\n Date Lancement : "+data.get(ind).getDate_lancement()+"\n Couleur : "+data.get(ind).getCouleur()
                +"\n Categorie : "+data.get(ind).getCategorie().getNom());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
                alert.close();
        } 
    }
    

    @Override
    public void onCreateView(FXMLLoader loader) {
        loader.setLocation(getClass().getResource("/fxml/EvaluationProduit.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(entities.Reclamation.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildData();
        evaluer.setVisible(false);
    }
    
}
