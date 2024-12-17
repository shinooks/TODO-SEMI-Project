package com.hsw.todo;

import ch.qos.logback.classic.Logger;
import com.hsw.todo.dto.AddDto;
import com.hsw.todo.dto.ResponseDto;
import com.hsw.todo.dto.SetDto;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static jakarta.servlet.http.HttpServletResponse.*;

@RestController
public class TodoController {
    private Logger LOGGER = (Logger) LoggerFactory.getLogger(TodoController.class);
    private TodoService service;
    @Autowired
    public TodoController(TodoService service){
        this.service = service;
    }

    @GetMapping("/todo/list")
    public ResponseEntity<List> getList(){
        LOGGER.info("[INFO]: /todo/list로 Get 요청\n");
        List<ResponseDto> todoList = service.getTodoList();
        LOGGER.info("[Result]:{}",todoList);
        return ResponseEntity.ok(todoList);
    }

    @PostMapping("/todo/add")
    public ResponseEntity addTodo(@RequestBody AddDto dto){
        LOGGER.info("[INFO]: /todo/add로 Post 요청\n");
        ResponseDto res = service.addTodo(dto);
        LOGGER.info("[RESULT]:{}",res);
        if (res!=null){
            return ResponseEntity.status(SC_CREATED).body(res);
        }
        String error = "생성 실패";
        return ResponseEntity.status(SC_INTERNAL_SERVER_ERROR).body(error);
    }


    @PutMapping("/todo/edit")
    public ResponseEntity setTodo(@RequestBody SetDto dto){
        LOGGER.info("[INFO]: /todo/edit로 Put 요청");
        ResponseDto res = service.setTodo(dto);
        LOGGER.info("[RESULT]:{}",res);
        if (res!=null){
            return ResponseEntity.status(SC_CREATED).body(res);
        }
        String error = "생성 실패";
        return ResponseEntity.status(SC_INTERNAL_SERVER_ERROR).body(error);
    }

    @DeleteMapping("/todo/delete")
    public ResponseEntity deleteTodo(@RequestParam long id){
        LOGGER.info("[INFO]: /todo/delete로 Delete 요청");
        boolean result = service.deleteTodo(id);
        LOGGER.info("[RESULT]:{}",result);
        if(result){
            return ResponseEntity.status(SC_NOT_ACCEPTABLE).build();
        }
        else
            return ResponseEntity.status(SC_OK).build();
    }
}
