package com.example.security0912.todo;

import com.example.security0912.todo.dto.AddDto;
import com.example.security0912.todo.dto.ResponseDto;
import com.example.security0912.todo.dto.SetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

@Service
public class TodoService {
    private TodoDao dao;
    @Autowired
    public TodoService(TodoDao dao){
        this.dao = dao;
    }

    public ResponseDto addTodo(AddDto dto){
        return new ResponseDto(
                dao.addMemory(dto)
                .orElseThrow(()->
                        new IllegalAccessError("데이터 등록에 실패하였습니다.")));
    }

    public ResponseDto setTodo(SetDto dto){
        return new ResponseDto(
                dao.setMemory(dto)
                .orElseThrow(()->
                        new IllegalAccessError("잘못된 입력입니다.")));
    }

    public List<ResponseDto> getTodoList(){
        List<ResponseDto> list = new ArrayList<>();
       for (TodoEntity entity: dao.getMemories()){
           list.add(new ResponseDto(entity));
       }
       return list;
    }

    public ResponseDto getTodoById(Long id){
        return new ResponseDto(
                dao.getMemoryById(id)
                .orElseThrow(()->new InputMismatchException("없는 ID 입니다."))
        );
    }

    public boolean deleteTodo(Long id){
        return dao.deleteMemory(id);
    }
}
