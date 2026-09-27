package com.ehmjamiu.learn.controller;

import com.ehmjamiu.learn.dto.TaskDTO;
import com.ehmjamiu.learn.dto.TaskResponseDTO;
import com.ehmjamiu.learn.entity.Task;
import com.ehmjamiu.learn.entity.TaskStatus;
import com.ehmjamiu.learn.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalTime;
import java.util.List;


@Controller
public class TaskController {

     private final TaskService taskService;


    @Autowired
    private TaskController(JsonMapper jsonMapper,  TaskService categoryService) {
        this.taskService = categoryService;

    }

    @GetMapping("/todos/list")
    public String getAllTasks(Model model) {
        List<TaskResponseDTO> tasks = taskService.findAllByCreatedAtAsc();

        model.addAttribute("tasks", tasks);

        return "todo-list";

    }

    @GetMapping("/todos/addTask")
    public String addTask(Model model) {
        Task task = new Task();

        model.addAttribute("task", task);
        return "todo-form";

    }

    @PostMapping("/todos/saveTask")
    public String saveTask(@ModelAttribute TaskDTO task) {
        taskService.save(task);

        return "redirect:/todos/list";
    }

    @GetMapping("/todos/changeStatus")
    public String changeTaskStatus(@RequestParam("id") long id, Model model){
        Task task = taskService.findTaskById(id);

        if(task.getStatus().equals(TaskStatus.PENDING)){
           task.setStatus(TaskStatus.IN_PROGRESS);
           task.setUpdatedAt(LocalTime.now());

        } else if (task.getStatus().equals(TaskStatus.IN_PROGRESS)){
            task.setStatus(TaskStatus.COMPLETED);
            task.setUpdatedAt(LocalTime.now());
        } else {
            task.setStatus(TaskStatus.PENDING);
        }
        taskService.saveChange(task);
        model.addAttribute("task", task);


        return "redirect:/todos/list";

    }

    @GetMapping("/todos/delete")
    public String deleteTask(@RequestParam("id") long id, Model model){

        taskService.deleteById(id);

        return "redirect:/todos/list";
    }



}