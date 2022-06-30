package com.example.demo.Controller;

import antlr.ASdebug.TokenOffsetInfo;
import com.example.demo.Config.JwtUtilService;
import com.example.demo.Config.TokenInfo;
import com.example.demo.Config.UsuarioDetailsService;
import com.example.demo.Config.AuthenticationReq;
import com.example.demo.Entity.Users;
import com.example.demo.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioDetailsService usuarioDetailsService;
    @Autowired
    private JwtUtilService jwtUtilService;
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

            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
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

    @PostMapping("/public/authenticate")
    public ResponseEntity<TokenInfo> authenticate(@RequestBody AuthenticationReq authenticationReq){
        log.info("Authenticate user {}",authenticationReq.getUsuario());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationReq.getUsuario(),authenticationReq.getClave()));

        final UserDetails userDetails = usuarioDetailsService.loadUserByUsername(authenticationReq.getUsuario());

        final String jwt = jwtUtilService.generateToken(userDetails);

        TokenInfo tokenInfo = new TokenInfo(jwt);

        return ResponseEntity.ok(tokenInfo);

    }

}
