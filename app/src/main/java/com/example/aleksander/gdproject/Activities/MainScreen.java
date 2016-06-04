package com.example.aleksander.gdproject.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.aleksander.gdproject.Database.TaskDbHelper;
import com.example.aleksander.gdproject.Dialogs.EditDeleteDialog;
import com.example.aleksander.gdproject.Dialogs.ShowTaskDialog;
import com.example.aleksander.gdproject.List.Task;
import com.example.aleksander.gdproject.List.TaskListAdapter;
import com.example.aleksander.gdproject.R;

import java.util.List;

public class MainScreen extends AppCompatActivity
{

    private TaskListAdapter taskListAdapter;
    private ListView listView;
    private static List<Task> list;
    public static TaskDbHelper taskDbHelper;
    public final static String TYPE_ACTION_ADD = "Add";
    public final static String TYPE_ACTION_EDIT = "Edit";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        init();
        initListView();
    }

    private void init()
    {
        taskDbHelper = new TaskDbHelper(getApplicationContext());
        listView = (ListView) findViewById(R.id.listViewMainScreen);
    }

    private void initListView()
    {
        list = taskDbHelper.getAllTasks();
        taskListAdapter = new TaskListAdapter(this, list);
        listView.setAdapter(taskListAdapter);
        listView.setLongClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                Task task = list.get(position);
                showPrevTask(task, position);
                taskListAdapter.notifyDataSetChanged();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id)
            {
                Task task = list.get(position);
                showEditDialog(task, position);
                taskListAdapter.notifyDataSetChanged();
                return true;
            }

        });
    }

    public void showAddActivity(View v)
    {
        Intent myIntent = new Intent(MainScreen.this, SecondScreen.class);
        myIntent.putExtra("action", TYPE_ACTION_ADD); //Optional parameters
        MainScreen.this.startActivity(myIntent);
    }


    private void showEditDialog(Task task, int position)
    {
        FragmentManager fm = getSupportFragmentManager();
        EditDeleteDialog editDeleteDialog = new EditDeleteDialog();
        Bundle args = new Bundle();
        args.putString("task", task.getTitle());
        args.putInt("position", position);
        editDeleteDialog.setArguments(args);
        editDeleteDialog.show(fm, "edit or delete");
    }

    private void showPrevTask(Task task, int position)
    {
        FragmentManager fm = getSupportFragmentManager();
        ShowTaskDialog showTaskDialog = new ShowTaskDialog();
        Bundle args = new Bundle();
        args.putString("task", task.getTitle());
        showTaskDialog.setArguments(args);
        showTaskDialog.show(fm, "show preview");
    }

    public void onUpdate(int position)
    {
        list.remove(position);
        taskListAdapter.notifyDataSetChanged();
    }


}
