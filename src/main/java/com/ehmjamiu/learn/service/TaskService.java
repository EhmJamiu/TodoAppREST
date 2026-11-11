package com.ehmjamiu.learn.service;


import com.ehmjamiu.learn.dto.TaskDTO;
import com.ehmjamiu.learn.dto.TaskResponseDTO;
import com.ehmjamiu.learn.entity.Task;
import com.ehmjamiu.learn.entity.enums.TaskStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface TaskService {

    List<TaskResponseDTO> findAllTaskByUsername(String username);

    TaskResponseDTO findById(long id, String username);

    Task findTaskById(long id);

    void save(TaskDTO dto, String username);

    void deleteById(long id, String username);

    Task savePatch(Task patchedTask);

    Task patchTask (long id, Map<String, Object> patchPayload, String username);

    List<Task> getTasksByStatus(TaskStatus status, String username);

    void updateTask(long id, TaskDTO request, String username);
}
