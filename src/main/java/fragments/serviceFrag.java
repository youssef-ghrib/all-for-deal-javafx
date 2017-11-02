/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fragments;

import activities.consulter;
import activities.mail;
import dao.servicedao;
import entities.Service;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import org.kairos.core.Fragment;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.kairos.components.MaterialButton;
import org.kairos.core.ActivityFactory;
import org.kairos.core.FragmentTransaction;
import org.kairos.core.Intent;

public class serviceFrag extends Fragment implements Initializable  {
    @Override
   public void onCreateView(FXMLLoader loader) {
        loader.setLocation(getClass().getResource("../fxml/service.fxml"));
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
    
    private ObservableList<Service> data;
    
    @FXML
    TableView<Service> table;
    @FXML
    private TableColumn<Service, Integer> id;
    @FXML
    private TableColumn<Service, String> nom;
    @FXML
    private TableColumn<Service, String> categorie;
     @FXML
    private TableColumn<Service, String> description;
    @FXML
    private TableColumn<Service, String> fournisseur;
     
//    @FXML
//    private TableColumn<Membre, String> password;
    @FXML
    private TextField find;
    
    @FXML
    public void locate()throws  IllegalAccessException, InstantiationException{
        try{
         HashMap<String, Object> args = new HashMap<>();
//     int ind =table.getSelectionModel().getSelectedIndex();
//             args.put("x", data.get(ind).getId_service());
//                    args.put("y", data.get(ind).getId_fournisseur().getId());

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    Fragment fragment = mapsss.class.newInstance();
                    fragment.setArguments(args);
                    transaction.replace("content", fragment);
                    transaction.commit();
        }catch (Exception e){
            System.out.println("Google Map");
        }
    
        
        
    }
    
    @FXML
    public void buildData() {
        data = FXCollections.observableArrayList();
        try {
            servicedao dd = new servicedao();
            for (int i = 0; i < dd.DisplayAllService().size(); i++) {
                
                data.add(dd.DisplayAllService().get(i));
              
            
            }
        id.setCellValueFactory(new PropertyValueFactory<>("id_service"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        categorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
         fournisseur.setCellValueFactory(new PropertyValueFactory<>("id_fournisseur"));
         table.setItems(data);
      
        } catch (Exception e) {
            System.out.println("Error on Building Data");
        }
    }
    
@FXML 
 public void DemandeBUTTON() throws  IllegalAccessException, InstantiationException
 {
     HashMap<String, Object> args = new HashMap<>();
     int ind =table.getSelectionModel().getSelectedIndex();
             args.put("x", data.get(ind).getId());
                    args.put("y", data.get(ind).getId_fournisseur().getId());

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    Fragment fragment = DemandeAjoutC.class.newInstance();
                    fragment.setArguments(args);
                    transaction.replace("content", fragment);
                    transaction.commit();

 }
 
 @FXML 
 public void reclamation_button() throws  IllegalAccessException, InstantiationException
 {
     HashMap<String, Object> args = new HashMap<>();
     int ind =table.getSelectionModel().getSelectedIndex();
             args.put("x", data.get(ind).getId());
                    args.put("y", data.get(ind).getId_fournisseur().getId());

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    Fragment fragment = Reclamation.class.newInstance();
                    fragment.setArguments(args);
                    transaction.replace("content", fragment);
                    transaction.commit();

 }
 
 
 @FXML 
 public void contacter_message() throws  IllegalAccessException, InstantiationException
 {
     HashMap<String, Object> args = new HashMap<>();
     int ind =table.getSelectionModel().getSelectedIndex();
             args.put("x", data.get(ind).getId());
                    args.put("y", data.get(ind).getId_fournisseur().getId());

                    Intent intent = new Intent(args, mail.class);
                    
                    Stage stage = new Stage();

        StackPane root = new StackPane(); // Create root pane
        stage.setScene(new Scene(root)); // Set the scene in the stage

        stage.setResizable(false);

        // this object represent the stack  of activities  in your application
        ActivityFactory factory = new ActivityFactory(stage);

                   // set the material design style in your applicat
        stage.getScene().getStylesheets().add(MaterialButton.class.getResource("controls.css").toExternalForm());
        stage.getScene().getStylesheets().add(getClass().getResource("../css/styles.css").toExternalForm());

        factory.startActivity(intent); // start the activity
        
        stage.show();
    
     
     
     
 }

    
        @FXML
    public void SupprimerMembre()
    {
        int ind =table.getSelectionModel().getSelectedIndex();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Suppression de Service");
        alert.setContentText("Vous etes sure de supprimer le membre "+data.get(ind).getNom());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
             servicedao dao = new servicedao();

                try {
                    int idB=  data.get(ind).getId();
                    
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
    public void ConsulterMembre()
    {
 HashMap<String, String> args = new HashMap<>();
     int ind =table.getSelectionModel().getSelectedIndex();
        System.out.println(data.get(ind).getId_fournisseur().getNom());
         System.out.println(data.get(ind).getId_fournisseur().getAdresse());
                     args.put("x", data.get(ind).getId_fournisseur().getPrenom());
//                     args.put("y", data.get(ind).getId_fournisseur().getDateNaissance());
//                    System.out.println(""+data.get(ind).getId_fournisseur().getDateNaissance());
//                    System.out.println(""+data.get(ind).getId_fournisseur().getMail());
//                    System.out.println(""+data.get(ind).getId_fournisseur().getPrenom());
                     args.put("q", data.get(ind).getNom());
                     args.put("s", data.get(ind).getId_fournisseur().getMail());
                     args.put("d", data.get(ind).getCategorie());
                     args.put("f", data.get(ind).getDescription());
                    Intent intent = new Intent(args, consulter.class);
                    
                    Stage stage = new Stage();

        StackPane root = new StackPane(); // Create root pane
        stage.setScene(new Scene(root)); // Set the scene in the stage

        stage.setResizable(false);

        // this object represent the stack  of activities  in your application
        ActivityFactory factory = new ActivityFactory(stage);

                   // set the material design style in your applicat
        stage.getScene().getStylesheets().add(MaterialButton.class.getResource("controls.css").toExternalForm());
        stage.getScene().getStylesheets().add(getClass().getResource("../css/styles.css").toExternalForm());

        factory.startActivity(intent); // start the activity
        
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        id.setCellValueFactory(data -> data.getValue().getId());
//        nom.setCellValueFactory(data -> data.getValue().getNom());
//        prenom.setCellValueFactory(data -> data.getValue().getPrenom());
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        categorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
    fournisseur.setCellValueFactory(new PropertyValueFactory<>("fournisseur"));
       
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
           
            servicedao dao = new servicedao();
            
            
            String rech= find.getText();
            
           
        try {
          for(int i = 0;i<dao.findServiceByNom(rech).size();i++)
          {  
              data.add(dao.findServiceByNom(rech).get(i)); 
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
