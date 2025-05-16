package com.cmu.quizit.data.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cmu.quizit.model.option.MainOption;
import com.cmu.quizit.model.option.SubOption;
import com.cmu.quizit.model.question.Question;

import java.util.ArrayList;
import java.util.Collections;

public class OptionDatabase extends Database{
    public static ArrayList<MainOption> getMainOptions(Context context){
        String query = "SELECT * FROM categories";
        SQLiteDatabase db = Database.connect(context);
        Cursor cursor = db.rawQuery(query,null);

        ArrayList<MainOption> mainOptions = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int id = getIntValue(cursor, "id");
                String iconResourceName = getStringValue(cursor, "iconResource_name");
                String name = getStringValue(cursor, "name");

                MainOption option = new MainOption(id, iconResourceName,name, getSubOptions(context, id));
                mainOptions.add(option);
            }while (cursor.moveToNext());
            cursor.close();
        }
        return mainOptions;
    }

    private static ArrayList<SubOption> getSubOptions(Context context, int categoryId){
        String query = "SELECT * FROM subcategories WHERE category_id = ?";
        SQLiteDatabase db = Database.connect(context);
        Cursor cursor = db.rawQuery(query,new String[]{String.valueOf(categoryId)});

        ArrayList<SubOption> subCategories = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int id = getIntValue(cursor, "id");
                String iconResourceName = getStringValue(cursor, "iconResource_name");
                String name = getStringValue(cursor, "name");

                subCategories.add(new SubOption(id, iconResourceName, name));
            }while (cursor.moveToNext());
            cursor.close();
        }
        return subCategories;
    }

}
