package com.hsw.todo;

import ch.qos.logback.classic.Logger;
import com.hsw.todo.dto.AddDto;
import com.hsw.todo.dto.SetDto;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;


@Repository
public class TodoDaoImpl implements TodoDao {
    private Map<Long,TodoEntity> memory = new ConcurrentHashMap<>();
    private AtomicLong idGenerator = new AtomicLong(0);
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(TodoDaoImpl.class);

    @Override
    public Optional<TodoEntity> addMemory(AddDto dto) {
        LOGGER.info("\n[INFO]: 메모리에 데이터 추가됨");
        Long id = idGenerator.incrementAndGet();
        TodoEntity entity = TodoEntity.builder()
                .id(id)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .sDate(dto.getSDate())
                .eDate(dto.getEDate()).build();
        memory.put(id,entity);


        return Optional.of(memory.get(id));
    }

    @Override
    public Optional<TodoEntity> setMemory(SetDto dto) {
        LOGGER.info("\n\n[INFO]: 메모리 업데이트 시작\n\n");
        TodoEntity entity = memory.get(dto.getId());
        System.out.println("\n[INFO]: " + entity);
        if(entity != null) {
            updateIfNotNull(dto.getTitle(), entity::setTitle);
            updateIfNotNull(dto.getDescription(), entity::setDescription);
            updateIfNotNull(dto.getStatus(), entity::setStatus);
            updateIfNotNull(dto.getSDate(), entity::setSDate);
            updateIfNotNull(dto.getEDate(), entity::setEDate);
            memory.put(dto.getId(), entity);
        }
        LOGGER.info("\n\n[INFO]: 객체의 내용이 업데이트 됨. {}", entity);
        return Optional.of(memory.get(dto.getId()));
    }



    @Override
    public Boolean deleteMemory(long id) {
        memory.remove(id);
        LOGGER.info("\n\n[INFO]: 메모리에서 ID: {} 객체를 삭제함\n\n",id);

        return memory.containsKey(id);
    }

    @Override
    public List<TodoEntity> getMemories() {
        LOGGER.info("Getting all memory");
        if(memory.isEmpty()){ throw new NoSuchElementException("리스트가 없습니다."); }
        List<TodoEntity> list =new ArrayList<>();
        memory.forEach((key,value)->{list.add(value);}
        );
        return list;
    }

    @Override
    public Optional<TodoEntity> getMemoryById(long id) {
        LOGGER.info("[INFO]: ID {} 값으로 객체 검색: ", id);
        return  Optional.of(memory.get(id));
    }

    private <T> void updateIfNotNull(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
