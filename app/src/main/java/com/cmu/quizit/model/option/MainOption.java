package com.cmu.quizit.model.option;

import java.util.ArrayList;

public class MainOption extends Option{
    private ArrayList<SubOption> subOptions;
    public MainOption(int optionID, String imageResourceName, String title, ArrayList<SubOption> subOptions) {
        super(optionID, imageResourceName, title);
        this.subOptions = subOptions;
    }

    public ArrayList<SubOption> getSubOptions() {
        return subOptions;
    }
}
