package activities;

import java.io.File;
import java.util.List;
import org.kairos.components.SVGFactory;

public class Item {

    protected String icon;
    protected String title;
    protected List<Tab> tabs;

    public Item(String icon, String title, List<Tab> tabs) {
        this.icon = SVGFactory.getSVGContent(new File(icon));
        this.title = title;
        this.tabs = tabs;
    }
    
}
