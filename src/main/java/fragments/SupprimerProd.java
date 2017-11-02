/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fragments;

import activities.ModifProd;
import activities.Authentification;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.SpringLayout;
import org.kairos.components.MaterialButton;
import org.kairos.core.ActivityFactory;
import org.kairos.core.Fragment;
import dao.ProduitDao;
import entities.Produit;

/**
 *
 * @author daly
 */
public class SupprimerProd extends Fragment implements Initializable {
public static SupprimerProd supprim;

    private ObservableList<Produit> data;
    @FXML
 TableView<Produit> tab;
        @FXML
    private TableColumn<Produit, String> nom;
    @FXML
    private TableColumn<Produit, Number> prix;
         @FXML
    private TableColumn<Produit, Number> reduction;
         @FXML
    private TableColumn<Produit, Number> id;
    @FXML
    private TableColumn<Produit, String> couleur;
    @FXML
    private TableColumn<Produit, String> categorieid;

    @FXML
    private TableColumn<Produit, Number> points;
    @FXML
    private TableColumn<Produit, String> sous_categorie;
    @FXML
    private TableColumn<Produit, String> datelanc;
 
    @FXML
    private Button btnsupprimer;
    @FXML
    private Button btnmodifier;

    public  void buildData() {
        data = FXCollections.observableArrayList();
        try {
            ProduitDao pDao = new ProduitDao();
            for (int i = 0; i < pDao.DisplayAllProduit().size(); i++) {
                data.add(pDao.DisplayAllProduit().get(i));
//                System.out.println(pDao.DisplayAllProduit().get(i).getId());
            }
           
          
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
         couleur.setCellValueFactory(new PropertyValueFactory<>("couleur"));
          categorieid.setCellValueFactory(new PropertyValueFactory<>("categorie"));
           reduction.setCellValueFactory(new PropertyValueFactory<>("reduction"));
           points.setCellValueFactory(new PropertyValueFactory<>("points"));
           sous_categorie.setCellValueFactory(new PropertyValueFactory<>("sous_categorie"));
           datelanc.setCellValueFactory(new PropertyValueFactory<>("date_lancement"));
           id.setCellValueFactory(new PropertyValueFactory<>("id"));
            tab.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
}
    
    @Override
    public void onCreateView(FXMLLoader loader) {

            loader.setLocation(getClass().getResource("/fxml/Supprimerproduit.fxml"));
                    try {
            loader.load();
            buildData();
            supprim=this;
        } catch (IOException ex) {
            Logger.getLogger(SupprimerProd.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    
    }

@FXML
  public void deleteProduit()
    {
        int ind =tab.getSelectionModel().getSelectedIndex();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Suppression de produit");
        alert.setContentText("Vous etes sure de supprimer ce produit "+data.get(ind).getNom());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
             
            ProduitDao dao = new ProduitDao();

                try {
                   int idB=  data.get(ind).getId();
                   dao.deleteImage(idB);
                    dao.deleteCommentaire(idB);
                    dao.deleteEvaluation(idB);
                   dao.deleteProduit(idB);
                    buildData();
                    
                } catch (Exception ex) {
                    Logger.getLogger(SupprimerProd.class.getName()).log(Level.SEVERE, null, ex);
                }
        } 
}
    
  
  


  

    @Override
    public void initialize(URL location, ResourceBundle resources) {
                        tab.setRowFactory((TableView<Produit> p) -> {
            final TableRow<Produit> row = new TableRow<>();
            row.setOnMousePressed((MouseEvent t) -> {
                 btnsupprimer.setDisable(false);
                btnmodifier.setDisable(false);
            });
            return row;
        });
              
        buildData();
    }

    public void modifierProduit() {

     

    Stage primaryStage =new Stage();
        StackPane root = new StackPane(); // Create root pane
        primaryStage.setScene(new Scene(root)); // Set the scene in the stage

        // this object represent the stack  of activities  in your application
        ActivityFactory factory = new ActivityFactory(primaryStage);

        // set the material design style in your applicat
        primaryStage.getScene().getStylesheets().add(MaterialButton.class.getResource("controls.css").toExternalForm());

        factory.startActivity(ModifProd.class); // start the activity
        primaryStage.show();
           
    }

    public  Produit getid(){

        Produit a=tab.getSelectionModel().getSelectedItem();
       
            return a;
    }


}
