package com.haulmont.testtask.model;

import java.util.ArrayList;
import java.util.List;

public enum PriorityR {
    Normal, Cito, Statim;

    public static List<String> getAsArrayList(){
        ArrayList<String> states = new ArrayList<String>();
        states.add(PriorityR.Normal.toString());
        states.add(PriorityR.Cito.toString());
        states.add(PriorityR.Statim.toString());
        return states;
    }

}