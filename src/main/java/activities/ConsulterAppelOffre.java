package activities;

import entities.AppelOffre;
import fragments.AppelsOffreMembre;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kairos.core.Activity;

public class ConsulterAppelOffre extends Activity {
    
    VBox v;
    
    @FXML
    Label lblSujet;
    
    @FXML
    Label lblDescription;
    
    @FXML
    Label lblDateDebut;
    
    @FXML
    Label lblDateFin;
    
    @FXML
    Label lblLieu;
    
    @FXML
    Label lblRemarques;
    
    @Override
    public void onCreate() {
        super.onCreate();
        setContentView(getClass().getResource("../fxml/consulterAppelOffre.fxml"));
        
        HashMap<String, Object> extras = intent.getExtras();
        AppelOffre a = (AppelOffre) extras.get("a");
        
        lblSujet.setText(lblSujet.getText()+a.getSujet());
        lblDescription.setText(lblDescription.getText()+a.getDescription());
        lblDateDebut.setText(lblDateDebut.getText()+a.getDateDebut());
        lblDateFin.setText(lblDateFin.getText()+a.getDateFin());
        lblLieu.setText(lblLieu.getText()+a.getLieu());
        lblRemarques.setText(lblRemarques.getText()+a.getRemarques());
    }
    
    public void fermer(ActionEvent event) {

        Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        stage.close();
    }
    
}
