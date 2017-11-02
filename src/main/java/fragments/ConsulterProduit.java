package fragments;

import activities.Application;
import entities.Produit;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.kairos.components.MaterialButton;
import org.kairos.core.Fragment;
import org.kairos.core.FragmentTransaction;
import org.kairos.core.Intent;

public class ConsulterProduit extends Fragment {

    @FXML
    AnchorPane anchor;

    @FXML
    VBox vbox;

    @FXML
    VBox buttons;

    @FXML
    Label lblNom;

    @FXML
    Label lblFournisseur;

    @FXML
    Label lblPrix;

    @FXML
    Label lblPoints;

    @FXML
    Label lblDate;

    @FXML
    Label lblCategorie;

    @FXML
    MaterialButton btnAdd;

    public ConsulterProduit() {

    }

    @Override
    public void onCreateView(FXMLLoader loader) {
        try {
            loader.setLocation(getClass().getResource("../fxml/consulterProduit.fxml"));
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ALaUne.class.getName()).log(Level.SEVERE, null, ex);
        }

        HashMap<String, Object> args = getArguments();

        Produit p = (Produit) args.get("produit");
        Image image = (Image) args.get("img");

        ImageView imageView = new ImageView(image);

        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);

        imageView.setX((Double) args.get("x"));
        imageView.setY((Double) args.get("y"));

        lblNom.setText(p.getNom());
        lblFournisseur.setText("de " + p.getFournisseur().getNom());
        lblPrix.setText("Prix: " + p.getPrix() + " (Réduction " + p.getReduction() + " %)");
        lblPoints.setText("Points: " + p.getPoints());
        lblDate.setText("Date lancement: " + p.getDate_lancement());
        lblCategorie.setText("Catégorie: " + p.getSous_categorie().getNom());

        anchor.getChildren().add(imageView);

        TranslateTransition ttImage = new TranslateTransition(Duration.seconds(1), imageView);
        ttImage.setToX(40 - (Double) args.get("x"));
        ttImage.setToY(40 - (Double) args.get("y"));
        ttImage.play();

        Bounds boundsInParent = buttons.localToParent(buttons.getBoundsInLocal());
        double xInParent = boundsInParent.getMinX();

        TranslateTransition ttButtons = new TranslateTransition(Duration.seconds(1), buttons);
        ttButtons.setFromX(xInParent + 200);
        ttButtons.setToX(xInParent);
        ttButtons.play();

        ScaleTransition st = new ScaleTransition(Duration.seconds(1), imageView);
        st.setToX(1.4);
        st.setToY(1.4);
        st.play();

        FadeTransition ft = new FadeTransition(Duration.seconds(2), vbox);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }

    @FXML
    public void addToCart() {

        try {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            Fragment fragment = Panier.class.newInstance();
            fragment.setArguments(getArguments());
            transaction.replace("content", fragment);
            transaction.commit();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ConsulterProduit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
