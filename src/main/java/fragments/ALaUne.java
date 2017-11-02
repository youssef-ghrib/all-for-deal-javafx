package fragments;

import com.jfoenix.controls.JFXSpinner;
import dao.ProduitDao;
import dao.imageDao;
import entities.Image;
import entities.Produit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import org.kairos.core.Fragment;
import org.kairos.core.FragmentTransaction;

public class ALaUne extends Fragment {

    imageDao imageDao = new imageDao();
    ProduitDao produitDao = new ProduitDao();

    @FXML
    TilePane tilePane;

    @FXML
    VBox vbox;

    @Override
    public void onCreateView(FXMLLoader loader) {
        try {
            loader.setLocation(getClass().getResource("../fxml/aLaUne.fxml"));
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ALaUne.class.getName()).log(Level.SEVERE, null, ex);
        }

        // start displaying the loading indicator at the Application Thread
        JFXSpinner spinner = new JFXSpinner();
        tilePane.getChildren().add(spinner);

        vbox.getChildren().remove(tilePane);
        StackPane stack = new StackPane();
        StackPane.setAlignment(spinner, Pos.CENTER);
        stack.getChildren().add(spinner);
        vbox.getChildren().add(stack);

        // loads the items at another thread, asynchronously
        new Thread(() -> {
            List<ImageView> images = new ArrayList<>();
            List<Produit> produits = produitDao.getBestSellers();

            for (Produit p : produits) {

                String url = getMainImage(p);
                javafx.scene.image.Image image = new javafx.scene.image.Image(url, 180, 180, true, true, true);
                ImageView imageView = new ImageView(image);

                imageView.setPickOnBounds(true);

                imageView.setPreserveRatio(true);

                imageView.setOnMouseClicked((MouseEvent event) -> {
                    try {
                        System.out.println("clickable image indeed :D");

                        Bounds boundsInParent = imageView.localToParent(imageView.getBoundsInLocal());
                        double xInParent = boundsInParent.getMinX();
                        double yInParent = boundsInParent.getMinY();

                        HashMap<String, Object> args = new HashMap<>();

                        args.put("produit", p);
                        args.put("img", image);
                        args.put("x", xInParent);
                        args.put("y", yInParent);

                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        Fragment fragment = ConsulterProduit.class.newInstance();
                        fragment.setArguments(args);
                        transaction.replace("content", fragment);
                        transaction.commit();

                    } catch (InstantiationException | IllegalAccessException ex) {
                        Logger.getLogger(ALaUne.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });
                images.add(imageView);
            }

            Platform.runLater(() -> {
                vbox.getChildren().remove(stack); // stop displaying the loading indicator
                vbox.getChildren().add(tilePane);
                tilePane.getChildren().addAll(images);
            });
        }).start();
    }

    public String getMainImage(Produit p) {

        List<Image> images = imageDao.findByProduct(p);
        final String imageURI = new File("C://wamp/www/web/web/img/produits/" + images.get(0).getImageName()).toURI().toString();
        return imageURI;
    }
}
