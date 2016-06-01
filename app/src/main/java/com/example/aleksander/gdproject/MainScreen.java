package com.example.aleksander.gdproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.aleksander.gdproject.Database.TaskDbHelper;
import com.example.aleksander.gdproject.List.Task;
import com.example.aleksander.gdproject.List.TaskListAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainScreen extends AppCompatActivity {

    private TaskListAdapter taskListAdapter;
    private ListView listView;
    private TaskDbHelper taskDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        taskDbHelper = new TaskDbHelper(getApplicationContext());
        listView = (ListView)findViewById(R.id.listViewMainScreen);
        taskDbHelper.addToDb(new Task("first", "really", "121", "121", "121"));
        taskDbHelper.addToDb(new Task("second", "really", "1233", "121", "121"));
        List<Task> list = taskDbHelper.getAllTasks();
        taskListAdapter = new TaskListAdapter(this, list);
        listView.setAdapter(taskListAdapter);
    }
}
