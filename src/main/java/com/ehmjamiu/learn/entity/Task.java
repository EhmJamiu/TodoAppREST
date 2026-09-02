package com.ehmjamiu.learn.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "todos")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    public Task() {
        this.status = TaskStatus.PENDING;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}