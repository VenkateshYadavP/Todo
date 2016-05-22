package com.example.venkatesh.project1.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.venkatesh.project1.model.Task;

import java.util.ArrayList;

/**
 * Created by venkatesh on 5/22/16.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "TASK";
    private static final int DB_VERSION = 1 ;
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DECRIPTION = "description";
    private static final String KEY_DATE = "date";
    private static final String KEY_STATUS = "status";
    private String Table_Title = "TASK";
    SQLiteDatabase sdb;
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "CREATE TABLE "
                + Table_Title + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE
                + " TEXT," + KEY_DECRIPTION
                + " TEXT," + KEY_DATE + " TEXT,"+KEY_STATUS
                + " INTEGER" +")";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insertTask(Task mTask) {
        sdb = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE,mTask.title);
        values.put(KEY_DECRIPTION,mTask.description);
        values.put(KEY_DATE,mTask.date);
        values.put(KEY_STATUS,mTask.status);
        sdb.insert(Table_Title, null, values);
        sdb.close();
    }

    public ArrayList<Task> fetchAllTasks() {
        ArrayList<Task> mTasks = new ArrayList<>();
        sdb = getReadableDatabase();
        String query = "select * from "+Table_Title;
        Cursor cursor = sdb.rawQuery(query,null);
        if(cursor.moveToFirst()) {
            do {
                Integer id = cursor.getInt(0);
                String title =  cursor.getString(1);
                String descriprion = cursor.getString(2);
                String date = cursor.getString(3);
                Integer status = cursor.getInt(4);
                mTasks.add(new Task(id,title,descriprion,date,status));

            } while (cursor.moveToNext());
        }
        return  mTasks;
    }
    public ArrayList<Task> fetchIncompleteTasks() {
        ArrayList<Task> mTasks = new ArrayList<>();
        sdb = getReadableDatabase();
        String query = "SELECT * FROM " + Table_Title + " WHERE "+ KEY_STATUS +" = 0";
        Cursor cursor = sdb.rawQuery(query,null);
        if(cursor.moveToFirst()) {
            do {
                Integer id = cursor.getInt(0);
                String title =  cursor.getString(1);
                String descriprion = cursor.getString(2);
                String date = cursor.getString(3);
                Integer status = cursor.getInt(4);
                mTasks.add(new Task(id,title,descriprion,date,status));

            } while (cursor.moveToNext());
        }
        return  mTasks;
    }
    public int updateStatus(Integer id) {
        sdb = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_STATUS, 1);
        return sdb.update(Table_Title,values,KEY_ID+"=?",new String[]{String.valueOf(id)});
    }
    public int updateTask(Integer id,String title,String description,String date) {
        sdb = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, date);
        values.put(KEY_TITLE,title);
        values.put(KEY_DECRIPTION,description);
        return sdb.update(Table_Title,values,KEY_ID+"=?",new String[]{String.valueOf(id)});
    }
    public int deleteTask(Integer id) {
        sdb = getWritableDatabase();
        String whereClause = KEY_ID + "=?";
        String[] whereArgs = new String[] { String.valueOf(id) };
        return  sdb.delete(Table_Title, whereClause, whereArgs);
    }
    public void drop()
    {
        sdb = getWritableDatabase();
        String query = "DELETE FROM "+Table_Title;
        sdb.execSQL(query);
        sdb.close();
    }

    public ArrayList<Task> fetchCompletedTasks() {
        ArrayList<Task> mTasks = new ArrayList<>();
        sdb = getReadableDatabase();
        String query = "SELECT * FROM " + Table_Title + " WHERE "+ KEY_STATUS +" = 1";
        Cursor cursor = sdb.rawQuery(query,null);
        if(cursor.moveToFirst()) {
            do {
                Integer id = cursor.getInt(0);
                String title =  cursor.getString(1);
                String descriprion = cursor.getString(2);
                String date = cursor.getString(3);
                Integer status = cursor.getInt(4);
                mTasks.add(new Task(id,title,descriprion,date,status));

            } while (cursor.moveToNext());
        }
        return  mTasks;
    }
}
