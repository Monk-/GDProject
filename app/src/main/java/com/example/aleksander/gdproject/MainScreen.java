package com.example.aleksander.gdproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.aleksander.gdproject.List.Task;
import com.example.aleksander.gdproject.List.TaskListAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainScreen extends AppCompatActivity {

    private TaskListAdapter taskListAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        listView = (ListView)findViewById(R.id.listViewMainScreen);
        List<Task> list = new ArrayList<>();
        list.add(new Task("first", "really", new Date(12312313L)));
        list.add(new Task("second", "really", new Date(12312313L)));
        taskListAdapter = new TaskListAdapter(this, list);
        listView.setAdapter(taskListAdapter);
    }
}
