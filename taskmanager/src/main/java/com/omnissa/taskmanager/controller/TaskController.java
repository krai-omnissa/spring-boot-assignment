package com.omnissa.taskmanager.controller;

import com.omnissa.taskmanager.model.Task;
import com.omnissa.taskmanager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<?> createTask(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody Map<String, String> request
    ) {

        String title = request.get("title");
        String description = request.get("description");
        String status = request.get("status");
        LocalDate dueDate = request.get("dueDate") != null
                ? LocalDate.parse(request.get("dueDate"))
                : null;

        if (title == null || status == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("title and status are required");
        }

        Task task = taskService.createTask(
                title,
                description,
                dueDate,
                status,
                userId
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getTasks(
            @RequestHeader("X-USER-ID") Long userId
    ) {
        return ResponseEntity.ok(taskService.getTasksForUser(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(
            @PathVariable Long id,
            @RequestHeader("X-USER-ID") Long userId
    ) {

        Optional<Task> task = taskService.getTaskByIdForUser(id, userId);

        if (task.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(task.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(
            @PathVariable Long id,
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody Map<String, String> request
    ) {

        Optional<Task> updated = taskService.updateTask(
                id,
                userId,
                request.get("title"),
                request.get("description"),
                request.get("dueDate") != null ? LocalDate.parse(request.get("dueDate")) : null,
                request.get("status")
        );

        if (updated.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(updated.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(
            @PathVariable Long id,
            @RequestHeader("X-USER-ID") Long userId
    ) {

        boolean deleted = taskService.deleteTask(id, userId);

        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.noContent().build();
    }
}
