package com.example.aleksander.gdproject.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.aleksander.gdproject.Database.TaskDbHelper;
import com.example.aleksander.gdproject.Dialogs.EditDeleteDialog;
import com.example.aleksander.gdproject.Dialogs.ShowTaskDialog;
import com.example.aleksander.gdproject.List.Task;
import com.example.aleksander.gdproject.List.TaskListAdapter;
import com.example.aleksander.gdproject.R;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.Seconds;

import java.util.Collections;
import java.util.Comparator;
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
                showPrevTask(task);
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

    private void showPrevTask(Task task)
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

    /// --- MENU --- ///

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sortAscending:
                sortAscending();
                return true;
            case R.id.sortByDate:
                sortByDate();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sortAscending()
    {
        Collections.sort(list, new LexicographicComparator()); //sort list ascending
        taskListAdapter.notifyDataSetChanged();
    }

    private void sortByDate()
    {
        Collections.sort(list, new DateComparator()); // sort list by date
        Collections.sort(list, new DateCreatedComparator()); // sort tasks without expire date
        taskListAdapter.notifyDataSetChanged();
    }


    static class LexicographicComparator implements Comparator<Task>
    {

        @Override
        public int compare(Task lhs, Task rhs)  // compare A-Z
        {
            return lhs.getTitle().compareTo(rhs.getTitle());
        }
    }

    static class DateComparator implements Comparator<Task>
    {

        @Override
        public int compare(Task lhs, Task rhs)
        {
            DateTime timeNow = new DateTime();
            // we will sort tasks without date later
            if (lhs.getTime_end().equals("") && rhs.getTime_end().equals(""))
            {
                return 0;
            }
            else if (rhs.getTime_end().equals("")) // if task without date - to the end
            {
                return -1;
            }
            else  if (lhs.getTime_end().equals("")) // if task without date - to the end
            {
                return 1;
            }
            else
            {
                DateTime timeLeft = new DateTime(lhs.getTime_end());
                DateTime timeRight = new DateTime(rhs.getTime_end());
                Seconds secLeft = Seconds.secondsBetween(timeNow, timeLeft);
                Seconds secRight = Seconds.secondsBetween(timeNow, timeRight);
                // if we have time to expire date just compare
                if (secLeft.getSeconds() >= 0 && secRight.getSeconds() >= 0)
                {
                    return DateTimeComparator.getDateOnlyInstance().compare(timeLeft, timeRight);
                }
                else
                {   // if we don't compare due time
                    if (secLeft.getSeconds() < 0 && secRight.getSeconds() < 0)
                    {
                        return secLeft.compareTo(secRight);
                    }
                    else if (secLeft.getSeconds() < 0) // if left task is due date, to the end
                    {
                        return 1;
                    }
                    else if (secRight.getSeconds() < 0) // same with right
                    {
                        return -1;
                    }
                    else
                    {
                        return secLeft.compareTo(secRight); // if up to date, just compare
                    }
                }
            }
        }
    }


    static class DateCreatedComparator implements Comparator<Task>
    {

        @Override
        public int compare(Task lhs, Task rhs) // here we compare tasks without expire date
        {
            if (lhs.getTime_end().equals("") && rhs.getTime_end().equals(""))
            {
                DateTime leftTime = new DateTime(lhs.getCreated());
                DateTime rightTime = new DateTime(rhs.getCreated());
                return rightTime.compareTo(leftTime); // compare by created date
            }
            else   // we have no interest in other tasks
            {
              return 0;
            }
        }
    }

}
