package com.omnissa.taskmanager.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    private LocalDate dueDate;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Long userId;

    // Required by JPA
    protected Task() {
    }

    public Task(String title, String description, LocalDate dueDate, String status, Long userId) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getStatus() {
        return status;
    }

    public Long getUserId() {
        return userId;
    }
}
