package com.example.security0912.todo.dto;

import com.example.security0912.todo.TodoEntity;
import lombok.Data;

import java.util.Date;
@Data
public class ResponseDto {
    private long id;
    private String title;
    private String description;
    private String status;
    private Date cDate;
    private Date sDate;
    private Date eDate;

    public ResponseDto(TodoEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.status = entity.getStatus();
        this.cDate = entity.getCDate();
        this.sDate = entity.getSDate();
        this.eDate = entity.getEDate();
    }
}
