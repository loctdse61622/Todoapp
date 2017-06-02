package com.coderschool.todoapp.entity;

/**
 * Created by Admin on 5/31/2017.
 */

public class Task {
    private String title;
    private String priority;
    private String deadline;
    private boolean status;

    public Task() {
    }

    public Task(String title, String priority, String deadline, boolean status) {
        this.title = title;
        this.priority = priority;
        this.deadline = deadline;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
