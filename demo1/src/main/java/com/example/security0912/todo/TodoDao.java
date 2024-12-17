package com.example.security0912.todo;

import com.example.security0912.todo.dto.AddDto;
import com.example.security0912.todo.dto.SetDto;

import java.util.List;
import java.util.Optional;


public interface TodoDao {
   Optional<TodoEntity> addMemory(AddDto entity);
   Optional<TodoEntity> setMemory(SetDto entity);
   Boolean deleteMemory(long id);
   List<TodoEntity> getMemories();
   Optional<TodoEntity> getMemoryById(long id);
}
