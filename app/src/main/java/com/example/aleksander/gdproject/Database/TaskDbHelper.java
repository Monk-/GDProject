package com.example.aleksander.gdproject.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.aleksander.gdproject.List.Task;

import java.util.ArrayList;
import java.util.List;


public class TaskDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1; // If change then increment version.
    public static final String DATABASE_NAME = "Tasks.db";

    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void addToDb(Task come)         // add new task to DB
    {
        SQLiteDatabase db = connectToDb();
        ContentValues values = setValues(come);
        insertToDb(db,values);
        closeDb(db);
    }


    private SQLiteDatabase connectToDb()   // get connection
    {
        return this.getWritableDatabase();
    }

    private ContentValues setValues(Task task)  // set values
    {
        ContentValues values = new ContentValues();
        values.put(ColumnNames.TaskEntry.COLUMN_NAME_TITLE, task.getTitle());
        values.put(ColumnNames.TaskEntry.COLUMN_NAME_DESCRIPTION, task.getDescription());
        values.put(ColumnNames.TaskEntry.COLUMN_NAME_TIME_END, task.getTime_end());
        values.put(ColumnNames.TaskEntry.COLUMN_NAME_CREATED, task.getCreated());
        values.put(ColumnNames.TaskEntry.COLUMN_NAME_IMAGE_URL, task.getUrl());
        return values;
    }

    private void insertToDb(SQLiteDatabase db, ContentValues values)  // insert
    {
        db.insert(ColumnNames.TaskEntry.TABLE_NAME,
                null,
                values);
    }

    private void closeDb(SQLiteDatabase db)  // close DB
    {
        db.close();
    }


    public List<Task> getAllTasks()   // get all tasks from DB
    {
        SQLiteDatabase db = connectToDb();       // open
        String query = buildQuery();             // build query
        Cursor cursor = createCursor(db,query);  // create cursor
        List<Task> tasks = getAllData(cursor);  // get tasks
        db.close();                             // close db
        return tasks;
    }

    private String buildQuery()
    {
        return "SELECT  * FROM " + ColumnNames.TaskEntry.TABLE_NAME;
    }

    private Cursor createCursor(SQLiteDatabase db, String query)
    {
        return db.rawQuery(query, null);
    }

    private List<Task> getAllData(Cursor cursor)
    {
        List<Task> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                    Task task = new Task(cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getString(4), cursor.getString(5));
                    list.add(task);
            }
            while (cursor.moveToNext());
        }
        return list;
    }

    public void deleteTask(String taskTitle)
    {
        SQLiteDatabase db = connectToDb();
        delete(db, taskTitle);
        closeDb(db);
    }

   private void delete(SQLiteDatabase db, String taskTitle) {
        db.delete(ColumnNames.TaskEntry.TABLE_NAME, //table name
                " title = ?",  // selections
                new String[]{taskTitle}); //selections args
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASK_TABLE = "CREATE TABLE " + ColumnNames.TaskEntry.TABLE_NAME + " ( " +
                ColumnNames.TaskEntry.COLUMN_NAME_TASK_ID + " INTEGER PRIMARY KEY, " +
                ColumnNames.TaskEntry.COLUMN_NAME_TITLE + " TEXT NOT NULL UNIQUE, " +
                ColumnNames.TaskEntry.COLUMN_NAME_DESCRIPTION + " TEXT, " +
                ColumnNames.TaskEntry.COLUMN_NAME_TIME_END + " TEXT, " +
                ColumnNames.TaskEntry.COLUMN_NAME_CREATED + " TEXT NOT NULL, " +
                ColumnNames.TaskEntry.COLUMN_NAME_IMAGE_URL + " TEXT )";
        db.execSQL(CREATE_TASK_TABLE);

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ColumnNames.TaskEntry.TABLE_NAME);
        this.onCreate(db);
    }
}