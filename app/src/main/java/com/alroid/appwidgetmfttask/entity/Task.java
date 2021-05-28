package com.alroid.appwidgetmfttask.entity;

public class Task {
    private int id;
    private String tittle;
    private String details;

    public Task(String tittle, String details) {
        this.tittle = tittle;
        this.details = details;
    }

    public Task(int id, String tittle, String details) {
        this.id = id;
        this.tittle = tittle;
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", tittle='" + tittle + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
