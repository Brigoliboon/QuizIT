package com.cmu.quizit.model.question;

import java.util.ArrayList;

public class Question {
    private  int subcategoryID;
    private  String question;
    private int questionID;

    private ArrayList<Choice> choices;

    public Question(int subcategoryID, int questionID, String question, ArrayList<Choice> choices){
        this.subcategoryID = subcategoryID;
        this.questionID = questionID;
        this.question = question;
        this.choices = choices;
    }

    public int getSubcategoryID() {
        return subcategoryID;
    }

    public int getQuestionID() {
        return questionID;
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<Choice> getChoices() {
        return choices;
    }

    public boolean isCorrectChoice(Choice choice){
        return choice.isCorrect() && choice.getQuestionID() == questionID;
    }
}
