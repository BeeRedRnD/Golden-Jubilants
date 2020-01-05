package com.learn2crack.model;


public class User {

    private String name;
    private String email;
    private String password;
    private String created_at;
    private String newPassword;
    private String token;

    private String county;
    private String frequency;

    private String skills;

    // private String[] preferencesList;

    public User() {
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public void setCounty(String county) {
        this.county = county;
    }
    public String getCounty() {
        return county;
    }


    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
    public String getFrequency() {
        return frequency;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }


//    public String[] getPreferencesList() {
//        return preferencesList;
//    }
//
//    public void setPreferencesList(String[] preferencesList) {
//        this.preferencesList = preferencesList;
//    }
}
