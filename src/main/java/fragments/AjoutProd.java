/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fragments;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.kairos.core.Fragment;
import dao.CategorieDao;
import dao.ProduitDao;
import dao.SousCategorieDao;
import dao.imageDao;

import entities.Produit;

/**
 *
 * @author daly
 */
public class AjoutProd extends Fragment implements Initializable {
    
    File file;

    private ObservableList<String> data;
    @FXML
    private Button btnajouter;
    @FXML
    private Button btnchoisir;
    @FXML
    private TextField nom;
    @FXML
    private TextField prix;
    @FXML
    private TextField reduction;
    @FXML
    private TextField images;
    @FXML
    private TextField couleur;
  
    @FXML
    private ComboBox<String> sous_categorie;
    @FXML
    private ComboBox<String> categorie;

    @Override
    public void onCreateView(FXMLLoader loader) {
        loader.setLocation(getClass().getResource("/fxml/ajoutprod.fxml"));

        try {
            loader.load();

        } catch (IOException ex) {
            Logger.getLogger(AjoutProd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void insertProduit() throws FileNotFoundException, IOException {
        if (validateFields()) {
            FileInputStream inputStream = null;
                 String noms = nom.getText();
                float prixs = Float.parseFloat(prix.getText());
                String couleurs = couleur.getText();
                String categ = categorie.getValue();
                String souscateg = sous_categorie.getValue();
                CategorieDao cdao = new CategorieDao();
                SousCategorieDao sdao = new SousCategorieDao();
                int a = cdao.findCategoriebyNOm(categ);
                int b = sdao.findSousCategoriebyNOm(souscateg);
                int reductions = Integer.parseInt(reduction.getText());
                Produit S = new Produit(noms, prixs, reductions, couleurs);
                ProduitDao dao = new ProduitDao();
                dao.insertProduitd(S,b);
                System.out.println(S.getId());
                imageDao imgDao =new  imageDao();
                
//                
                InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(file);
            os = new FileOutputStream(new File("C:/wamp/www/web/web/img/produits/"+file.getName()));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        
                
                
                imgDao.insertImage(dao.LastRowADDed(), file.getName());
                
              
                
               
            
            JOptionPane j;
            j = new JOptionPane();
            j.showMessageDialog(null, "Produit ajouté avec succes ", "Information", JOptionPane.INFORMATION_MESSAGE);
            refresh();

        }
    }}

    @FXML
    void refresh() {
        nom.setText(null);
        prix.setText(null);
        couleur.setText(null);
        reduction.setText(null);
        btnchoisir.setText("saisissez l'image du produit svp");
     categorie.getSelectionModel().clearSelection();
 sous_categorie.getSelectionModel().clearSelection();
    }

    public void choisirAction() {
      
            Stage primaryStage = new Stage();
            Group root = new Group();
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("All Images", "*.*");
            fileChooser.getExtensionFilters().add(extFilter);
            file = fileChooser.showOpenDialog(primaryStage);
            if(file!=null){
           btnchoisir.setText("image insérée");
            }
    }

    public void cboxcAction() {
      List<String> li=new ArrayList<>();  
  
      String a = categorie.getSelectionModel().getSelectedItem();
      ProduitDao pdao = new ProduitDao();
        CategorieDao c = new CategorieDao();
        int v = c.findCategoriebyNOm(a);
        sous_categorie.getItems().clear();
       li = pdao.souscat(v);
 
        remplircombosouscateg(li);
    }

    public void cboxscAction() {

    }

    public void remplircombo() {
        ProduitDao pdao = new ProduitDao();
        List<String> li = new ArrayList<>();
        li = pdao.findcateg();
        final ObservableList<String> list = FXCollections.<String>observableList(li);
        categorie.promptTextProperty().setValue("");
        categorie.getItems().addAll(list);
    }
@FXML
public void viderSC(MouseEvent m)
{}


    public void remplircombosouscateg(List<String> li) {
    
        final ObservableList<String> list = FXCollections.<String>observableList(li);

        sous_categorie.promptTextProperty().setValue("");
        sous_categorie.getItems().addAll(list);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        remplircombo();
    }

    private boolean validateFields() {
        if (nom.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Fields");
            alert.setHeaderText(null);
            alert.setContentText("saisissez le nom du produit svp");
            alert.showAndWait();
            return false;
        }
        if (!validatee(prix.getText())) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Fields");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un nombre dans le champs prix");
            alert.showAndWait();
            return false;

        }
        if (((prix.getText()) == "")) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Fields");
            alert.setHeaderText(null);
            alert.setContentText("saisissez le nom du produit svp");
            alert.showAndWait();
            return false;
        }

        if (reduction.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Fields");
            alert.setHeaderText(null);
            alert.setContentText("saisissez le taux de reduction du produit svp");
            alert.showAndWait();
            return false;
        }
        if (file ==null)
        {     Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Fields");
            alert.setHeaderText(null);
            alert.setContentText("saisissez l'image de produit svp");
            alert.showAndWait();
            return false; }

        if (!validate(reduction.getText())) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Fields");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un nombre dans le champs réduction");
            alert.showAndWait();
            return false;

        }
        if ((couleur.getText().isEmpty())) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Fields");
            alert.setHeaderText(null);
            alert.setContentText("saisissez la couleur du produit svp");
            alert.showAndWait();
            return false;
        }
        if (categorie.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Fields");
            alert.setHeaderText(null);
            alert.setContentText("choisissez la categorie du produit svp");
            alert.showAndWait();
            return false;
        }

        if (sous_categorie.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Fields");
            alert.setHeaderText(null);
            alert.setContentText("choisissez la sous categorie du produit svp");
            alert.showAndWait();
            return false;
        }

        if ((btnchoisir.getText() != "image insérée")) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Fields");
            alert.setHeaderText(null);
            alert.setContentText("saisissez l'image du produit svp");
            alert.showAndWait();
            return false;
        }
    else {
        }
        return true;
    }

    private boolean validate(String text) {
        return text.matches("[0-9]*");
    }

    private boolean validatee(String text) {
        return text.matches("[0-9_._,]*");
    }
}
