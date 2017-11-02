package main;

import activities.Authentification;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.kairos.components.MaterialButton;
import org.kairos.core.ActivityFactory;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane(); // Create root pane
        primaryStage.setScene(new Scene(root)); // Set the scene in the stage

        primaryStage.setResizable(false);

        // this object represent the stack  of activities  in your application
        ActivityFactory factory = new ActivityFactory(primaryStage);

        // set the material design style in your applicat
        primaryStage.getScene().getStylesheets().add(MaterialButton.class.getResource("controls.css").toExternalForm());
        primaryStage.getScene().getStylesheets().add(getClass().getResource("../css/styles.css").toExternalForm());

        factory.startActivity(Authentification.class); // start the activity
        
        primaryStage.show();
    }

}
