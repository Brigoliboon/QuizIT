package com.cmu.quizit.data.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.cmu.quizit.model.question.Choice;
import com.cmu.quizit.model.question.Question;

import java.util.ArrayList;
import java.util.Collections;

public abstract class QuestionDatabase extends Database{
    public static ArrayList<Question> getQuestions(Context context, int subcategoryId, int limit) {
        String query = "SELECT * FROM questions WHERE subcategory_id = ?";
        SQLiteDatabase db = Database.connect(context);
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(subcategoryId)});

        ArrayList<Question> questions = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {

                int questionID = getIntValue(cursor, "id");
                String q = getStringValue(cursor, "question_text");

                Question question = new Question(subcategoryId, questionID, q, getChoices(db, questionID));

                questions.add(question);
            } while (cursor.moveToNext());
            Collections.shuffle(questions);
            cursor.close();
        }
        ArrayList<Question> randomized = new ArrayList<>(questions.subList(0, limit));
        Log.d("prepared_question", randomized.toString());
        return randomized;
    }

    private static ArrayList<Choice> getChoices(SQLiteDatabase db, int questionId){
        String choiceQuery = "SELECT * FROM choices WHERE question_id = ?";
        Cursor choiceCursor = db.rawQuery(choiceQuery, new String[]{String.valueOf(questionId)});

        ArrayList<Choice> choices = new ArrayList<>();
        if (choiceCursor != null && choiceCursor.moveToFirst()) {
            do {
                int id = getIntValue(choiceCursor, "id");
                String choiceText = getStringValue(choiceCursor, "choice_text");
                int status = getIntValue(choiceCursor,"is_correct");
                Choice choice = new Choice(id, questionId, choiceText, status);

                choices.add(choice);
            } while (choiceCursor.moveToNext());
            choiceCursor.close();
        }
        return choices;
    }
}
