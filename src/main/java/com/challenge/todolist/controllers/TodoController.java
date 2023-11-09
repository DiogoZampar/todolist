package com.challenge.todolist.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.todolist.model.Todo;
import com.challenge.todolist.services.TodoService;


@RestController
@RequestMapping("/todo")
public class TodoController {
    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    
    @GetMapping
    public ResponseEntity<List<Todo>> getAll(){
        return ResponseEntity.ok(todoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getById(@PathVariable("id") UUID id){
        Todo todo = todoService.findById(id);
        if(todo==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        
        return ResponseEntity.ok(todo);
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo){

        Todo createdTodo = todoService.create(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
    }



    @PutMapping
    public ResponseEntity<Todo> updateTodo(@RequestBody Todo todo){
        Todo originalTodo = todoService.findById(todo.getTodoId()); //checking if original todo existed at all
        if(originalTodo == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(todoService.update(todo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Todo> deleteById(@PathVariable("id") UUID id){
        Todo originalTodo = todoService.findById(id); //checking if original todo existed at all
        if(originalTodo == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        todoService.deleteById(id);
        return ResponseEntity.ok(null);
    }
}
