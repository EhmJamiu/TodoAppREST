package com.ehmjamiu.learn.service;

import com.ehmjamiu.learn.dto.TaskDTO;
import com.ehmjamiu.learn.dto.TaskResponseDTO;
import com.ehmjamiu.learn.entity.Task;
import com.ehmjamiu.learn.entity.TaskStatus;
import com.ehmjamiu.learn.exceptionHandler.TodoNotFoundException;
import com.ehmjamiu.learn.mapper.TaskMapper;
import com.ehmjamiu.learn.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final JsonMapper jsonMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper, JsonMapper jsonMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.jsonMapper = jsonMapper;
    }

    public List<TaskResponseDTO> findAll(){
        return taskRepository.findAll()
                .stream()
                .map(task -> taskMapper.toTaskResponseDTO(task))
                .collect(Collectors.toList());
    }

    public TaskResponseDTO findById(@PathVariable long id) {
        Task task = taskRepository.findById(id).orElse(null);

        if (task == null) {
            throw new TodoNotFoundException("Task with id: " + id + " does not exist");
        }
        return taskMapper.toTaskResponseDTO(task);
    }

    public Task findTaskById(@PathVariable long id) {
        Task task = taskRepository.findById(id).orElse(null);

        if (task == null) {
            throw new TodoNotFoundException("Task with id: " + id + " does not exist");
        }
        return task;
    }

    public TaskResponseDTO save(@RequestBody TaskDTO dto) {
        Task task = taskMapper.toTask(dto);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toTaskResponseDTO(savedTask);
    }

    public void saveChange(@RequestBody Task task) {
        taskRepository.save(task);
    }

    public void deleteById(@PathVariable long id) {
        var task = findById(id);
        if(task == null) {
            throw new TodoNotFoundException("Task with id: " +id+ " does not exist");
        }
        taskRepository.deleteById(id);
    }


    public Task savePatch(Task patchedTask) {
        return taskRepository.save(patchedTask);
    }

    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.getTasksByStatus(status);
    }

    public Task patchTask (@PathVariable long id, @RequestBody Map<String, Object> patchPayload) {
        Task existingTask = findTaskById(id);

        if (existingTask == null) {
            throw new TodoNotFoundException("Task with id: " +id+ " does not exist");
        } else {
            existingTask.setUpdatedAt(LocalTime.now());
        }

        if (patchPayload.containsKey("id")) {
            throw new RuntimeException("Task id not allow in the request body - " + id);
        }

        Task patchedTask = jsonMapper.updateValue(existingTask , patchPayload);
        return savePatch(patchedTask);
    }

    public List<TaskResponseDTO> findAllByCreatedAtAsc() {
        return taskRepository.findAllByCreatedAtAsc();
    }
}
