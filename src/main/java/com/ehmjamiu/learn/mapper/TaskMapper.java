package com.ehmjamiu.learn.mapper;

import com.ehmjamiu.learn.dto.TaskDTO;
import com.ehmjamiu.learn.dto.TaskResponseDTO;
import com.ehmjamiu.learn.entity.Task;
import org.springframework.stereotype.Service;

@Service
public class TaskMapper {

    public Task toTask(TaskDTO dto) {
        Task task = new Task();
        task.setTitle(dto.title());
        return task;
    }

    public TaskResponseDTO taskResponseDTO(Task task) {
        return new TaskResponseDTO(
                task.getTitle(),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                task.getStatus()
        );
    }
}
