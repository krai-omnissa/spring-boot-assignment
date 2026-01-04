package com.omnissa.taskmanager.service;

import com.omnissa.taskmanager.model.Task;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskService {

    Task createTask(
            String title,
            String description,
            LocalDate dueDate,
            String status,
            Long userId
    );

    List<Task> getTasksForUser(Long userId);

    Optional<Task> getTaskByIdForUser(Long taskId, Long userId);

    Optional<Task> updateTask(
            Long taskId,
            Long userId,
            String title,
            String description,
            LocalDate dueDate,
            String status
    );

    boolean deleteTask(Long taskId, Long userId);
}
