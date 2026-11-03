package com.ehmjamiu.learn.service.Impl;

import com.ehmjamiu.learn.dto.TaskDTO;
import com.ehmjamiu.learn.dto.TaskResponseDTO;
import com.ehmjamiu.learn.entity.Task;
import com.ehmjamiu.learn.entity.TodoUser;
import com.ehmjamiu.learn.entity.enums.TaskStatus;
import com.ehmjamiu.learn.exceptionHandler.CustomBadRequest;
import com.ehmjamiu.learn.exceptionHandler.TodoNotFoundException;
import com.ehmjamiu.learn.exceptionHandler.UserDoesNotExist;
import com.ehmjamiu.learn.exceptionHandler.UserNotFound;
import com.ehmjamiu.learn.mapper.TaskMapper;
import com.ehmjamiu.learn.repo.TaskRepository;
import com.ehmjamiu.learn.repo.TodoUserRepository;
import com.ehmjamiu.learn.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class TaskServiceImpl implements TaskService{
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final JsonMapper jsonMapper;
    private final TodoUserRepository todoUserRepository;


    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, JsonMapper jsonMapper, TodoUserRepository todoUserRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.jsonMapper = jsonMapper;
        this.todoUserRepository = todoUserRepository;
    }

    @Override
    public List<TaskResponseDTO> findAllTaskByUsername(String username){
        if(username==null) throw new CustomBadRequest("User is Null");

        TodoUser user = todoUserRepository.findByUsername(username).orElseThrow(()
                -> new UserNotFound("Complete user details"));
        List<Task> tasks = taskRepository.findTasksByUser(user);

        return tasks.stream()
                .map(taskMapper::toTaskResponseDTO)
                .toList();

    }

    public TaskResponseDTO findById(@PathVariable long id, String username) {
        if(username==null) throw new CustomBadRequest("User is Null");

        TodoUser user = todoUserRepository.findByUsername(username).orElseThrow(()
                -> new UserNotFound("Complete user details"));
        Task task = taskRepository.findByIdAndUser(id, user).orElseThrow(() -> new TodoNotFoundException("Task not found"));
        return taskMapper.toTaskResponseDTO(task);
    }

    public Task findTaskById(@PathVariable long id) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task == null) {
            throw new TodoNotFoundException("Task with id: " + id + " does not exist");
        }
        return task;
    }

    public void save(@RequestBody @Valid TaskDTO dto, String username) {
        if(username==null) throw new CustomBadRequest("User is Null");
        if(dto.title() == null || dto.title().isEmpty()) throw new CustomBadRequest("Task title is required");

        TodoUser user = todoUserRepository.findUserByUsername(username);
        if(user == null) throw new UserDoesNotExist(username + " user, is not exist ");

        Task task = taskMapper.toTask(dto);
        task.setUser(user);
        Task savedTask = taskRepository.save(task);
        taskMapper.toTaskResponseDTO(savedTask);
    }

    public void deleteById(@PathVariable long id, String username) {
        if(username==null) throw new CustomBadRequest("User is Null");
        TodoUser user = todoUserRepository.findUserByUsername(username);
        if(user == null) throw new UserDoesNotExist(username + " user, is not exist ");
        Task task = taskRepository.findTaskById(id);
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

    @Override
    public void updateTask(long id, @Valid TaskDTO request, String username) {
        if(request.title() == null || request.title().isEmpty()) throw new CustomBadRequest("Task tile is required");
        Task existingTask = taskRepository.findById(id).orElseThrow(()
                -> new TodoNotFoundException());
        taskRepository.save(existingTask);
    }

    public Task patchTask (@PathVariable long id, @RequestBody Map<String, Object> patchPayload, String username) {
        if (username == null) throw new CustomBadRequest("User details required");

        Task existingTask = taskRepository.findTaskById(id);

        if (existingTask == null) {
            throw new TodoNotFoundException("Task with id: " +id+ " does not exist");
        } else {
            existingTask.setUpdatedAt(LocalDateTime.now());
        }

        if (patchPayload.containsKey("id")) {
            throw new RuntimeException("Task id not allow in the request body - " + id);
        }

        Task patchedTask = jsonMapper.updateValue(existingTask , patchPayload);
        return savePatch(patchedTask);
    }
}