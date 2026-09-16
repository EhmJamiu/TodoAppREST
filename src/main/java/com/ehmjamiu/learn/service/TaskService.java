package com.ehmjamiu.learn.service;

import com.ehmjamiu.learn.dto.TaskDTO;
import com.ehmjamiu.learn.dto.TaskResponseDTO;
import com.ehmjamiu.learn.entity.Task;
import com.ehmjamiu.learn.entity.TaskStatus;
import com.ehmjamiu.learn.mapper.TaskMapper;
import com.ehmjamiu.learn.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public List<Task> findAll(){
        return taskRepository.findAll();
    }

    public Task findById(long id) {

        return taskRepository.findById(id).orElse(null);
    }

    public TaskResponseDTO save(@RequestBody TaskDTO dto) {
        Task task = taskMapper.toTask(dto);
        Task savedTask = taskRepository.save(task);
        return taskMapper.taskResponseDTO(savedTask);
    }

    public Task savePatch(Task task) {
        return taskRepository.save(task);
    }

    public void deleteById(long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> getTasksByStatus(TaskStatus status) {
//        List<Task> tasks = taskRepository.getTasksByStatus(status);
//        if (tasks == null) {
//            throw new RuntimeException("No task with status: " + status);
//        }
        return taskRepository.getTasksByStatus(status);
    }


}