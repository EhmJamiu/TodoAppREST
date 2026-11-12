package com.ehmjamiu.learn.repo;

import com.ehmjamiu.learn.entity.Task;
import com.ehmjamiu.learn.entity.TodoUser;
import com.ehmjamiu.learn.entity.enums.TaskStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.lang.ScopedValue;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


    @Query(value = """
            SELECT t FROM Task t WHERE t.status = :status
            """)
    List<Task> getTasksByStatus(@Param("status") TaskStatus status);


    List<Task> findTasksByUser(TodoUser user);

    Optional<Task> findByIdAndUser(long id, TodoUser user);

    Task findTaskById(long id);


    @Transactional
    @Modifying
    void deleteByIdAndUser(long id, TodoUser user);

    TodoUser user(TodoUser user);

    List<Task> getTasksByStatusAndUser(TaskStatus status, TodoUser user);
}