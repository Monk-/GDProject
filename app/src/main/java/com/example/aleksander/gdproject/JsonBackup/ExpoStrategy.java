package com.example.aleksander.gdproject.JsonBackup;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.example.aleksander.gdproject.Database.ColumnNames;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static com.example.aleksander.gdproject.Activities.MainScreen.taskDbHelper;
import static com.example.aleksander.gdproject.Database.TaskDbHelper.DATABASE_NAME;

public class ExpoStrategy extends  JsonBackupStrategy
{
    @Override
    public void backup(Activity activity)
    {

        String searchQuery = "SELECT  * FROM " + ColumnNames.TaskEntry.TABLE_NAME;
        SQLiteDatabase db = taskDbHelper.connectToDb();
        Cursor cursor = db.rawQuery(searchQuery, null );
        JSONArray resultSet     = new JSONArray();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for( int i=0 ;  i< totalColumn ; i++ )
            {
                if( cursor.getColumnName(i) != null )
                {
                    try
                    {
                        if(cursor.getString(i)!= null)
                        {
                            Log.d("TAG_NAME", cursor.getString(i) );
                            rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                        }
                        else
                        {
                            rowObject.put( cursor.getColumnName(i) ,  "" );
                        }
                    }
                    catch( Exception e )
                    {
                        Log.d("TAG_NAME", e.getMessage()  );
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("TAG_NAME", resultSet.toString() );
        File sd = Environment.getExternalStorageDirectory();
        FileOutputStream fos;
        try
        {
            File direct = new File(Environment.getExternalStorageDirectory() + "/BackupFolder");

            if (!direct.exists())
            {
                direct.mkdir(); // create directory if doesn't exist
            }

            File file = new File(sd, pathToDb);
            if(!file.exists()) {
               file.createNewFile();  // create file
            }
            fos = new FileOutputStream (file, true);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(resultSet.toString());
            os.close();
            fos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}
