package activities;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.shape.SVGPath;
import org.kairos.layouts.RecyclerView;

public class Holder extends RecyclerView.ViewHolder {
    
    @FXML
    Label title;
    
    @FXML
    SVGPath svg;
    
    public Holder(FXMLLoader loader) {
        super(loader);
    }
    
}
