package com.example.aleksander.gdproject.Dialogs;

import android.app.Dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.aleksander.gdproject.Activities.MainScreen;
import com.example.aleksander.gdproject.R;
import static com.example.aleksander.gdproject.Activities.MainScreen.taskDbHelper;


public class YouSureDialog extends DialogFragment {

    public View dialogView;

    public YouSureDialog() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.edit_delete_fragment, (ViewGroup)null);
        TextView tV = (TextView)dialogView.findViewById(R.id.delete);
        builder.setView(dialogView)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        taskDbHelper.deleteTask(getArguments().getString("task"));
                        MainScreen activity = (MainScreen) getActivity();
                        activity.onUpdate();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        Dialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return dialog;
    }

}
