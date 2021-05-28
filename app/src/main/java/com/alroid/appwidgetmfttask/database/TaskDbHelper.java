package com.alroid.appwidgetmfttask.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.alroid.appwidgetmfttask.Const;
import com.alroid.appwidgetmfttask.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskDbHelper extends DbHelper {
    private final String TABLE_NAME = Task.class.getSimpleName();
    private final String FIELD_ID = "id";
    private final String FIELD_TITTLE = "tittle";
    private final String FIELD_DETAILS = "details";

    //constructor:
    public TaskDbHelper(@Nullable Context context) {
        super(context);
    }

    public long insert(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = -1;

        try {
            ContentValues cv = new ContentValues();
            cv.put(FIELD_TITTLE, task.getTittle());
            cv.put(FIELD_DETAILS, task.getDetails());

            result = db.insert(TABLE_NAME, null, cv);
        } catch (Exception exception) {
            Log.e(Const.TAG_DB, "insert: \n" + exception.getMessage());
        } finally {
            db.close();
        }
        Log.e(Const.TAG_DB, "insert into db");
        return result;
    }

    public List<Task> select() {
        List<Task> taskList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] col = {FIELD_ID, FIELD_TITTLE, FIELD_DETAILS};
        Cursor cursor = db.query(TABLE_NAME, col, null, null, null, null, null);

        if (cursor.getCount() == 0) {
            db.close();
            return taskList;
        }

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(FIELD_ID));
            String tittle = cursor.getString(cursor.getColumnIndex(FIELD_TITTLE));
            String details = cursor.getString(cursor.getColumnIndex(FIELD_DETAILS));

            Task task = new Task(id, tittle, details);
            taskList.add(task);
        }

        cursor.close();
        db.close();
        return taskList;
    }

    public Task select(String param) {

        SQLiteDatabase db = getReadableDatabase();
        String[] col = {FIELD_ID, FIELD_TITTLE, FIELD_DETAILS};
        Cursor cursor = db.query(TABLE_NAME, col, "tittle like ?", new String[]{"%" + param + "%"}, null, null, null);

        if (cursor.getCount() == 0)
            return null;

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(FIELD_ID));
            String tittle = cursor.getString(cursor.getColumnIndex(FIELD_TITTLE));
            String details = cursor.getString(cursor.getColumnIndex(FIELD_DETAILS));

            Task task = new Task(id, tittle, details);
            return task;
        }

        cursor.close();
        return null;
    }
}
