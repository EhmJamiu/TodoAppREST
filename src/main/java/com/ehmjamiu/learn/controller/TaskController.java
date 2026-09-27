package com.ehmjamiu.learn.controller;

import com.ehmjamiu.learn.dto.TaskDTO;
import com.ehmjamiu.learn.dto.TaskResponseDTO;
import com.ehmjamiu.learn.entity.Task;
import com.ehmjamiu.learn.entity.TaskStatus;
import com.ehmjamiu.learn.exceptionHandler.ErrorResponse;
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

     private final TaskService taskService;


    @Autowired
    private TaskController(JsonMapper jsonMapper,  TaskService categoryService) {
        this.taskService = categoryService;

    }

    @GetMapping("/todos")
    public List<TaskResponseDTO> findAll() {
        return taskService.findAll();
    }

    @GetMapping("/todos/{id}")
    public TaskResponseDTO findById(@PathVariable long id){
        return taskService.findById(id);
    }

    @GetMapping("/todos/status")
    public List<Task> getTasksByStatus(@RequestParam TaskStatus status) {
        return taskService.getTasksByStatus(status);

    }

    @PostMapping("/todos")
    public TaskResponseDTO save(@RequestBody TaskDTO dto) {
        return taskService.save(dto);
    }


    @PatchMapping("/todos/{id}")
    public Task patchTask (@PathVariable long id, @RequestBody Map<String, Object> patchPayload) {
        return taskService.patchTask(id, patchPayload);
    }

    @DeleteMapping("/todos/{id}")
    public void deleteById(@PathVariable long id) {
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