package com.project.project.controllers;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.project.project.requests.StoreUserLoginRequest;
import com.project.project.requests.StoreUserRequest;
import com.project.project.services.AuthService;
import com.project.project.services.UserTokenService;

import jakarta.validation.Valid;

@RestController
public class AuthController {
    private AuthService authService ; 
    private UserTokenService userTokenService;

    @Autowired
    public AuthController(AuthService authService, UserTokenService userTokenService) {
        this.authService = authService;
        this.userTokenService = userTokenService;
    }

    
    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody StoreUserRequest request)
    {
        if (!request.passwordCheck()) {
            Map<String, Object> result = new HashMap<>();
            result.put("error", "The password does not match the password confirmation");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> result = authService.register(request);
        HttpStatus status = result.containsKey("user") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(result, status);
        
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody StoreUserLoginRequest request)
    {
        Map<String, Object> result = authService.loginUser(request);
        HttpStatus status = result.containsKey("user") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(result, status);

    }

    @GetMapping("/user")
    public ResponseEntity user(@RequestHeader("Authorization") String token)
    {
        Map<String, Object> result = authService.checkUser(userTokenService.extractToken(token));
        HttpStatus status = result.containsKey("user") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(result, status);

    }



    @GetMapping("/checkcne/{cne}")
    public ResponseEntity checkCne(@PathVariable String cne)
    {
        Map<String, Object> result = authService.checkCneUser(cne);
        return new ResponseEntity<>(result, HttpStatus.OK );

    }


    @PostMapping("/logout")
    public ResponseEntity logout(@RequestHeader("Authorization") String token) 
    {
        boolean logout = authService.logoutUser(userTokenService.extractToken(token));
        Map<String, Object> result = new HashMap<>();

        if (logout) {
            result.put("message", "user logged out");
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        result.put("error", "invalid token");
        return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
    }


    
    
}
