package com.ghouri.todo.controller;

import com.ghouri.todo.dto.TodoDto;
import com.ghouri.todo.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("api/todos")
public class TodoController {

    private TodoService todoService;

    //Build add Todo Rest Api
    //http://localhost:9999/api/todos
    //@PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping
    public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto){
        TodoDto savedTodoDto=todoService.addTodo(todoDto);
        return new ResponseEntity<>(savedTodoDto, HttpStatus.CREATED);
    }

    //Build get Todo Rest Api
    //http://localhost:9999/api/todos/1
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("{id}")
    public ResponseEntity<TodoDto> getTodo(@PathVariable("id") Long todoId){
        TodoDto todoDto=todoService.getTodo(todoId);
        return ResponseEntity.ok(todoDto);
    }

    //Build get All Todo Rest Api
    //http://localhost:9999/api/todos
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<TodoDto>> getAllTodos(){
        return ResponseEntity.ok(todoService.getAllTodo());
    }

    //Build update Todo Rest Api
    //http://localhost:9999/api/todos
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<TodoDto> updateTodo(@PathVariable("id") Long todoId,@RequestBody TodoDto todoDto){
        TodoDto updatedTodoDto=todoService.updateTodo(todoId,todoDto);
        return ResponseEntity.ok(updatedTodoDto);
    }

    //Build delete Todo Rest Api
    //http://localhost:9999/api/todos/1
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable("id") Long todoId){
        todoService.deleteTodo(todoId);
        return ResponseEntity.ok("Todo deleted successfully!.");
    }

    //Build complete Todo Rest Api
    //http://localhost:9999/api/todos/1/complete
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("{id}/complete")
    public ResponseEntity<TodoDto> completeTodo(@PathVariable("id") Long todoId){
        TodoDto updatedTodoDto=todoService.completeTodo(todoId);
        return ResponseEntity.ok(updatedTodoDto);
    }

    //Build in complete Todo REst Api
    //http://localhost:9999/api/todos/1/in-complete
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("{id}/in-complete")
    public ResponseEntity<TodoDto> inCompleteTodo(@PathVariable("id") Long todoId){
        TodoDto updatedTodoDto=todoService.inCompleteTodo(todoId);
        return ResponseEntity.ok(updatedTodoDto);
    }

}
