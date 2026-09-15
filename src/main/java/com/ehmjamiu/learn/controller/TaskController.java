package com.ehmjamiu.learn.controller;

import com.ehmjamiu.learn.entity.Task;
import com.ehmjamiu.learn.entity.TaskStatus;
import com.ehmjamiu.learn.exceptionHandler.ErrorResponse;
import com.ehmjamiu.learn.exceptionHandler.TodoNotFoundException;
import com.ehmjamiu.learn.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/api")
public class TaskController {

    private final JsonMapper jsonMapper;

    private final TaskService taskService;

    @Autowired
    private TaskController(JsonMapper jsonMapper, TaskService categoryService) {
        this.jsonMapper = jsonMapper;
        this.taskService = categoryService;
    }

    @GetMapping("/todos")
    public List<Task> findAll() {
        return taskService.findAll();
    }

    @GetMapping("/todos/{id}")
    public Task findById(@PathVariable long id){
        Task task = taskService.findById(id);

        if (task == null) {
            throw new TodoNotFoundException("Task with id: " +id+ " does not exist");

        }
        return task;
    }

    @GetMapping("/todos/status")
    public List<Task> getTasksByStatus(@RequestParam TaskStatus status) {
        return taskService.getTasksByStatus(status);

    }

    @PostMapping("/todos")
    public Task save(@RequestBody Task task) {
        return taskService.save(task);
    }


    @PatchMapping("/todos/{id}")
    public Task patchTask (@PathVariable long id, @RequestBody Map<String, Object> patchPayload
                           ) {
        Task existingTask = taskService.findById(id);

        if (existingTask == null) {
            throw new TodoNotFoundException("Task with id: " +id+ " does not exist");
        }

        if (patchPayload.containsKey("id")) {
            throw new RuntimeException("Task id not allow in the request body - " + id);
        }

        Task patchedTask = jsonMapper.updateValue(existingTask , patchPayload);
        return taskService.save(patchedTask);
    }

    @DeleteMapping("/todos/{id}")
    public void deleteById(@PathVariable long id) {

        Task task = taskService.findById(id);
        if(task == null) {
            throw new TodoNotFoundException("Task with id: " +id+ " does not exist");
        }

        taskService.deleteById(id);
    }



    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }


}