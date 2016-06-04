package com.example.aleksander.gdproject.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aleksander.gdproject.List.Task;
import com.example.aleksander.gdproject.R;
import com.squareup.picasso.Picasso;

import static com.example.aleksander.gdproject.Activities.MainScreen.taskDbHelper;

public class ShowTaskDialog  extends DialogFragment
{
    public View dialogView;
    private Activity activity;
    private ViewHolderItem viewHolderItem;
    private Picasso picasso;

    public ShowTaskDialog()
    {
    }

    static class ViewHolderItem
    {
        TextView editTitle;
        TextView editDescription;
        TextView editUrl;
        TextView editDate;
        ImageView imagePrev;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        this.picasso = Picasso.with(activity);
        activity = getActivity();
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        dialogView = inflater.inflate(R.layout.show_task_fragment, (ViewGroup) null);
        initViewHolder();
        setPrevFilelds();
        builder.setView(dialogView)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()  {
            public void onClick(DialogInterface dialog, int id)
            {

            }
        });;
        Dialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return dialog;
    }

    private void initViewHolder()
    {
        viewHolderItem = new ViewHolderItem();
        viewHolderItem.editTitle = (TextView) dialogView.findViewById(R.id.editTitlePrev);
        viewHolderItem.editUrl = (TextView) dialogView.findViewById(R.id.editUrlPrev);
        viewHolderItem.editDescription = (TextView) dialogView.findViewById(R.id.editDescriptionPrev);
        viewHolderItem.editDate = (TextView) dialogView.findViewById(R.id.editEndTimePrev);
        viewHolderItem.imagePrev = (ImageView) dialogView.findViewById(R.id.imagePrev);
    }

    private void setPrevFilelds()
    {
        Task task = taskDbHelper.getTaskByTitle(getArguments().getString("task"));
        viewHolderItem.editTitle.setText(task.getTitle());
        viewHolderItem.editUrl.setText(task.getUrl().equals("")
                ? "No URL" : task.getUrl());
        viewHolderItem.editDescription.setText(task.getDescription().equals("")
                ? "No description" : task.getDescription());
        if (task.getTime_end().length() >= 10)
        {
            viewHolderItem.editDate.setText(task.getTime_end().equals("") ?
                    "No end date" : task.getTime_end().substring(0,10));
        }
        picasso
                .load(task.getUrl().equals("") ? "nope" : task.getUrl())
                .placeholder(R.drawable.no_photo)
                .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                .centerInside()
                .into(viewHolderItem.imagePrev);
    }
}
