package com.ehmjamiu.learn.repo;

import com.ehmjamiu.learn.entity.Task;
import com.ehmjamiu.learn.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


    @Query(value = """
            SELECT t FROM Task t WHERE t.status = :status
            """)
    List<Task> getTasksByStatus(@Param("status") TaskStatus status);
}