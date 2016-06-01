package com.example.aleksander.gdproject.List;

import android.widget.ImageView;

import java.util.Date;

public class Task {
    private String title;
    private String description;
    private ImageView image;
    private Date time_end;

    public Task(String title, String description, ImageView image, Date time_end) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.time_end = time_end;
    }

    public Task(String description, String title, Date time_end) {
        this.description = description;
        this.title = title;
        this.time_end = time_end;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public Date getTime_end() {
        return time_end;
    }

    public void setTime_end(Date time_end) {
        this.time_end = time_end;
    }
}
