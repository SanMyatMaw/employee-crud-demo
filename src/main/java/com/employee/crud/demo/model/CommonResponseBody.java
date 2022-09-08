package com.employee.crud.demo.model;


import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponseBody {

    private String id;
    private String status;
    private String message;
}
