package com.example.demo.service;

import com.example.demo.dto.TodoDTO;
import com.example.demo.entity.TodoEntity;
import com.example.demo.repository.TodoRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TodoService {

    protected final TodoRepository repository;

    @PostConstruct
    public void init() {
        if (repository.count() == 0) {
            log.info("No todos found in database. Creating sample todos...");
            saveTodo("Learn Java");
            saveTodo("Learn SpringBoot");
            saveTodo("Learn Rest Spring-Web");
            for(int i = 1; i < 100; i++) {
                saveTodo("Sample Todo " + i);
            }

        }
    }

    // MapStruct can be used for complex mappings, or Orika
    public TodoDTO toDto(TodoEntity src) {
        return new TodoDTO(src.getId(), src.getDescription());
    }

    public List<TodoDTO> toDtos(List<TodoEntity> src) {
        return src.stream().map(this::toDto).toList();
    }


    public TodoEntity saveTodo(String description) {
         TodoEntity e = new TodoEntity();
         e.setDescription(description);
         return repository.save(e);
    }

    public List<TodoEntity> findAll() {
        return repository.findAll();
    }

    public List<TodoDTO> getAllDTOs() {
        return toDtos(repository.findAll());
    }

    public TodoEntity getById(int id) {
        return repository.findById(id).orElse(null);
    }
    public TodoDTO getDtoById(int id) {
        val entity = getById(id);
        return entity != null ? toDto(entity) : null;
    }

    public TodoDTO createTodo(TodoDTO src) {
        TodoEntity entity = new TodoEntity();
        entity.setDescription(src.descr);
        entity = repository.save(entity);
        return toDto(entity);
    }

    public TodoDTO updateTodo(TodoDTO src) {
        val entity = repository.findById(src.id).orElseThrow();
        entity.setDescription(src.descr);
        return toDto(entity);
    }
}
