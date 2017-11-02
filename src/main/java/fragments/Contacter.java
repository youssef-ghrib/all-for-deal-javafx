package fragments;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import org.kairos.core.Fragment;

public class Contacter extends Fragment {

    @Override
    public void onCreateView(FXMLLoader loader) {
        loader.setLocation(getClass().getResource("../fxml/contacter.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(AppelsOffreMembre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
