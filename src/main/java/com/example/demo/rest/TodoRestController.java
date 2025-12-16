package com.example.demo.rest;

import com.example.demo.dto.TodoDTO;
import com.example.demo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/todo")
@RequiredArgsConstructor
public class TodoRestController {

    private final TodoService delegate;

    // READ - Get all todos
    @GetMapping
    public List<TodoDTO> getAll() {
        return delegate.getAllDTOs();
    }

    // READ - Get todo by ID
    @GetMapping("/{id}")
    public ResponseEntity<TodoDTO> getById(@PathVariable int id) {
        TodoDTO todo = delegate.getDtoById(id);
        if (todo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(todo);
    }

    // CREATE - Add new todo
    @PostMapping
    public ResponseEntity<TodoDTO> create(@RequestBody TodoDTO todo) {
        if (todo == null) {
            return ResponseEntity.badRequest().build();
        }
        val res = delegate.createTodo(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    // UPDATE - Update existing todo
    @PutMapping()
    public ResponseEntity<TodoDTO> update(@RequestBody TodoDTO updatedTodo) {
        if (updatedTodo == null) {
            return ResponseEntity.badRequest().build();
        }
        val res = delegate.updateTodo(updatedTodo);
        return ResponseEntity.ok(res);
    }

//    // DELETE - Delete todo by ID
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable int id) {
//        boolean removed = todos.removeIf(t -> t.id == id);
//        if (removed) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.notFound().build();
//    }

//    // DELETE - Delete all todos
//    @DeleteMapping
//    public ResponseEntity<Void> deleteAll() {
//        todos.clear();
//        return ResponseEntity.noContent().build();
//    }
}