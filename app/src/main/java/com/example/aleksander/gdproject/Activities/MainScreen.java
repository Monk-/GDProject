package com.example.aleksander.gdproject.Activities;


import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.example.aleksander.gdproject.Database.TaskDbHelper;
import com.example.aleksander.gdproject.Dialogs.EditDeleteDialog;
import com.example.aleksander.gdproject.List.Task;
import com.example.aleksander.gdproject.List.TaskListAdapter;
import com.example.aleksander.gdproject.R;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.List;

public class MainScreen extends AppCompatActivity {

    private TaskListAdapter taskListAdapter;
    private ListView listView;
    public static TaskDbHelper taskDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        taskDbHelper = new TaskDbHelper(getApplicationContext());
        listView = (ListView) findViewById(R.id.listViewMainScreen);
        taskDbHelper.addToDb(new Task("first", "really", "121", "121", "https://j7w7h8q2.ssl.hwcdn.net/achievements/ach_ipad/6.10.png"));
        taskDbHelper.addToDb(new Task("second", "really", "1233", "121", "ht"));
        final List<Task> list = taskDbHelper.getAllTasks();
        taskListAdapter = new TaskListAdapter(this, list);
        listView.setAdapter(taskListAdapter);
        listView.setLongClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Task task = list.get(position);
                showEditDialog(task);
                taskListAdapter.notifyDataSetChanged();
                return true;
            }

        });

    }

    public void showAddActivity(View v)
    {
        Intent myIntent = new Intent(MainScreen.this, SecondScreen.class);
        //myIntent.putExtra("key", value); //Optional parameters
        MainScreen.this.startActivity(myIntent);
    }


    private void showEditDialog(Task task) {
        FragmentManager fm = getSupportFragmentManager();
        EditDeleteDialog editDeleteDialog = new EditDeleteDialog();
        Bundle args = new Bundle();
        args.putString("task", task.getTitle());
        editDeleteDialog.setArguments(args);
        editDeleteDialog.show(fm, "edit or delete");
    }

    public void onUpdate() {
        taskListAdapter = new TaskListAdapter(this, taskDbHelper.getAllTasks());
        taskListAdapter.notifyDataSetChanged();
        listView.setAdapter(taskListAdapter);
    }



}
