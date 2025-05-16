package com.cmu.quizit.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class QuizDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "quizit.db";
    private static String DB_PATH = "";
    private Context context;

    public QuizDatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
        DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        copyDatabaseIfNeeded();
    }

    private void copyDatabaseIfNeeded() {
        File dbFile = new File(DB_PATH + DB_NAME);
        if (!dbFile.exists()) {
            Log.d("Created", "Creating Table");
            getReadableDatabase(); // creates empty db
            close();

            try {
                File dir = new File(DB_PATH);
                if (!dir.exists()) {
                    dir.mkdir();
                }

                InputStream input = context.getAssets().open(DB_NAME);
                OutputStream output = new FileOutputStream(dbFile);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = input.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }

                output.flush();
                output.close();
                input.close();

            } catch (IOException e) {
                throw new RuntimeException("Error copying database", e);
            }
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Nothing to do; pre-filled DB already exists
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Implement version control if needed
    }
}

