package com.ghouri.todo.service;

import com.ghouri.todo.dto.TodoDto;

import java.util.List;

public interface TodoService {

    TodoDto addTodo(TodoDto todoDto);
    TodoDto getTodo(Long todoId);
    List<TodoDto> getAllTodo();
    TodoDto updateTodo(Long todoId,TodoDto todoDto);
    void deleteTodo(Long todoId);
    TodoDto completeTodo(Long todoId);
    TodoDto inCompleteTodo(Long todoId);
}
