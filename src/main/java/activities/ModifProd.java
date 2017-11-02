/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activities;

import fragments.SupprimerProd;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import org.kairos.core.Activity;
import dao.ProduitDao;
import entities.Produit;


/**
 *
 * @author daly
 */
public class ModifProd extends  Activity{
    
    @FXML
private Button btnvalider;
@FXML
private TextField nom;
@FXML
private TextField prix;
@FXML
private TextField reduction;

@FXML
private TextField couleur;



    @Override
    public void onCreate() {
           super.onCreate();
           
        
  
       
        Produit m=SupprimerProd.supprim.getid();
        System.out.println(m);
      setContentView(getClass().getResource("/fxml/modifprod.fxml"));
        nom.setText(m.getNom());
        prix.setText(String.valueOf(m.getPrix()));
        couleur.setText((m.getCouleur()));

        reduction.setText(Integer.toString(m.getReduction()));
       
    }

    
        
     public void validerProduit(ActionEvent event) {
         int id=SupprimerProd.supprim.getid().getId();
         System.out.println(id);
       ProduitDao pdao=new ProduitDao();
       Produit m=new Produit();
     
      String Nom = nom.getText();
        float Prix = Float.parseFloat(prix.getText());
        int Reduction=Integer.parseInt(reduction.getText());
  m.setId(id);
       
        String Couleur= couleur.getText();
     m.setNom(Nom);
     m.setCouleur(Couleur);
     m.setReduction(Reduction);
     m.setPrix(Prix);

pdao.updateProduit(m);
  JOptionPane j;
        j = new JOptionPane();
j.showMessageDialog(null, "Produit modifié avec succes ", "Informations", JOptionPane.INFORMATION_MESSAGE);
  

      

 ((Node)(event.getSource())).getScene().getWindow().hide();
       
SupprimerProd.supprim.buildData();

        }

     }    
    
    
      
