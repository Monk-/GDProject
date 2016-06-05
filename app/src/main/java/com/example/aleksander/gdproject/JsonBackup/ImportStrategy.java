package com.example.aleksander.gdproject.JsonBackup;

import android.app.Activity;
import android.os.Environment;

import com.example.aleksander.gdproject.Database.ColumnNames;
import com.example.aleksander.gdproject.List.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import static com.example.aleksander.gdproject.Activities.MainScreen.taskDbHelper;

public class ImportStrategy extends JsonBackupStrategy
{
    @Override
    public void backup(Activity activity)
    {
        try
        {
            File file = new File(Environment.getExternalStorageDirectory() + "/BackupFolder/TaskDB");
            if (file.exists())
            {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream is = new ObjectInputStream(fis);
                String jsonString = (String) is.readObject();
                JSONArray jsonArray = new JSONArray(jsonString);
                is.close();
                fis.close();

                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Task task = new Task(Integer.parseInt(jsonObject.getString(ColumnNames.TaskEntry.COLUMN_NAME_TASK_ID)),
                            jsonObject.getString(ColumnNames.TaskEntry.COLUMN_NAME_TITLE),
                            jsonObject.getString(ColumnNames.TaskEntry.COLUMN_NAME_DESCRIPTION),
                            jsonObject.getString(ColumnNames.TaskEntry.COLUMN_NAME_TIME_END),
                            jsonObject.getString(ColumnNames.TaskEntry.COLUMN_NAME_CREATED),
                            jsonObject.getString(ColumnNames.TaskEntry.COLUMN_NAME_IMAGE_URL));
                    if (taskDbHelper.checkIfTaskWithThatIdExist(Integer.toString(task.getId())))
                    {
                        taskDbHelper.updateById(task, Integer.toString(task.getId()));
                    }
                    else
                    {
                        taskDbHelper.addToDb(task);
                    }
                }
            }
        }
        catch (IOException | ClassNotFoundException | JSONException ex)
        {
            ex.printStackTrace();
        }
    }

    }
