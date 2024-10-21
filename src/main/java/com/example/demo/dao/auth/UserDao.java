package com.example.demo.dao.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDao {

    int userId;
    String userName;
    String email;
    Boolean isEnable;

}
