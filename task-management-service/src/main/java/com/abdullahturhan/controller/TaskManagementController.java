package com.abdullahturhan.controller;

import com.abdullahturhan.dto.CreateTaskRequest;
import com.abdullahturhan.dto.SearchTaskResponse;
import com.abdullahturhan.exception.TaskNotFoundException;
import com.abdullahturhan.exception.UserNotFoundOrUserRoleIsNotValidException;
import com.abdullahturhan.service.TaskManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/tasks")
@Validated
public class TaskManagementController {
    private final TaskManagementService managementService;

    public TaskManagementController(TaskManagementService managementService) {
        this.managementService = managementService;
    }
    @GetMapping(path = "/label")
    public ResponseEntity<List<SearchTaskResponse>> getTaskByLabel(@RequestParam("label") String label) throws TaskNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(managementService.getTaskByLabel(label));
    }

    @PostMapping(path = "/create")
    public ResponseEntity<String> createTask(@RequestBody CreateTaskRequest request) throws UserNotFoundOrUserRoleIsNotValidException {
        return ResponseEntity.status(HttpStatus.CREATED).body(managementService.createAndAssignTask(request));
    }
    @PatchMapping(path = "/update")
    public void updateTask(@RequestParam("id") String id) throws TaskNotFoundException {
        managementService.updateTaskToCompleted(id);
    }
    @DeleteMapping(path = "/delete")
    public void deleteTask(@RequestParam("id") String id) throws TaskNotFoundException {
        managementService.deleteTaskById(id);
    }
}
