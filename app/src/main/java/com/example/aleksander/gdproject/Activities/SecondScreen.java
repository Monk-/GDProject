package com.example.aleksander.gdproject.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.aleksander.gdproject.List.Task;
import com.example.aleksander.gdproject.R;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;

import java.util.Locale;

import static com.example.aleksander.gdproject.Activities.MainScreen.TYPE_ACTION_ADD;
import static com.example.aleksander.gdproject.Activities.MainScreen.TYPE_ACTION_EDIT;
import static com.example.aleksander.gdproject.Activities.MainScreen.taskDbHelper;

public class SecondScreen extends AppCompatActivity
{

    private static DateTime date; //date picker result
    private EditText editTitle;
    private EditText editDescription;
    private EditText editUrl;
    private static EditText editDate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);
        JodaTimeAndroid.init(this);  // init joda time
        actionTypeChange();
        initializeEditTexts();
    }

    private void initializeEditTexts()
    {
        editTitle = (EditText)findViewById(R.id.editTitle);
        editDescription = (EditText)findViewById(R.id.editDescription);
        editUrl = (EditText)findViewById(R.id.editUrl);
        editDate = (EditText) findViewById(R.id.editData);
        if (editDate != null)
        {
            editDate.setInputType(InputType.TYPE_NULL); // set inputType none, because in xml not working
        }
    }

    private void actionTypeChange()
    {
        Intent intent = getIntent();
        String typeAction = intent.getStringExtra("action");
        try
        {
            FloatingActionButton button = (FloatingActionButton) findViewById(R.id.fabSecondScreen);
            switch (typeAction)
            {
                case TYPE_ACTION_ADD:
                    button.setImageResource(R.drawable.ic_add_white_36dp);
                    button.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            onAddClick();
                        }

                    });
                    break;
                case TYPE_ACTION_EDIT:

                    button.setImageResource(R.drawable.ic_mode_edit_white_36dp);
                    button.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            onEditClick();
                        }

                    });
                    break;
            }
        }
        catch (NullPointerException e) // if button is NULL
        {
            e.printStackTrace();
        }
    }

    private void onEditClick()
    {

    }

    private void onAddClick()
    {
        String temp = editTitle.getText().toString();
        if (temp.equals(""))
        {
            editTitle.setError("Required field");
        }
        else
        {
            taskDbHelper.addToDb(new Task(
                    temp,
                    editDescription.getText().toString(),
                    date == null ? "" : date.toString(),
                    new DateTime().toString(),
                    editUrl.getText().toString()
            ));
        }

    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener
    {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            // Use the current date as the default date in the picker
            DateTime today = new DateTime();
            return new DatePickerDialog(getActivity(), this,
                    today.getYear(), today.getMonthOfYear(), today.getDayOfMonth());
        }

        public void onDateSet(DatePicker view, int year, int month, int day)
        {
            date = new DateTime(year, month, day, 0, 0);
            editDate.setText(String.format(Locale.getDefault(), "%d/%d/%d", date.getDayOfMonth(),
                    date.getMonthOfYear(), date.getYear()));
        }

    }

    public void showDatePickerDialog(View v)
    {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
}
