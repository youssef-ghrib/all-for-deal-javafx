/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fragments;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;
import org.kairos.core.Fragment;

/**
 *
 * @author Pato
 */
public class mapsss extends Fragment implements Initializable{

 @FXML
    private WebView webviex_;
    @Override
   public void onCreateView(FXMLLoader loader) {
        loader.setLocation(getClass().getResource("../fxml/mapsfxml.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(AppelsOffreMembre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            
        
        webviex_.getEngine().load("https://www.google.fr/maps/place/Esprit+Pr%C3%A9pa/@36.8987544,10.1895625,19z/data=!4m2!3m1!1s0x0000000000000000:0xcb5e71cbf91ec526?hl=en");
        } catch (Exception e) {
            System.out.println("GoogleMapppanes");
        }
    }    


   
}