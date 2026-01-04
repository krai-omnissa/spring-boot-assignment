package com.omnissa.taskmanager.service.impl;

import com.omnissa.taskmanager.model.Task;
import com.omnissa.taskmanager.repository.TaskRepository;
import com.omnissa.taskmanager.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(String title,
                           String description,
                           LocalDate dueDate,
                           String status,
                           Long userId) {

        Task task = new Task(title, description, dueDate, status, userId);
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getTasksForUser(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    @Override
    public Optional<Task> getTaskByIdForUser(Long taskId, Long userId) {
        return taskRepository.findByIdAndUserId(taskId, userId);
    }

    @Override
    public Optional<Task> updateTask(Long taskId,
                                     Long userId,
                                     String title,
                                     String description,
                                     LocalDate dueDate,
                                     String status) {

        Optional<Task> existing = taskRepository.findByIdAndUserId(taskId, userId);

        if (existing.isEmpty()) {
            return Optional.empty();
        }

        Task task = existing.get();

        Task updated = new Task(
                title != null ? title : task.getTitle(),
                description != null ? description : task.getDescription(),
                dueDate != null ? dueDate : task.getDueDate(),
                status != null ? status : task.getStatus(),
                userId
        );

        return Optional.of(taskRepository.save(updated));
    }

    @Override
    public boolean deleteTask(Long taskId, Long userId) {
        Optional<Task> task = taskRepository.findByIdAndUserId(taskId, userId);

        if (task.isEmpty()) {
            return false;
        }

        taskRepository.delete(task.get());
        return true;
    }
}
