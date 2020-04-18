package com.example.to_do_list_app;

public class Todo {
    private String id;
    private String description;
    private String dateofrecord;
    private String timeofrecord;

    public Todo(String id, String description, String dateofrecord, String timeofrecord) {
        this.id = id;
        this.description = description;
        this.dateofrecord = dateofrecord;
        this.timeofrecord = timeofrecord;
    }

    public String getId() {
        return id;
    }


    public String getDescription() {
        return description;
    }


    public String getDateofrecord() {
        return dateofrecord;
    }


    public String getTimeofrecord() {
        return timeofrecord;
    }

}
