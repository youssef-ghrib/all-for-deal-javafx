/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fragments;

import dao.demandedao;
import dao.servicedao;
import entities.Membre;
import entities.demande;
import entities.Service;

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
import javafx.scene.control.Label;
import org.kairos.core.Fragment;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class DemandeFrag extends Fragment implements Initializable  {
    @Override
   public void onCreateView(FXMLLoader loader) {
        loader.setLocation(getClass().getResource("../fxml/Demande.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(AppelsOffreMembre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //Bloc
//    private ObservableList<Integer> data_id = FXCollections.observableArrayList();
//    private ObservableList<String> data_nom = FXCollections.observableArrayList();
//    private ObservableList<String> data_categorie = FXCollections.observableArrayList();
//    private ObservableList<String> data_description = FXCollections.observableArrayList();
//   private ObservableList<String> data_fournisseur = FXCollections.observableArrayList();
    
       //Hbox
//    final AutoFillTextBox box_id_bloc = new AutoFillTextBox();
    final TextField box_nom_bloc = new TextField();
    final TextField box_type_bloc = new TextField();
    
    private ObservableList<demande> data;
    
    @FXML
    TableView<demande> table;
    @FXML
    private TableColumn<demande, String> Membre;
    @FXML
    private TableColumn<demande, String> Service;
    @FXML
    private TableColumn<demande, String> date_debut;
     @FXML
    private TableColumn<demande, String> date_fin;
    @FXML
    private TableColumn<demande, String> lieu;
    @FXML
    private TableColumn<demande, String> Message;
     
     
//    @FXML
//    private TableColumn<Membre, String> password;
    @FXML
    private TextField find;
    
    @FXML
    public void buildData() {
        data = FXCollections.observableArrayList();
        try {
            demandedao dd = new demandedao();
            for (int i = 0; i < dd.DisplayAll_demande().size(); i++) {
                
                data.add(dd.DisplayAll_demande().get(i));
                
            
            }
        Membre.setCellValueFactory(new PropertyValueFactory<>("id_membre"));
        Service.setCellValueFactory(new PropertyValueFactory<>("id_service"));
        date_debut.setCellValueFactory(new PropertyValueFactory<>("datdebu"));
        date_fin.setCellValueFactory(new PropertyValueFactory<>("datefin"));
         lieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
              Message.setCellValueFactory(new PropertyValueFactory<>("message"));
         table.setItems(data);
      
        } catch (Exception e) {
            System.out.println("Error on Building Data");
        }
    }
    

  
    
        @FXML
    public void SupprimerMembre()
    {
        int ind =table.getSelectionModel().getSelectedIndex();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Suppression de reclamation");
        alert.setContentText("Vous etes sure de supprimer le membre "+data.get(ind).getId_membre().getNom()+ "~~du Monsieur"+data.get(ind).getId_service().getNom());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
             servicedao dao = new servicedao();

                try {
                    int idB=  data.get(ind).getId_membre().getId();
                    dao.deleteService(idB);
                    buildData();
                } catch (Exception ex) {
                    Logger.getLogger(ListeMembre1.class.getName()).log(Level.SEVERE, null, ex);
                }
        } else {
            // ... user chose CANCEL or closed the dialog
        }
}
    
     @FXML
    public void DemandeBUTTON()
    {
        try {
            
        
        int ind =table.getSelectionModel().getSelectedIndex();
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Informations");
        alert.setHeaderText("Informations de Membre");
//        alert.setContentText("Le nom est :"+data.get(ind).getNom()+"\n Description : "+data.get(ind).getDescription()+"\n Categorie : "+data.get(ind).getCategorie()+
//        "\n ID : "+data.get(ind).getId_service());
        alert.setContentText("Contactez le fournisseur de service en messagerie pour entamer l echange de service");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
                alert.close();
        } 
        } catch (Exception e) {
            System.out.println("consulter error");
        }
    }

    
    @FXML
    public void ConsulterMembre()
    {
        try {
            
        
        int ind =table.getSelectionModel().getSelectedIndex();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Informations");
        alert.setHeaderText("Informations de Membre");
        alert.setContentText("Le nom est :"+data.get(ind).getDatdebu()+"\n Description : "+data.get(ind).getDatefin()+"\n Categorie : "+data.get(ind).getLieu()+
        "\n ID : "+data.get(ind).getId_service()+" \n nom fournisseur:"+data.get(ind).getId_service().getNom());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
                alert.close();
        } 
        } catch (Exception e) {
            System.out.println("consulter error");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        id.setCellValueFactory(data -> data.getValue().getId());
//        nom.setCellValueFactory(data -> data.getValue().getNom());
//        prenom.setCellValueFactory(data -> data.getValue().getPrenom());
        Service.setCellValueFactory(new PropertyValueFactory<>("Service"));
        Membre.setCellValueFactory(new PropertyValueFactory<>("Membre"));
        date_debut.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        date_fin.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
    lieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
      Message.setCellValueFactory(new PropertyValueFactory<>("Message"));
       
//        System.out.println("after");

//     IServicedao dbloc = new servicedao();
//        try {
//            for (int j = 0; j < dbloc.DisplayAllService().size(); j++) {
//               
////                data_nom_bloc.add(dbloc.DisplayAllBlocs().get(j).getNom());
//
//                
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(serviceFrag.class.getName()).log(Level.SEVERE, null, ex);
//        }
        //////////////////////find/////////////////////
           find.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
            data = FXCollections.observableArrayList();
           
            demandedao dao = new demandedao();
            
            
            String rech= find.getText();
            
           
        try {
          for(int i = 0;i<dao.findreclamationBylieu(rech).size();i++)
          {  
              data.add(dao.findreclamationBylieu(rech).get(i)); 
          }                           
        table.setItems(data); 
            
        } catch (SQLException ex) {
            Logger.getLogger(DemandeFrag.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
             table.getSelectionModel().clearSelection();
             
            }
        });
        
    }
    
}
