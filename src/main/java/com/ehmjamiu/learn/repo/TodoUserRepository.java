package com.ehmjamiu.learn.repo;

import com.ehmjamiu.learn.entity.TodoUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface TodoUserRepository extends JpaRepository<TodoUser, BigDecimal> {
    @Query(""" 
            SELECT t FROM TodoUser t WHERE t.username = :username""")
    Optional<TodoUser> findByUsername(@Param("username") String username);


    Optional<TodoUser> findTodoUserByUsername(String username);

    @Query(""" 
            SELECT t FROM TodoUser t WHERE t.username = :username""")
    TodoUser findUserByUsername(@Param("username") String username);
}