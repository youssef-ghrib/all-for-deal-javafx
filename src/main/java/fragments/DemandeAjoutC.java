package fragments;

import dao.demandedao;
import dao.servicedao;
import entities.Membre;
import entities.demande;
import entities.Service;
import java.io.IOException;
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
import javafx.util.Duration;
import org.kairos.components.MaterialButton;
import org.kairos.core.Fragment;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class DemandeAjoutC extends Fragment  {

    @Override
    public void onCreateView(FXMLLoader loader) {
        loader.setLocation(getClass().getResource("../fxml/demandeAjout.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(AppelsOffreMembre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    
    
     @FXML
    public void ConsulterMembre()
    {
        try {
            
        
//        int ind =table.getSelectionModel().getSelectedIndex();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informations");
        alert.setHeaderText("Informations de Membre");
//        alert.setContentText("Le nom est :"+data.get(ind).getDatdebu()+"\n Description : "+data.get(ind).getDatefin()+"\n Categorie : "+data.get(ind).getLieu()+
//        "\n ID : "+data.get(ind).getId_service()+" \n nom fournisseur:"+data.get(ind).getId_service().getNom());
        
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
                alert.close();
        } 
        } catch (Exception e) {
            System.out.println("consulter error");
        }
    }
    
    
//    @FXML
//    private TextField fournisseur;
//     @FXML
//    private TextArea service;
     @FXML
    private DatePicker txtDateDebut;
     @FXML
    private DatePicker txtDateFin;
    
     @FXML
    private TextField txtLieu;
     @FXML
    private TextArea txtRemarque;
      @FXML
    private MaterialButton btnAjouter;
    @FXML
    private Label label1;
    
 

    private boolean validateFields(){
        if( txtDateDebut.getEditor().getText().isEmpty()){
            
            Alert alert=new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Validate Fields");
                    alert.setHeaderText(null);
                    alert.setContentText("entrer la date Debut correctement");
                    alert.showAndWait();
                    return false;
        }
        if( txtDateFin.getEditor().getText().isEmpty()){
            
            Alert alert=new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Validate Fields");
                    alert.setHeaderText(null);
                    alert.setContentText("entrer le la Date Fin correctement");
                    alert.showAndWait();
                    return false;
        }
        if( txtLieu.getText().isEmpty()){
            
            Alert alert=new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Validate Fields");
                    alert.setHeaderText(null);
                    alert.setContentText("entrer le lieu correctement");
                    alert.showAndWait();
                    return false;
        }
        if( txtRemarque.getText().isEmpty()){
            
            Alert alert=new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Validate Fields");
                    alert.setHeaderText(null);
                    alert.setContentText("entrer la  Remaque correctement");
                    alert.showAndWait();
                    return false;
        }
        
        
        
        
        return true;
    }
    
    
    
    
    
    
    
    @FXML
            public void builddata(){
                
            
    HashMap<String, String> args = getArguments();

//        imageView.setX(args.get("x"));
//        imageView.setY(args.get("y"));
//    
//    fournisseur.setAccessibleHelp(args.get("x"));
        System.out.println(args.get("x"));
        System.out.println(args.get("y"));
//     service.setAccessibleHelp(args.get("y");
    
}
            
           @FXML
    void AddDemande() {
             
                   
              
HashMap<String, Object> args = getArguments();

Membre bl = new Membre();
      
      bl.setId((int) args.get("x"));
   Service c1=new Service();
   c1.setId((int) args.get("y"));
//               System.out.println("date:"+txtDateFin.getValue());
//                  System.out.println(txtLieu.getText());
//                     System.out.println("aa1:"+args.get("x"));
//                      System.out.println("aa2:"+args.get("y"));
                      String d1=""+txtDateDebut.getValue();
                        String d2=""+txtDateFin.getValue();
        demande de=new demande(bl, c1, d1, d2, txtLieu.getText(), txtRemarque.getText());
        try {
       if( validateFields()){
               demandedao cha = new demandedao();
        cha.insertdemande(de);
        NotificationType type = NotificationType.SUCCESS;
                TrayNotification tray = new TrayNotification();
                tray.setTitle("Bravo");
                tray.setMessage("Demande enregistré");
                tray.setNotificationType(type);
                tray.setAnimationType(AnimationType.POPUP);
                tray.showAndDismiss(Duration.seconds(10));
    
                
               
          
 
                
                
       }
        } catch (Exception e) {
            System.out.println("error demande Ajout");
           }
       
    }

            
}
