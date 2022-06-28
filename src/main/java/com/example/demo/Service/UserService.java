package com.example.demo.Service;

import com.example.demo.Entity.Users;

import java.util.List;

public interface UserService {
    List<Users> listUser();
    Users createUser(Users users);
    Users editUser(Users users);
    void deleteUser(String id);
}
