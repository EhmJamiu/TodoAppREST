package com.ehmjamiu.learn.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Entity
public class TodoUser {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    public Integer id;

    public String username;

    public String password;


}