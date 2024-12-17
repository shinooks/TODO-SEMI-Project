package com.hsw.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Optional;

@Data
public class SetDto {
    private long id;
    private String title;
    private String description;
    private String status;
    private Date sDate;
    private Date eDate;
}
