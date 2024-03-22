package com.project.project.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.project.services.UserService;
import com.project.project.services.UserTokenService;

@RestController
@RequestMapping("/students")
public class StudentController {
    private UserService  userService ;
    private UserTokenService userTokenService ;

    @Autowired
    public StudentController(UserService userService , UserTokenService userTokenService)
    {
        this.userService = userService ;
        this.userTokenService = userTokenService ;
    }

    @GetMapping("/allStudents")
    public ResponseEntity index(@RequestHeader("Authorization") String token)
    {
        boolean isValidToken = userTokenService.isValidToken(token);
        if (isValidToken) {
            Map<String, Object> result = new HashMap<>();
            result.put("students", userService.getAllUser());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        

    }
    
}
