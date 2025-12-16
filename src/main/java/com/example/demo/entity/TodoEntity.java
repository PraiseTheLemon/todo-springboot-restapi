package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class TodoEntity {

    @Id @GeneratedValue
    protected int id;

    protected String description;
}
