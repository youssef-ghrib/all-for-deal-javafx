package fragments;

import dao.MembreDao;
import entities.Membre;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import org.kairos.core.Fragment;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class ListeMembre1 extends Fragment implements Initializable  {
   
    
    
    private ObservableList<Membre> data;
    
    @FXML
    TableView<Membre> table;
    @FXML
    private TableColumn<Membre, String> nom;
    @FXML
    private TableColumn<Membre, String> prenom;
    @FXML
    private TableColumn<Membre, String> mail;
    @FXML
    private TableColumn<Membre, String> type;
    @FXML
    private TextField find;
    
    @FXML
    public void buildData() {
        data = FXCollections.observableArrayList();
        try {
            MembreDao dd = new MembreDao();
            for (int i = 0; i < dd.DisplayAllMembre1().size(); i++) {
                data.add(dd.DisplayAllMembre1().get(i));
            }

        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
         table.setItems(data);

    }   catch (SQLException ex) {
            Logger.getLogger(ListeMembre1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onCreateView(FXMLLoader loader) {
        loader.setLocation(getClass().getResource("/fxml/GestionMembres.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ListeMembre1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        @FXML
    public void SupprimerMembre()
    {
        int ind =table.getSelectionModel().getSelectedIndex();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Suppression de Membre");
        alert.setContentText("Vous etes sure de supprimer le membre "+data.get(ind).getNom());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
             MembreDao dao = new MembreDao();

                try {
                    int idB=  data.get(ind).getId();
                    dao.deleteImage(idB);
                    dao.deleteEvaluationProd(idB);
                    dao.deleteEvaluationProd(idB);
                    
                    dao.deleteDemande(idB);
                    dao.deleteReclamation(idB);
                    dao.deleteService(idB);
                    dao.deleteAppelOffre(idB);
                    dao.deleteAppelOffre1(idB);
                    dao.deleteCommande(idB);
                    dao.deleteligne(idB);
                    dao.deleteAdresse(idB);
                    dao.deleteProduit(idB);

                    dao.deleteMembre(idB);
                    buildData();
                NotificationType type = NotificationType.SUCCESS;
                TrayNotification tray = new TrayNotification();
                tray.setTitle("Bravo");
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
    public void ConsulterMembre()
    {
        int ind =table.getSelectionModel().getSelectedIndex();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Informations");
        alert.setHeaderText("Informations de Membre");
        alert.setContentText("Prenom : "+data.get(ind).getPrenom()+"\n Email : "+data.get(ind).getMail()+"\n Password : "+data.get(ind).getPassword()+"\n Points : "+data.get(ind).getPoints()+"\n Genre : "+data.get(ind).getGenre());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
                alert.close();
        } 
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        buildData();
//        id.setCellValueFactory(data -> data.getValue().getId());
//        nom.setCellValueFactory(data -> data.getValue().getNom());
//        prenom.setCellValueFactory(data -> data.getValue().getPrenom());
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));

        
////////////////////////////////////////        ///////////////////////////////////////////
           
        find.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
            data = FXCollections.observableArrayList();
           
            MembreDao dao = new MembreDao();
          
            String rech= find.getText();
            
           
        try {
          for(int i = 0;i<dao.findMembreByName(rech).size();i++)
          {  
              data.add(dao.findMembreByName(rech).get(i)); 
          }                           
        table.setItems(data); 
            
        } catch (SQLException ex) {
            Logger.getLogger(ListeMembre1.class.getName()).log(Level.SEVERE, null, ex);
        }
         table.getSelectionModel().clearSelection();         
            }
        });
        
    }
    
}
