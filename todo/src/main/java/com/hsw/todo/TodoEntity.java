package com.hsw.todo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class TodoEntity {
    private final long id;
    private String title;
    private String description;
    private String status;
    private final Date cDate=new Date(System.currentTimeMillis());
    private Date sDate;
    private Date eDate;

}
