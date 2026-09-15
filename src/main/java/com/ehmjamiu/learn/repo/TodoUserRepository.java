package com.ehmjamiu.learn.repo;

import com.ehmjamiu.learn.entity.TodoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface TodoUserRepository extends JpaRepository<TodoUser, BigDecimal> {
    @Query(""" 
            SELECT t FROM TodoUser t WHERE t.username = :username""")
    TodoUser findByUsername(@Param("username") String username);
}