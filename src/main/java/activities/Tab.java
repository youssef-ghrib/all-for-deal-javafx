package activities;

import org.kairos.core.Fragment;

public class Tab {

    protected String title; // title of the tab
    protected Class<? extends Fragment> fragment; // Fragment that represent a page

    public Tab(String title, Class<? extends Fragment> fragment) {
        this.title = title;
        this.fragment = fragment;
    }
}
