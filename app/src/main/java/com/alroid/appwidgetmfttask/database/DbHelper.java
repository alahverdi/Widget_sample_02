package com.alroid.appwidgetmfttask.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.alroid.appwidgetmfttask.Const;
import com.alroid.appwidgetmfttask.entity.Task;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "task_db";
    public static final int VERSION = 1;

    //db constructor:
    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        Log.e(Const.TAG_DB, "Constructor()");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(Const.TAG_DB, "OnCreate()");
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // do not need to upgrade in this case ...
    }

    private void createTable(SQLiteDatabase db) {
        try {
            String query = "CREATE TABLE " + Task.class.getSimpleName() + "(\n" +
                    "   id INTEGER PRIMARY KEY,\n" +
                    "   tittle VARCHAR(255),\n" +
                    "   details VARCHAR(255)\n" +
                    ");";

            db.execSQL(query);

        } catch (Exception exception) {
            Log.e(Const.TAG_DB, "createTable: \ncatch Exception:\n" + exception.getMessage());
        }
    }
}
