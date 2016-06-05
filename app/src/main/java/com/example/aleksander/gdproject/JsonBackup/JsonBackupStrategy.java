package com.example.aleksander.gdproject.JsonBackup;


import android.app.Activity;

public abstract class JsonBackupStrategy
{
    String pathToDb = "/BackupFolder/Tasks";

    public abstract void backup(Activity activity);
}
