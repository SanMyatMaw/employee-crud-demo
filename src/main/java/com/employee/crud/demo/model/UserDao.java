package com.employee.crud.demo.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="user-info")
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDao {

    @Id
    private String id;
    private String username;
    private String password;
}
