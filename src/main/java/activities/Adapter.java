package activities;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import org.kairos.components.RippleViewRow;
import org.kairos.layouts.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Holder> {

    @Override
    public RecyclerView.ViewRow call(ListView param) {
        return new RippleViewRow(this);
    }

    @Override
    public Holder onCreateViewHolder(FXMLLoader loader) {
        loader.setLocation(getClass().getResource("../fxml/item.fxml"));
        Holder holder = new Holder(loader);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, Object item) {
        Item object = (Item) item;
        holder.svg.setContent(object.icon);
        holder.title.setText(object.title);
    }

}
