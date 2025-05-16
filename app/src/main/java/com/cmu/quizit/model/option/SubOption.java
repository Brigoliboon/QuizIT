package com.cmu.quizit.model.option;

import android.content.Context;
import android.view.View;

import androidx.annotation.Nullable;

import com.cmu.quizit.data.database.QuestionDatabase;
import com.cmu.quizit.model.question.Question;

import java.util.ArrayList;

public class SubOption extends Option {
    private ArrayList<Question> questions;
    public SubOption(int optionID, String imageResourceName, String title) {
        super(optionID, imageResourceName, title);
        this.questions = null;
    }

    public void loadQuestions(Context context, SubOption subOption){
        questions = QuestionDatabase.getQuestions(context, subOption.getOptionID(), 15);
    }

    @Nullable
    public ArrayList<Question> getQuestions() {
        return questions;
    }
}
