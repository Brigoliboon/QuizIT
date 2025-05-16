package com.cmu.quizit.model.question;

import androidx.annotation.NonNull;

public class Choice {
    private int id;
    private int questionID;
    private boolean isCorrect;
    private String choice;
    public Choice(int id, int questionID, String choice, int status){
        this.id = id;
        this.questionID = questionID;
        this.choice = choice;
        this.isCorrect = correctIdentifier(status);
    }

    public int getQuestionID() {
        return questionID;
    }

    public String getChoice() {
        return choice;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public int getId() {
        return id;
    }


    private boolean correctIdentifier(int status){
        return status == 1;
    }

    @Override
    public String toString() {
        return choice + " "+ isCorrect;
    }
}
