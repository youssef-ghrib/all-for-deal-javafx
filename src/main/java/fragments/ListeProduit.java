/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fragments;

import activities.CommentRatingProd;
import activities.ModifProd;
import static fragments.SupprimerProd.supprim;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.kairos.components.MaterialButton;
import org.kairos.core.ActivityFactory;
import org.kairos.core.Fragment;
import dao.ProduitDao;
import entities.Produit;
import entities.Sous_categorie;

/**
 *
 * @author daly
 */
public class ListeProduit extends Fragment implements Initializable{
public static ListeProduit supprim;
 private ObservableList<Produit> data;
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
     @FXML
    private TextField find;
    @FXML
    private Button btnajouter;
    @FXML
    private Button btnconsulter;
    @Override
    public void onCreateView(FXMLLoader loader) {

            loader.setLocation(getClass().getResource("/fxml/ListeProduit1.fxml"));
                    try {
            loader.load();
            supprim=this;
        } catch (IOException ex) {
            Logger.getLogger(ListeProduit.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    
    }
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
           sous_categorie.setCellValueFactory(new PropertyValueFactory<>("sous_categorie"));
           dateid.setCellValueFactory(new PropertyValueFactory<>("date_lancement"));

            tabid.setItems(data);
        } catch (Exception e) {
            System.out.println("Error on Building Data");
        }
}
 
 
 
 
 

 public void filtrer(){
     try {
         data.clear();
         ProduitDao dao = new ProduitDao();
         
         
         String rech= find.getText();
         String rech1= find.getText();
         
         
         for(int i = 0;i<dao.findProduitByColorAndNom(rech1,rech).size();i++)
         {
             data.add(dao.findProduitByColorAndNom(rech,rech1).get(i));
         }
         
         
         tabid.setItems(data);
     } catch (SQLException ex) {
         Logger.getLogger(ListeProduit.class.getName()).log(Level.SEVERE, null, ex);
     }
 }
 
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    tabid.setRowFactory((TableView<Produit> p) -> {
            final TableRow<Produit> row = new TableRow<>();
            row.setOnMousePressed((MouseEvent t) -> {
                 btnconsulter.setDisable(false);
                btnajouter.setDisable(false);
            });
            return row;
        });
        buildData();   

  }

   public void detailProduit() {
    Stage primaryStage =new Stage();
        StackPane root = new StackPane(); // Create root pane
        primaryStage.setScene(new Scene(root)); // Set the scene in the stage

        // this object represent the stack  of activities  in your application
        ActivityFactory factory = new ActivityFactory(primaryStage);

        // set the material design style in your applicat
        primaryStage.getScene().getStylesheets().add(MaterialButton.class.getResource("controls.css").toExternalForm());
ProduitDao pdao=new ProduitDao();
boolean x=false;
//if (pdao.verifierComment(tabid.getSelectionModel().getSelectedItem().getId(),6)>0)
{x=true;

}
        factory.startActivity(CommentRatingProd.class); // start the activity
        primaryStage.setResizable(false);
        primaryStage.show();
           
    }
     public  Produit getid(){

        Produit a=tabid.getSelectionModel().getSelectedItem();
       
            return a;
    }
        
}