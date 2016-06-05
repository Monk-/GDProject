package com.example.aleksander.gdproject.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.aleksander.gdproject.List.Task;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;


public class TaskDbHelper extends SQLiteOpenHelper
{

    public static final int DATABASE_VERSION = 1; // If change then increment version.
    public static final String DATABASE_NAME = "Tasks.db";

    public TaskDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void addToDb(Task come)         // add new task to DB
    {
        SQLiteDatabase db = connectToDb();
        ContentValues values = setValues(come);
        insertToDb(db, values);
        closeDb(db);
    }


    public SQLiteDatabase connectToDb()   // get connection
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
        Cursor cursor = createCursor(db, query);  // create cursor
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
        if (cursor.moveToFirst())
        {
            do
            {
                Task task = new Task(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5));
                list.add(task);
            }
            while (cursor.moveToNext());
        }
        return list;
    }

    public Task getTaskByTitle(String title)
    {
        SQLiteDatabase db = connectToDb();
        String query = buildQueryWhereTitle(title);
        Cursor cursor = createCursor(db, query);
        Task task = getTask(cursor);
        closeDb(db);
        return task;
    }

    private String buildQueryWhereTitle(String title)
    {
        return "SELECT  * FROM " + ColumnNames.TaskEntry.TABLE_NAME + " WHERE title='" + title + "'";
    }

    private Task getTask(Cursor cursor)
    {
        if (cursor.moveToFirst())
        {
            return new Task(cursor.getInt(0),cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5));
        }
        return null;
    }


    public void deleteTask(String taskTitle)
    {
        SQLiteDatabase db = connectToDb();
        delete(db, taskTitle);
        closeDb(db);
    }

    private void delete(SQLiteDatabase db, String taskTitle)
    {
        db.delete(ColumnNames.TaskEntry.TABLE_NAME, //table name
                " title = ?",  // selections
                new String[]{taskTitle}); //selections args
    }

    public void update(Task task, String oldTitle)
    {
        SQLiteDatabase db = connectToDb();
        ContentValues cv = new ContentValues();
        cv.put(ColumnNames.TaskEntry.COLUMN_NAME_TITLE, task.getTitle());
        cv.put(ColumnNames.TaskEntry.COLUMN_NAME_TIME_END, task.getTime_end());
        cv.put(ColumnNames.TaskEntry.COLUMN_NAME_IMAGE_URL, task.getUrl());
        cv.put(ColumnNames.TaskEntry.COLUMN_NAME_DESCRIPTION, task.getDescription());
        cv.put(ColumnNames.TaskEntry.COLUMN_NAME_CREATED, new DateTime().toString());
        db.update(ColumnNames.TaskEntry.TABLE_NAME, cv, "title=?", new String[]{oldTitle});
        closeDb(db);
    }


    public void updateById(Task task, String id)
    {
        SQLiteDatabase db = connectToDb();
        ContentValues cv = new ContentValues();
        cv.put(ColumnNames.TaskEntry.COLUMN_NAME_TITLE, task.getTitle());
        cv.put(ColumnNames.TaskEntry.COLUMN_NAME_TIME_END, task.getTime_end());
        cv.put(ColumnNames.TaskEntry.COLUMN_NAME_IMAGE_URL, task.getUrl());
        cv.put(ColumnNames.TaskEntry.COLUMN_NAME_DESCRIPTION, task.getDescription());
        cv.put(ColumnNames.TaskEntry.COLUMN_NAME_CREATED, new DateTime().toString());
        db.update(ColumnNames.TaskEntry.TABLE_NAME, cv, "taskid=?", new String[]{id});
        closeDb(db);
    }

    public boolean checkIfTaskWithThatTitleExist(String title)
    {
        SQLiteDatabase db = connectToDb();
        boolean g = find(db, title);
        closeDb(db);
        return g;
    }

    private boolean find(SQLiteDatabase db, String id)
    {
        Cursor cursor = setCursorID(db, setColumns(), id);
        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return false;
        }
        return true; // true if task with that id exists in db
    }

    public boolean checkIfTaskWithThatIdExist(String id)
    {
        SQLiteDatabase db = connectToDb();
        boolean g = find(db, id);
        closeDb(db);
        return g;
    }

    private boolean findByTitle(SQLiteDatabase db, String title)
    {
        Cursor cursor = setCursor(db, setColumns(), title);
        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return false;
        }
        return true; // true if task with that title exists in db
    }

    private Cursor setCursor(SQLiteDatabase db, String[] columns, String title)
    {
        return db.query(ColumnNames.TaskEntry.TABLE_NAME, // a. table
                columns, // b. column names
                " title = ?", // c. selections
                new String[]{title}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
    }

    private Cursor setCursorID(SQLiteDatabase db, String[] columns, String title)
    {
        return db.query(ColumnNames.TaskEntry.TABLE_NAME, // a. table
                columns, // b. column names
                " taskid = ?", // c. selections
                new String[]{title}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
    }

    private String[] setColumns() {
        return new String[]{
                ColumnNames.TaskEntry.COLUMN_NAME_TASK_ID,
                ColumnNames.TaskEntry.COLUMN_NAME_TITLE,
                ColumnNames.TaskEntry.COLUMN_NAME_DESCRIPTION,
                ColumnNames.TaskEntry.COLUMN_NAME_TIME_END,
                ColumnNames.TaskEntry.COLUMN_NAME_CREATED,
                ColumnNames.TaskEntry.COLUMN_NAME_IMAGE_URL
        };
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_TASK_TABLE = "CREATE TABLE " + ColumnNames.TaskEntry.TABLE_NAME + " ( " +
                ColumnNames.TaskEntry.COLUMN_NAME_TASK_ID + " INTEGER PRIMARY KEY, " +
                ColumnNames.TaskEntry.COLUMN_NAME_TITLE + " TEXT NOT NULL UNIQUE, " +
                ColumnNames.TaskEntry.COLUMN_NAME_DESCRIPTION + " TEXT, " +
                ColumnNames.TaskEntry.COLUMN_NAME_TIME_END + " TEXT, " +
                ColumnNames.TaskEntry.COLUMN_NAME_CREATED + " TEXT NOT NULL, " +
                ColumnNames.TaskEntry.COLUMN_NAME_IMAGE_URL + " TEXT )";
        db.execSQL(CREATE_TASK_TABLE);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + ColumnNames.TaskEntry.TABLE_NAME);
        this.onCreate(db);
    }
}