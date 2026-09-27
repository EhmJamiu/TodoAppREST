package com.ehmjamiu.learn.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;


@Entity
@Table(name = "todos")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String description;

    @Column(updatable = false)
    @JsonFormat(shape =JsonFormat.Shape.STRING, timezone = "UTC+1", pattern = "dd-mm-yy hh:mm a")
    private LocalTime createdAt;


    @Column(updatable = true)
    @JsonFormat(shape =JsonFormat.Shape.STRING, timezone = "UTC+1", pattern = "dd-mm-yy hh:mm a")
    private LocalTime updatedAt;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    public Task() {
        this.status = TaskStatus.PENDING;
        this.createdAt = LocalTime.now();
    }

    public LocalTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalTime updatedAt) {
        this.updatedAt = updatedAt;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}