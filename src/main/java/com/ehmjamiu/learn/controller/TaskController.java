package com.ehmjamiu.learn.controller;

import com.ehmjamiu.learn.dto.TaskDTO;
import com.ehmjamiu.learn.dto.TaskResponseDTO;
import com.ehmjamiu.learn.entity.Task;
import com.ehmjamiu.learn.entity.enums.TaskStatus;
import com.ehmjamiu.learn.service.JwtService;
import com.ehmjamiu.learn.service.TaskService;
import com.ehmjamiu.learn.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/api")
public class TaskController {

     private final TaskService taskService;
     private final JwtService jwtService;


     TaskController(TaskService taskService, JwtService jwtService) {
        this.taskService = taskService;
        this.jwtService = jwtService;

    }

    @GetMapping("/todos")
    public ResponseEntity<?> findAll(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        List<TaskResponseDTO> response = taskService.findAllTaskByUsername(username);
        return ResponseHandler.handleResponse(response, HttpStatus.OK, "tasks retrieved");
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<?> findById(@PathVariable long id, @RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

         TaskResponseDTO response = taskService.findById(id, username);
         return ResponseHandler.handleResponse(response, HttpStatus.OK, "A task retrieved");
    }

    @GetMapping("/todos/status")
    public List<Task> getTasksByStatus(@RequestParam TaskStatus status) {
        return taskService.getTasksByStatus(status);

    }

    @PostMapping("/todos")
    public ResponseEntity<?> save(@RequestBody TaskDTO dto, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        taskService.save(dto, username);
        return ResponseHandler.handleResponse(null, HttpStatus.CREATED, "A task created");
    }


//    @PatchMapping("/todos/{id}")
//    public ResponseEntity<?> patchTask (@PathVariable long id, @RequestHeader("Authorization") String authHeader, @RequestBody Map<String, Object> patchPayload) {
//        String token = authHeader.substring(7);
//        String username = jwtService.extractUsername(token);
//        Task response = taskService.patchTask(id, patchPayload, username);
//        return ResponseHandler.handleResponse(response, HttpStatus.OK, "Task updated");
//    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<?> updateTask (@PathVariable long id, @RequestHeader("Authorization") String authHeader, @RequestBody TaskDTO request) {
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        taskService.updateTask(id, request, username);
        return ResponseHandler.handleResponse(null, HttpStatus.OK, "Task updated");
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String email = jwtService.extractUsername(token);
        taskService.deleteById(id, email);
        return ResponseHandler.handleResponse("", HttpStatus.OK, "A task deleted");
    }




}