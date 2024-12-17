package com.example.security0912.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddDto {
    private String title;
    private String description;
    private String status;
    private Date sDate;
    private Date eDate;
}
