package com.example.security0912;

import com.example.security0912.todo.dto.AddDto;
import com.example.security0912.todo.dto.SetDto;
import com.example.security0912.todo.TodoDaoImpl;
import com.example.security0912.todo.TodoEntity;
import org.junit.jupiter.api.*;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;

class TodoDaoImplTest {
    // 메모리에 저장되는지, 실제 객체 이용
    static TodoDaoImpl dao= new TodoDaoImpl();

    @BeforeEach
    void addMemory() {
        //given
        AddDto entity = AddDto.builder()
                .title("테스트 시작")
                .description("0번에 데이터가 추가됩니다")
                .status("시작")
                .sDate(new Date(System.currentTimeMillis()))
                .eDate(new Date(System.currentTimeMillis()+1000*60))
                .build();

        //when
        Optional<TodoEntity> result = dao.addMemory(entity);

        //then
        System.out.println("데이터 추가하기");
        try {
            System.out.println("Result entity: " + result
                    .orElseThrow(()->{return new InputMismatchException("잘못된 입력");})
            );
        }catch(InputMismatchException e){
            e.printStackTrace();
        }
       Assertions.assertTrue(result.isPresent());
    }

    @AfterEach
    void deleteMemory() {
        System.out.println("\n 데이터 삭제 \n");

        Assertions.assertFalse(()->dao.deleteMemory(0));
    }

    @Test
    void setMemory() {
        //given
        SetDto entity = new SetDto();
        entity.setId(1);
        entity.setTitle("수정 테스트");
        entity.setDescription("데이터 수정을 테스트합니다");
        entity.setStatus("진행중");
        //when
        TodoEntity result  = dao.setMemory(entity).orElseThrow(()-> new InputMismatchException("수정안됨"));
        //then
        System.out.println("데이터 수정하기");
        Assertions.assertNotNull(result);

    }


    @Test
    void getMemories() {
        System.out.println("\n리스트 가져오기 시작\n");
        List<TodoEntity> memories = dao.getMemories();
        for(TodoEntity entity : memories){
            System.out.println("가져온거"+entity);
        }
    }
@Disabled
    @Test
    void getMemoryById() {
    }
}