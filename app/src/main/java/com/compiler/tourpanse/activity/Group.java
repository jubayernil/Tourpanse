package com.compiler.tourpanse.activity;

import java.util.ArrayList;

/**
 * Created by User on 8/21/2016.
 */
public class Group {
    private String Name;
    private ArrayList<Child> Items;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public ArrayList<Child> getItems() {
        return Items;
    }

    public void setItems(ArrayList<Child> Items) {
        this.Items = Items;
    }
}
