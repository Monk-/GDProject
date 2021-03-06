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
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

import java.util.List;


public class TaskListAdapter extends BaseAdapter
{

    private List<Task> taskArray;
    private LayoutInflater inflater;
    private Picasso picasso;
    private final static String NOW = "No date set";
    //private ImageLoader imageLoader;

    private final static String CLICK_TO_SEE = "Click to see";
    private final static String NO_DESCRIPTION = "No description";

    static class ViewHolderItem
    {
        TextView titleText;
        TextView descriptionText;
        ImageView iconView;
        TextView dateText;
    }

    public TaskListAdapter(Activity activity, List<Task> taskArray)
    {
        this.taskArray = taskArray;
        this.inflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.picasso = Picasso.with(activity);
        // uncomment to make volley work
        // VolleyHelper.init(activity);
        // this.imageLoader = VolleyHelper.getImageLoader();

    }

    @Override
    public int getCount()
    {
        return taskArray.size();
    }

    @Override
    public Object getItem(int position)
    {
        return taskArray.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolderItem viewHolder;
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.titleText = (TextView) convertView.findViewById(R.id.textTitle);
            viewHolder.iconView = (ImageView) convertView.findViewById(R.id.iconView);
            viewHolder.descriptionText = (TextView) convertView.findViewById(R.id.textDescription);
            viewHolder.dateText = (TextView) convertView.findViewById(R.id.textExpire);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        Task task = (Task) getItem(position);
        viewHolder.titleText.setText(task.getTitle());
        viewHolder.descriptionText.setText(task.getDescription().equals("") ? NO_DESCRIPTION : CLICK_TO_SEE);
        picasso
                .load(task.getUrl().equals("") ? "nope" : task.getUrl())
                .placeholder(R.drawable.no_photo)
                .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                .centerInside()
                .into(viewHolder.iconView);
        if (task.getTime_end().length() >=10)
        {
            viewHolder.dateText.setText(task.getTime_end().equals("") ? NOW : task.getTime_end().substring(0, 10));
            DateTime timeLeft = new DateTime(task.getTime_end());
            Seconds secLeft = Seconds.secondsBetween(new DateTime(), timeLeft);
            if (secLeft.getSeconds()<0)
            {
                viewHolder.iconView.setBackgroundResource(R.color.red);
            }
            else
            {
                viewHolder.iconView.setBackgroundResource(R.color.teal);
            }
        }
        else
        {
            viewHolder.iconView.setBackgroundResource(R.color.teal);
            viewHolder.dateText.setText(NOW);
        }
//       async download images with volley

//        imageLoader.get(task.getUrl(),
//                ImageLoader.getImageListener(viewHolder.iconView,R.drawable.ic_add_white_36dp, // noimage
//                        R.drawable.ic_date_range_black_18dp)); // error
        return convertView;
    }
}
