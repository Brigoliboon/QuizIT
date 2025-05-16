package com.cmu.quizit.ui.quiz;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.cmu.quizit.R;
import com.cmu.quizit.ui.quiz.fragment.QuizFragment;

public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_quiz);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, QuizFragment.newInstance())
                    .commitNow();
        }
    }
}