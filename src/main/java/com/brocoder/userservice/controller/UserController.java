package com.brocoder.userservice.controller;

import com.brocoder.userservice.entity.User;
import com.brocoder.userservice.model.UserRequest;
import com.brocoder.userservice.model.UserResponse;
import com.brocoder.userservice.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Log4j2
@CrossOrigin("http://localhost:4200/")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Long> signUp(@RequestBody UserRequest userRequest){
        return new ResponseEntity<>(userService.signUp(userRequest), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> requestMap){
        log.info("Inside login Controller of login {}");
        return new ResponseEntity<>(userService.login(requestMap),HttpStatus.ACCEPTED);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        log.info("Inside getAllUsers{}");
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{email}")
    public ResponseEntity<String> deleteUser(@RequestParam String email ){
        return new ResponseEntity<>(userService.deleteUserByEmail(email),HttpStatus.OK);
    }

}
