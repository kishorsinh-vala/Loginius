package com.loginius.loginiusinfotech;

public class ProjectModelClass {
    String pnm, ref, start, due, amount, dev;


    public ProjectModelClass(String pnm, String ref, String start, String due, String amount, String dev) {
        this.pnm = pnm;
        this.ref = ref;
        this.start = start;
        this.due = due;
        this.amount = amount;
        this.dev = dev;
    }

    public String getPnm() {
        return pnm;
    }

    public void setPnm(String pnm) {
        this.pnm = pnm;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDev() {
        return dev;
    }

    public void setDev(String dev) {
        this.dev = dev;
    }

}
