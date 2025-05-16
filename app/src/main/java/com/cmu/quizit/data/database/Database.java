package com.cmu.quizit.data.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.cmu.quizit.data.database.QuizDatabaseHelper;

public class Database {
    private static QuizDatabaseHelper helper;
    private static SQLiteDatabase db = null;

    // for Singleton purposes
    public static synchronized SQLiteDatabase connect(Context context){
        if (db == null){
            helper = new QuizDatabaseHelper(context);
            db = helper.getReadableDatabase();
        }
        return db;
    }

    static Integer getIntValue(Cursor cursor, String indexName){
        int index = cursor.getColumnIndex(indexName);
        if (index != -1){
            return cursor.getInt(index);
        }
        return null;
    }

    @Nullable
    static String getStringValue(Cursor cursor, String indexName){
        int index = cursor.getColumnIndex(indexName);
        if (index != -1){
            return cursor.getString(index);
        }
        return null;
    }
}
