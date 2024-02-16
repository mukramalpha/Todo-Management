package com.ghouri.todo.service.impl;

import com.ghouri.todo.dto.TodoDto;
import com.ghouri.todo.entity.Todo;
import com.ghouri.todo.exception.ResourceNotFoundException;
import com.ghouri.todo.repository.TodoRepository;
import com.ghouri.todo.service.TodoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {
    private TodoRepository todoRepository;
    private ModelMapper modelMapper;
    @Override
    public TodoDto addTodo(TodoDto todoDto) {
        //convert TodoDto into Todo Jpa Entity
        Todo todo=modelMapper.map(todoDto,Todo.class);

        //save Todo Jpa entity into db
        Todo savedTodo=todoRepository.save(todo);

        //convert saved Todo Jpa entity into TodoDto class
        TodoDto savedTodoDto=modelMapper.map(savedTodo,TodoDto.class);

        return savedTodoDto;
    }

    @Override
    public TodoDto getTodo(Long todoId) {
        Todo todo=todoRepository.findById(todoId).orElseThrow(()->new ResourceNotFoundException("Todo does not exist with given id="+todoId));
        return modelMapper.map(todo,TodoDto.class);
    }

    @Override
    public List<TodoDto> getAllTodo() {
        List<Todo> todos=todoRepository.findAll();
        List<TodoDto> todoDtos=todos.stream().map((todo)->modelMapper.map(todo,TodoDto.class)).collect(Collectors.toList());
        return todoDtos;
    }

    @Override
    public TodoDto updateTodo(Long todoId, TodoDto todoDto) {
        Todo todo=todoRepository.findById(todoId).orElseThrow(()->new ResourceNotFoundException("Todo does not found with given id="+todoId));
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());
        Todo updatedTodo=todoRepository.save(todo);
        return modelMapper.map(updatedTodo,TodoDto.class);
    }

    @Override
    public void deleteTodo(Long todoId) {
        Todo todo=todoRepository.findById(todoId).orElseThrow(()->new ResourceNotFoundException("Todo not found with id="+todoId));
        todoRepository.delete(todo);
    }

    @Override
    public TodoDto completeTodo(Long todoId) {
        Todo todo=todoRepository.findById(todoId).orElseThrow(()->new ResourceNotFoundException("Todo not found with given id="+todoId));
        todo.setCompleted(Boolean.TRUE);
        Todo updatedTodo=todoRepository.save(todo);
        return modelMapper.map(updatedTodo,TodoDto.class);
    }

    @Override
    public TodoDto inCompleteTodo(Long todoId) {
        Todo todo=todoRepository.findById(todoId).orElseThrow(()->new ResourceNotFoundException("Todo not found with id="+todoId));
        todo.setCompleted(Boolean.FALSE);
        Todo updatedTodo=todoRepository.save(todo);
        return modelMapper.map(updatedTodo,TodoDto.class);
    }
}
