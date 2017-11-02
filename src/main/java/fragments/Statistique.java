/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fragments;

import activities.AreaChartProd;
import activities.BarChartService;
import activities.PieChartProd;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.kairos.core.ActivityFactory;
import org.kairos.core.Fragment;

/**
 *
 * @author Wael
 */
public class Statistique extends Fragment implements Initializable {

    @FXML
    public void getPieChartProd() {

        Stage primaryStage = new Stage();
        StackPane root = new StackPane(); // Create root pane
        primaryStage.setScene(new Scene(root)); // Set the scene in the stage

        // this object represent the stack  of activities  in your application
        ActivityFactory factory = new ActivityFactory(primaryStage);

        factory.startActivity(PieChartProd.class); // start the activity
        primaryStage.show();
    }

    @FXML
    public void getAreaChartProd() {

        Stage primaryStage = new Stage();
        StackPane root = new StackPane(); // Create root pane
        primaryStage.setScene(new Scene(root)); // Set the scene in the stage

        // this object represent the stack  of activities  in your application
        ActivityFactory factory = new ActivityFactory(primaryStage);

        factory.startActivity(AreaChartProd.class); // start the activity
        primaryStage.show();
    }

    @FXML
    public void getBarChartProd() {

        Stage primaryStage = new Stage();
        StackPane root = new StackPane(); // Create root pane
        primaryStage.setScene(new Scene(root)); // Set the scene in the stage

        // this object represent the stack  of activities  in your application
        ActivityFactory factory = new ActivityFactory(primaryStage);

        factory.startActivity(BarChartService.class); // start the activity
        primaryStage.show();
    }

    @Override
    public void onCreateView(FXMLLoader loader) {
        loader.setLocation(getClass().getResource("/fxml/testchart.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(Statistique.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
