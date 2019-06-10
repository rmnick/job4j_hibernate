package ru.job4j.service.entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Task {
    private int id;
    private String description;
    private Timestamp created;
    private boolean isDone;


    public Task() {

    }

    public Task(final int id, final String description, final Timestamp created, final boolean isDone) {
        this.id = id;
        this.description = description;
        this.created = created;
        this.isDone = isDone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(boolean done) {
        isDone = done;
    }

    @Override
    public String toString() {
        return String.format("{id : %d, created : %s, isDone : %b}",
                this.id, new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(this.created), this.isDone);
    }
}
