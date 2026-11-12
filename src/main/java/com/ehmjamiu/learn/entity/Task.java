package com.ehmjamiu.learn.entity;

import com.ehmjamiu.learn.entity.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Entity
@Data
@Table(name = "todos")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private TodoUser user;

    @Column(updatable = false)
    @JsonFormat(shape =JsonFormat.Shape.STRING, timezone = "UTC+1", pattern = "dd-mm-yy hh:mm a")
    private LocalDateTime createdAt;


    @Column
    @JsonFormat(shape =JsonFormat.Shape.STRING, timezone = "UTC+1", pattern = "dd-mm-yy hh:mm a")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    public Task() {
        this.status = TaskStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }


}