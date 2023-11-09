package com.challenge.todolist.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.challenge.todolist.model.Todo;
import com.challenge.todolist.repositories.TodoRepository;



@Service
public class TodoService {
    
    private TodoRepository todoRepository;

    private TodoService(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }





    public Todo create(Todo todo){
        todo.setCreatedAt(LocalDateTime.now());
        return todoRepository.save(todo);
    }


    public List<Todo> getAll(){
        Sort sort = Sort.by("priority").descending().and(Sort.by("name").ascending());
        return todoRepository.findAll(sort);
    }

    
    public Todo findById(UUID id){
        return todoRepository.findById(id).orElse(null);
    }



    public Todo updateById(Todo todo, UUID id){
        todo.setUpdatedAt(LocalDateTime.now());
        Todo updatedTodo = todoRepository.save(todo);
        return updatedTodo;
    }



    public void deleteById(UUID id){
        todoRepository.deleteById(id);
    } 

}
