package com.hsw.todo;

import com.hsw.todo.dto.AddDto;
import com.hsw.todo.dto.SetDto;

import java.util.List;
import java.util.Optional;


public interface TodoDao {
   Optional<TodoEntity> addMemory(AddDto entity);
   Optional<TodoEntity> setMemory(SetDto entity);
   Boolean deleteMemory(long id);
   List<TodoEntity> getMemories();
   Optional<TodoEntity> getMemoryById(long id);
}
