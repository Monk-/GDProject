package com.example.aleksander.gdproject.JsonBackup;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

import static com.example.aleksander.gdproject.Database.TaskDbHelper.DATABASE_NAME;

public class ImportStrategy extends JsonBackupStrategy
{
    @Override
    public void backup(Activity activity)
    {
        String currentDBPath = "//data//" + "com.example.aleksander.gdproject"
                + "//databases//" + DATABASE_NAME;
        try
        {
            FileInputStream fis = new FileInputStream (new File(currentDBPath));
            ObjectInputStream is = new ObjectInputStream(fis);
           JSONArray jsonArray = (JSONArray) is.readObject();
            is.close();
            fis.close();

            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObjectCity = jsonArray.getJSONObject(i);
                String cityName = jsonObjectCity.getString("name");
                String cityState = jsonObjectCity.getString("state");
                String cityDescription = jsonObjectCity.getString("description");
            }
        }
        catch (IOException | ClassNotFoundException | JSONException ex)
        {
            ex.printStackTrace();
        }
    }

    }
