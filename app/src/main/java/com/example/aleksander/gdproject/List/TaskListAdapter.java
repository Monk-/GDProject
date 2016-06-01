package com.example.aleksander.gdproject.List;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aleksander.gdproject.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;


public class TaskListAdapter extends BaseAdapter {

    private List<Task> taskArray;
    private LayoutInflater inflater;

    static class ViewHolderItem
    {
        TextView titleText;
        TextView descriptionText;
        ImageView iconView;
        TextView dateText;
    }

    public TaskListAdapter(Activity activity, List<Task> taskArray) {
        this.taskArray = taskArray;
        this.inflater = ( LayoutInflater)activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return taskArray.size();
    }

    @Override
    public Object getItem(int position) {
        return taskArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.titleText = (TextView) convertView.findViewById(R.id.titleText);
            viewHolder.iconView = (ImageView) convertView.findViewById(R.id.iconView);
            viewHolder.descriptionText = (TextView) convertView.findViewById(R.id.descriptionText);
            viewHolder.dateText = (TextView) convertView.findViewById(R.id.dateText);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        Task task = (Task) getItem(position);
        viewHolder.titleText.setText(task.getTitle());
        viewHolder.descriptionText.setText(task.getDescription());
        viewHolder.dateText.setText("now");
        return convertView;
    }
}
