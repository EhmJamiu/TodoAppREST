package com.ehmjamiu.learn.service;

import com.ehmjamiu.learn.entity.Task;
import com.ehmjamiu.learn.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll(){
        return taskRepository.findAll();
    }

    public Task findById(long id) {

        return taskRepository.findById(id).orElse(null);
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public void deleteById(long id) {
        taskRepository.deleteById(id);
    }
}