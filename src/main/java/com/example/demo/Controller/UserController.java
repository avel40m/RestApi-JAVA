package com.example.demo.Controller;

import com.example.demo.Entity.Users;
import com.example.demo.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @GetMapping("/")
    public ResponseEntity<List<Users>> listUser(){
        List<Users>  users = userService.listUser();
        log.warn("List of users");
        return ResponseEntity.ok(users);
    }

    @PostMapping("/create")
    public ResponseEntity<Users> createUser(@RequestBody Users users){
        try {
            Users u = userService.createUser(users);
            log.warn("Create a user with id " + u.getId());
            return ResponseEntity.ok(u);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<Users> editUser(@RequestBody Users users){
        try {
            Users u = userService.editUser(users);
            log.info("Edit user the id " + users.getId());
            return ResponseEntity.ok(u);
        } catch (Exception e){
            log.error("Error: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id){
        try {
            userService.deleteUser(id);
            log.warn("the id is removed " + id);
            return ResponseEntity.ok("Deleted correctly");
        } catch (Exception e){
            log.error("Error " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
