/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.kairos.FragmentStatePagerAdapter;
import org.kairos.core.Fragment;
import org.kairos.core.FragmentManager;

/**
 *
 * @author joseph
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Tab> tabs; // collection of tabs

    public ViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        tabs = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int i) {
        try {
            Fragment fragment = tabs.get(i).fragment.newInstance(); // create instance of the fragment

            HashMap arguments = new HashMap();
            arguments.put("textLabel", "Fragment #" + (i + 1)); // pass a string like argument in the fragment
            fragment.setArguments(arguments); // set argumentts

            return fragment;

        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ViewPagerAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String getPageTitle(int i) {
        return tabs.get(i).title;
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    public void addTab(Tab tab) {
        tabs.add(tab);
    }

    public void removeAll() {
        tabs.clear();
    }

    public void removeTab(Tab tab) {
        tabs.remove(tab);
    }

    public void displayAll() {
        for (Tab t : tabs) {
            System.out.println(t.title);
        }
    }
}
