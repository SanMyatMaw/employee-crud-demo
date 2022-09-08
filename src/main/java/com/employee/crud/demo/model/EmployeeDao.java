package com.employee.crud.demo.model;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Document(collection="employee")
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDao {

    @Id
    private String id;

    @NotBlank
    @Size(max = 100)
    @Indexed(unique = true)
    private String firstName;

    private String lastName;

    @NotBlank
    @Size(max = 100)
    @Indexed(unique = true)
    private String emailId;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
