package com.employee.crud.demo.model;


import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestBody {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String emailId;

}
