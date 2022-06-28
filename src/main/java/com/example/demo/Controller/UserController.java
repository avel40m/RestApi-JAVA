package com.example.demo.Controller;

import com.example.demo.Entity.Users;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<Users>> listUser(){
        List<Users>  users = userService.listUser();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/create")
    public ResponseEntity<Users> createUser(@RequestBody Users users){
        Users u = userService.createUser(users);
        return ResponseEntity.ok(u);
    }

    @PutMapping("/edit")
    public ResponseEntity<Users> editUser(@RequestBody Users users){
        Users u = userService.editUser(users);
        return ResponseEntity.ok(u);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return ResponseEntity.ok("Deleted correctly");
    }
}
