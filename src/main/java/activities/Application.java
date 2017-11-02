package activities;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import dao.AppelOffreDao;
import dao.MembreDao;
import entities.Membre;
import fragments.ALaUne;
import fragments.AjoutProd;
import fragments.AjouterAppelOffre;
import fragments.AppelsOffre;
import fragments.AppelsOffreMembre;
import fragments.Compte;
import fragments.EvaluationProduit;
import fragments.GestionProduit;
import fragments.GestionReclamation;
import fragments.ListeMembre1;
import fragments.ListeProduit;
import fragments.MesServiceFrag;
import fragments.Nouveaute;
import fragments.Statistique;
import fragments.SupprimerProd;
import fragments.serviceFrag;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.kairos.ActionBarDrawerToggle;
import org.kairos.Toolbar;
import org.kairos.core.Activity;
import org.kairos.layouts.DrawerLayout;
import org.kairos.layouts.RecyclerView;
import org.kairos.layouts.SlidingTabLayout;
import org.kairos.layouts.ViewPager;

public class Application extends Activity implements Initializable {
//Image image = new Image(getClass().getResource("../activities/mute.png").getFile());
//    Image image1 = new Image(getClass().getResource("../activities/son.png").getFile());    

    public static Application app;
    
    MediaPlayer mediaplayer;

    HashMap<String, Object> args;

    AppelOffreDao appelOffreDao = new AppelOffreDao();
    MembreDao memberDao = new MembreDao();
    public Membre m;

    int initialCount;

    List<Tab> monEspaceTabs;

    List<Tab> servicesTabs;

    List<Tab> accueilTabs;

    List<Tab> produitsTabs;

    List<Tab> espaceAdminTabs;

    Adapter adapter;

    @FXML
    private Button iconB;

    @FXML
    AnchorPane ap;

    @FXML
    Toolbar toolbar;

    @FXML
    private DrawerLayout drawer;

    @FXML
    private RecyclerView<Item> items;

    @FXML
    private SlidingTabLayout tabLayout;

    @FXML
    private ViewPager viewPager;

    @FXML
    private Circle circle;

    public Application() {
        monEspaceTabs = new ArrayList<>();
        servicesTabs = new ArrayList<>();
        produitsTabs = new ArrayList<>();
        accueilTabs = new ArrayList<>();
        espaceAdminTabs = new ArrayList<>();
        adapter = new Adapter();
        app=this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        
        args = getIntent().getExtras();
        m = (Membre) args.get("membre");

        Media musicfile = new Media("file:///C:/Users/Pato/Downloads/a.mp3");

        mediaplayer = new MediaPlayer(musicfile);
        mediaplayer.setAutoPlay(true);
        mediaplayer.setVolume(10);
        iconB = new Button("mute");

        setContentView(getClass().getResource("../fxml/accueil.fxml"));

        toolbar.setTitle("ALL FOR DEAL");

        toolbar.setDisplayShowHomeEnabled(true);
//        toolbar.setDisplayHomeAsUpEnabled(true);

        setActionBar(toolbar);

        drawer.setDrawerListener(new ActionBarDrawerToggle(this, drawer, toolbar));

        items.setAdapter(adapter);

        accueilTabs.add(new Tab("A la une", ALaUne.class));
        accueilTabs.add(new Tab("Nouveautés", Nouveaute.class));

        monEspaceTabs.add(new Tab("Mes Service", MesServiceFrag.class));
        monEspaceTabs.add(new Tab("Mes Produits", SupprimerProd.class));
        monEspaceTabs.add(new Tab("Appels d'offre", AppelsOffreMembre.class));
        monEspaceTabs.add(new Tab("Ajouter Appel d'offre", AjouterAppelOffre.class));
        monEspaceTabs.add(new Tab("Mon Compte", Compte.class));

        servicesTabs.add(new Tab("Appels d'offre", AppelsOffre.class));
        servicesTabs.add(new Tab("Mes appels d'offres", AppelsOffreMembre.class));
        servicesTabs.add(new Tab(("Service Disponible"), serviceFrag.class));

        produitsTabs.add(new Tab("Ajouter produit", AjoutProd.class));
        produitsTabs.add(new Tab("Liste des produits", ListeProduit.class));

        espaceAdminTabs.add(new Tab("Gestion des Membres", ListeMembre1.class));
        espaceAdminTabs.add(new Tab("Gestion des reclamations", GestionReclamation.class));
        espaceAdminTabs.add(new Tab("Gestion des produits", GestionProduit.class));
        espaceAdminTabs.add(new Tab("Evaluation des Produit", EvaluationProduit.class));
        espaceAdminTabs.add(new Tab("Statistiques", Statistique.class));

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        accueilTabs.stream().forEach((t) -> {
            pagerAdapter.addTab(t);
        });

        tabLayout.setViewPager(viewPager);

        viewPager.setCurrentItem(0);

        HashMap<String, Boolean> args = intent.getExtras();

        if (args.get("user") == true) {
            System.out.println("user");
            items.getItems().addAll(
                    new Item(getClass().getResource("../img/Home.svg").getFile(), "ACCUEIL", accueilTabs),
                    new Item(getClass().getResource("../img/Contacts.svg").getFile(), "MON ESPACE", monEspaceTabs),
                    new Item(getClass().getResource("../img/Briefcase.svg").getFile(), "SERVICES", servicesTabs),
                    new Item(getClass().getResource("../img/Box.svg").getFile(), "PRODUITS", produitsTabs)
            );
        } else if (args.get("admin") == true) {
            System.out.println("admin");
            items.getItems().add(
                    new Item(getClass().getResource("../img/user.svg").getFile(), "ESPACE ADMIN", espaceAdminTabs)
            );
        }

        items.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            pagerAdapter.removeAll();

            newValue.tabs.stream().forEach((t) -> {
                pagerAdapter.addTab(t);
            });

            toolbar.getChildren().remove(tabLayout);
            tabLayout = new SlidingTabLayout();
            toolbar.getChildren().add(tabLayout);
            tabLayout.setViewPager(viewPager);

            viewPager.setCurrentItem(0);

            toolbar.setTitle(newValue.title);
            drawer.closeDrawer();

        });

        initialCount = appelOffreDao.getResponseCount(m);
        System.out.println(initialCount);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    int currentCount = appelOffreDao.getResponseCount(m);
                    System.out.println(currentCount);

                    if (initialCount != currentCount) {

                        initialCount = currentCount;

                        Notifications
                                .create()
                                .text("Vous avez reçu une réponse à votre appel d'offre")
                                .hideAfter(Duration.INDEFINITE)
                                .showInformation();
                    }
                });
            }
        }, 0, 5000);

        ap.getScene().getWindow().setOnCloseRequest((WindowEvent we) -> {
            timer.cancel();
        });

        if (m.getImageName() != null) {
            try {
                DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString());
                String accessToken = "tKqn9aG0UZAAAAAAAAAACMXrAofgnDqtNNSoX5gVYYeJCioYsaVLRnFcsdhQmWsC";
                DbxClient client = new DbxClient(config, accessToken);
                String url = client.createTemporaryDirectUrl("/" + m.getId() + "/" + m.getImageName()).url;
                Image img = new Image(url);
                circle.setFill(new ImagePattern(img));
            } catch (DbxException ex) {
                Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    public void mute() {
//    if(mediaplayer.getVolume()==0)
//    {mediaplayer.setVolume(10);
//     iconB.setGraphic(new ImageView(image1));
//    }else {
//mediaplayer.setVolume(0);
//     iconB.setGraphic(new ImageView(image));
//    }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
