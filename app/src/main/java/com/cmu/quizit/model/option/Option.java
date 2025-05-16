package com.cmu.quizit.model.option;


import android.content.Context;

public class Option {
    private int optionID;
    private String iconResourceName;
    private String title;

    public Option(int optionID, String imageResourceName, String title){
        this.optionID = optionID;
        this.iconResourceName = imageResourceName;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getIconResourceID(Context context) {
        int drawableId = context.getResources().getIdentifier(iconResourceName, "drawable", context.getPackageName());
        return drawableId;

    }

    public int getOptionID() {
        return optionID;
    }
}
