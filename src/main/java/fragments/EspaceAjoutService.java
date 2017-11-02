package fragments;

import activities.Authentification;
import dao.demandedao;
import dao.servicedao;

import entities.Membre;
import entities.demande;
import entities.Service;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.kairos.components.MaterialButton;
import org.kairos.core.Fragment;

public class EspaceAjoutService extends Fragment {

    @Override
    public void onCreateView(FXMLLoader loader) {
        loader.setLocation(getClass().getResource("../fxml/MonEspacemesServicesCrud.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(AppelsOffreMembre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    MaterialButton btnAjouter;
    @FXML
    MaterialButton btnEdit;
         @FXML
  MaterialButton  actualiser;
    
    @FXML
    TextArea description;
    @FXML
   TextField  categorie1;
    @FXML
    TextField nom1;
    
    
    @FXML
    public void Ajouter(){
        
        
        
//      HashMap<String, Object> args = getArguments();
//
//Membre bl = new Membre();
//      
//      bl.setId((int) args.get("x"));
//   service c1=new service();
//   c1.setId_service((int) args.get("y"));
//               System.out.println("date:"+txtDateFin.getValue());
//                  System.out.println(txtLieu.getText());
//                     System.out.println("aa1:"+args.get("x"));
//                      System.out.println("aa2:"+args.get("y"));
//                      String d1=""+txtDateDebut.getValue();
//                        String d2=""+txtDateFin.getValue();
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Ajout");
        alert.setContentText("vous etes sur de ses paramétres ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Membre m = new Membre();
            m.setId(1);
        Service de=new Service(5, nom1.getText(), categorie1.getText(), description.getText());
                servicedao ch = new servicedao();
        ch.insertService(de,m);   
        } else{
            
        }
 

    }
    
    
                 @FXML
    void act() {
          btnAjouter.setVisible(false);
          categorie1.setEffect(getEffect());
HashMap<String, Object> args = getArguments();
nom1.setText((String) args.get("y"));
description.setText((String) args.get("z"));
categorie1.setText((String) args.get("a"));


      
//      bl.setId((int) args.get("x"));
//   service c1=new service();
//   c1.setId_service((int) args.get("y"));
//               System.out.println("date:"+txtDateFin.getValue());
//                  System.out.println(txtLieu.getText());
//                     System.out.println("aa1:"+args.get("x"));
//                      System.out.println("aa2:"+args.get("y"));
//                      String d1=""+txtDateDebut.getValue();
//                        String d2=""+txtDateFin.getValue();

     

    }
    @FXML
    void Edit(){
//    
////    @FXML
////    private TextField fournisseur;
////     @FXML
////    private TextArea service;
//     @FXML
//    private DatePicker txtDateDebut;
//     @FXML
//    private DatePicker txtDateFin;
//    
//     @FXML
//    private TextField txtLieu;
//     @FXML
//    private TextArea txtRemarque;
//      @FXML
//    private MaterialButton btnAjouter;
//    @FXML
//    private Label label1;
//
//    
//    @FXML
//            public void builddata(){
//
//            
//    HashMap<String, String> args = getArguments();
//
////        imageView.setX(args.get("x"));
////        imageView.setY(args.get("y"));
////    
////    fournisseur.setAccessibleHelp(args.get("x"));
//        System.out.println(args.get("x"));
//        System.out.println(args.get("y"));
////     service.setAccessibleHelp(args.get("y");
//    
//}
//            
//           @FXML
//    void AddDemande() {
//HashMap<String, Object> args = getArguments();
//
//Membre bl = new Membre();
//      
//      bl.setId((int) args.get("x"));
//   service c1=new service();
//   c1.setId_service((int) args.get("y"));
////               System.out.println("date:"+txtDateFin.getValue());
////                  System.out.println(txtLieu.getText());
////                     System.out.println("aa1:"+args.get("x"));
////                      System.out.println("aa2:"+args.get("y"));
//                      String d1=""+txtDateDebut.getValue();
//                        String d2=""+txtDateFin.getValue();
//        demande de=new demande(bl, c1, d1, d2, txtLieu.getText(), txtRemarque.getText());
//               IDemandedao cha = new demandedao();
//        cha.insertdemande(de);
////      
//
//    }
        
        
HashMap<String, Object> args = getArguments();
Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
alert.setTitle("Confirmation");
alert.setHeaderText("Edition");
alert.setContentText("vous étes sur des ses modifications ?");
Optional<ButtonType> result = alert.showAndWait();
if (result.get() == ButtonType.OK){
    try {
        Service de=new Service((int)args.get("x"), nom1.getText(),description.getText(), categorie1.getText());
        servicedao cha = new servicedao();
        cha.updateService(de);
    } catch (SQLException ex) {
        Logger.getLogger(EspaceAjoutService.class.getName()).log(Level.SEVERE, null, ex);
    }
}else{
    
}
            
}}
