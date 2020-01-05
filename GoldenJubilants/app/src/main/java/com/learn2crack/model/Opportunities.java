package com.learn2crack.model;

/**
 * Created by Bilal on 31/10/2017.
 */

public class Opportunities {

    private String title;
    private String description;


    public String getAddedby() {
        return addedby;
    }

    public void setAddedby(String addedby) {
        this.addedby = addedby;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private String addedby;
    private String location;


    private String email;

    public Opportunities()
    {

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


    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
}
