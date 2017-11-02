/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activities;

import dao.MembreDao;
import entities.AppelOffre;
import entities.Service;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kairos.core.Activity;

/**
 *
 * @author Pato
 */
public class consulter extends Activity{
    
    
    VBox v;
    
    @FXML
    Label lblnom;
    
    @FXML
    Label lblDescription;
    
    @FXML
    Label lblcateg;
    
    @FXML
    Label lblnomf;
    
   
    

    
    @Override
    public void onCreate() {
        super.onCreate();
        setContentView(getClass().getResource("../fxml/consulter.fxml"));
        
        HashMap<String, String> extrasa = intent.getExtras();
       
        
        lblnom.setText(lblnom.getText()+extrasa.get("q"));
//        lblPrenom.setText(lblPrenom.getText()+extrasa.get("x"));
        lblnomf.setText(lblnomf.getText()+extrasa.get("q"));
        lblDescription.setText(lblDescription.getText()+extrasa.get("f"));
        lblcateg.setText(lblcateg.getText()+extrasa.get("d"));
//        lblage.setText(lblage.getText()+extrasa.get("y"));
//        lbmail.setText(lbmail.getText()+extrasa.get("s"));
        
    }
    
    public void fermer(ActionEvent event) {

        Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        stage.close();
    }
    
}



