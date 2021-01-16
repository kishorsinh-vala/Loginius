package com.loginius.loginiusinfotech;

public class ModalClass {
    private String uesrname,password;

    public ModalClass() {
    }

    public ModalClass(String uesrname, String password) {
        this.uesrname = uesrname;
        this.password = password;
    }

    public String getUesrname() {
        return uesrname;
    }

    public void setUesrname(String uesrname) {
        this.uesrname = uesrname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
