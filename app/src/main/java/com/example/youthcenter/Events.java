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
        this.eList.add(new Event("Koripallo","22.09.2020", "15:00" ,"17:00", "9-12 vuotiaille", "Nuokkari kenttä", "Tervetuloa pelaamaan koripalloa \n Lisäksi tarjolla syömistä ja juomista", 0, 1, false));
        this.eList.add(new Event("Koripallo","22.09.2020", "17:00" ,"19:00", "12-15 vuotiaille", "Nuokkari kenttä", "Tervetuloa pelaamaan koripalloa \n Lisäksi tarjolla syömistä ja juomista", 0, 1, false));
        this.eList.add(new Event("Koripallo","31.07.2020", "17:00" ,"19:00", "12-15 vuotiaille", "Nuokkari kenttä", "Tervetuloa pelaamaan koripalloa \n Lisäksi tarjolla syömistä ja juomista", 9, 1, true));
        this.eList.add(new Event("Pizza ja elokuvailta","02.08.2020", "19:00" ,"23:00", "12-15 vuotiaille", "Nuokkari", "Tehdään yhdessä pizzaa ja katsotaan WALLIE elokuva. Tervetuloa! \n ", 0, 1, false));
    }

    public ArrayList<Event> geteList() {
        return eList;
    }

    public void seteList(ArrayList<Event> eList) {
        this.eList = eList;
    }

    public Event getEvent(int i) {
        return eList.get(i);
    }
    public void AddToArray(Event event) {eList.add(event);}


}
