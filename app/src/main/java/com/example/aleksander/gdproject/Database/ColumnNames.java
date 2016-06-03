package com.example.aleksander.gdproject.Database;

import android.provider.BaseColumns;


public class ColumnNames
{

    //prevent someone from accidentally instantiating the contract class
    public ColumnNames()
    {
    }

    /* Inner class that defines the table contents */
    public static abstract class TaskEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "task";
        public static final String COLUMN_NAME_TASK_ID = "taskid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_CREATED = "created";
        public static final String COLUMN_NAME_TIME_END = "time_end";
        public static final String COLUMN_NAME_IMAGE_URL = "url";
    }
}
