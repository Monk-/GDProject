package com.example.aleksander.gdproject.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.aleksander.gdproject.Activities.SecondScreen;
import com.example.aleksander.gdproject.R;

import static com.example.aleksander.gdproject.Activities.MainScreen.TYPE_ACTION_EDIT;

public class EditDeleteDialog extends DialogFragment
{

    public View dialogView;
    private Activity activity;

    public EditDeleteDialog()
    {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        activity = getActivity();
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        dialogView = inflater.inflate(R.layout.edit_delete_fragment, (ViewGroup) null);
        TextView tV = (TextView) dialogView.findViewById(R.id.delete);
        tV.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                YouSureDialog youSureDialog = new YouSureDialog();
                Bundle args = new Bundle();
                args.putString("task", getArguments().getString("task"));
                youSureDialog.setArguments(args);
                youSureDialog.show(fm, "you are sure?");
                dismiss();
            }
        });
        TextView tV1 = (TextView) dialogView.findViewById(R.id.edit);
        tV1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent myIntent = new Intent(activity, SecondScreen.class);
                myIntent.putExtra("action", TYPE_ACTION_EDIT); //Optional parameters
                startActivity(myIntent);
                dismiss();
            }
        });
        builder.setView(dialogView)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {

                    }
                });
        Dialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return dialog;
    }

}
