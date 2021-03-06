package com.example.aleksander.gdproject.List;

public class Task
{
    private int id;
    private String title;
    private String description;
    private String time_end;
    private String created;
    private String url;

    public Task(int id, String title, String description, String time_end, String created, String url)
    {
        this.id = id;

        this.title = title;
        this.description = description;
        this.time_end = time_end;
        this.created = created;
        this.url = url;
    }

    public Task( String title, String description, String time_end, String created, String url)
    {
        this.title = title;
        this.description = description;
        this.time_end = time_end;
        this.created = created;
        this.url = url;
    }


    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getCreated()
    {
        return created;
    }

    public void setCreated(String created)
    {
        this.created = created;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }


    public String getTime_end()
    {
        return time_end;
    }

    public void setTime_end(String time_end)
    {
        this.time_end = time_end;
    }
}
