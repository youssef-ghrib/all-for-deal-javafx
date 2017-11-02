package activities;

import fragments.ListeProduit;
import fragments.SupprimerProd;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import org.controlsfx.control.Rating;
import org.kairos.core.Activity;
import org.kairos.core.Fragment;
import dao.CommentaireProduitDao;
import dao.ProduitDao;

import entities.CommentaireProd;
import entities.EvaluationProd;
import entities.Produit;

/**
 *
 * @author daly
 */
public class CommentRatingProd extends Activity implements Initializable {

    
    @FXML
    private ImageView img;
    @FXML
    private Label nom1;
    @FXML
    private Label prix1;
    @FXML
    private Label categorie1;
    @FXML
    private Label sousCategorie1;
    @FXML
    private Label Couleur1;
    @FXML
    private Label dateLancement1;
    @FXML
    private Label nom;
    @FXML
    private Label prix;
    @FXML
    private Label categorie;
    @FXML
    private Label sousCategorie;
    @FXML
    private Label couleur;
    @FXML
    private Label dateLancement;
    @FXML
    private Rating rating;
        @FXML
    private Rating ratecomment;
 
    @FXML
    private TextArea comment;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Rating rating1;
 @FXML
 private TableView tabV;
  @FXML
     private TableColumn<Produit, String> tabC;
    @FXML
    private Button btn;
 private ObservableList<CommentaireProd> data;
    @Override
    public void onCreate() {
        super.onCreate();
        
        Produit m = ListeProduit.supprim.getid();
        CommentaireProduitDao cpDao = new CommentaireProduitDao();
       
        setContentView(getClass().getResource("/fxml/ConsulterProduit.fxml"));
        nom.setText(m.getNom());
        prix.setText(String.valueOf(m.getPrix()));
        couleur.setText((m.getCouleur()));
        dateLancement.setText(m.getDate_lancement());
        categorie.setText(m.getCategorie().getNom());
        sousCategorie.setText(m.getSous_categorie().getNom());
//        System.out.println("Id:"+m.getId());
//        List<String> st = cpDao.AfficheComment(m.getId());
//        
//        for(String s:st){
//        
//        }
System.out.println("aaaaa");
        ratecomment.setRating(cpDao.calculRating(m.getId()));
        System.out.println("bbbbb");
          data = FXCollections.observableArrayList();
        try {
           CommentaireProduitDao pDao = new CommentaireProduitDao();
            for (int i = 0; i < pDao.AfficheComment(m.getId()).size(); i++) {
                data.add(pDao.AfficheComment(m.getId()).get(i));
            }
        tabC.setCellValueFactory(new PropertyValueFactory<>("texte"));
            tabV.setItems(data);
        } catch (Exception e) {
            System.out.println("Error on Building Data");
        }
                   CommentaireProduitDao pDaoo = new CommentaireProduitDao();

        String path=pDaoo.getImageName(m.getId());
         final String imageURI = new File(("C:/wamp/www/web/web/img/produits/"+path)).toURI().toString(); 
Image image = new Image(imageURI);
    
img.setImage(image);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       

    }

    public void valider() {
        Produit m = ListeProduit.supprim.getid();

        String noms = comment.getText();
        double rate = rating1.getRating();
        int r = (int) rate;
        int membreConnecte = 6;
        int produitAffiche = m.getId();
        CommentaireProd cp = new CommentaireProd(6, produitAffiche, noms);

        CommentaireProduitDao dao = new CommentaireProduitDao();
        dao.addCommentaire(cp);
        EvaluationProd ep = new EvaluationProd(6, produitAffiche, r);
        dao.addRating(ep);
        JOptionPane j;
        j = new JOptionPane();
        j.showMessageDialog(null, "informations ajoutées avec succès ", "Information", JOptionPane.INFORMATION_MESSAGE);

    }

    public void evaluer() {
    }
}
