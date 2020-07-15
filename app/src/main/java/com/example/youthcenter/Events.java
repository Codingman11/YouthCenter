package com.example.youthcenter;

import java.util.ArrayList;

public class Events {

    private static Events mInstance;
    private ArrayList<Event> eList = null;
    public static Events getInstance() {

        if (mInstance == null) {
            mInstance = new Events();
        }
        return mInstance;
    }

    public Events() {
        this.eList = new ArrayList<>();
    }

    public ArrayList<Event> geteList() {
        return eList;
    }

    public void seteList(ArrayList<Event> eList) {
        this.eList = eList;
    }

    public void AddToArray(Event event) {eList.add(event);}
}
