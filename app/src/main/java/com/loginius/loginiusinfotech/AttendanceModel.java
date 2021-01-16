package com.loginius.loginiusinfotech;

public class AttendanceModel {
    String firstname,lastname;

    public AttendanceModel() {
    }

    public AttendanceModel(String firstname,String lastname) {
        this.firstname = firstname;
        this.lastname=lastname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

}
