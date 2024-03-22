package com.project.project.controllers;

import java.net.http.HttpRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.project.project.requests.StoreDepartementRequest;
import com.project.project.requests.StoreFilierRequest;
import com.project.project.requests.StoreUserRequest;
import com.project.project.services.AdminService;
import com.project.project.services.UserTokenService;

import jakarta.validation.Valid;

@RestController
public class AdminController {
    private AdminService adminService ;
    private UserTokenService userTokenService ;
    @Autowired
    public AdminController(AdminService adminService , UserTokenService userTokenService){
        this.adminService = adminService ;
        this.userTokenService = userTokenService ;
    }

    @PostMapping("/storeStudent") 
    public ResponseEntity store(@RequestHeader("Authorization") String token,@Valid @RequestBody StoreUserRequest request)
    {
        if (!request.passwordCheck()) {
            Map<String, Object> result = new HashMap<>();
            result.put("error", "The password does not match the password confirmation");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        Map<String, Object> result = adminService.createStudent(userTokenService.extractToken(token) ,request);
        HttpStatus status = result.containsKey("success") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(result, status);
        
    }

    @PostMapping("/storeFilier")
    public ResponseEntity storeFilier(@RequestHeader("Authorization") String token , @Valid @RequestBody StoreFilierRequest request)
    {
        Map<String, Object> result = adminService.createFilier(userTokenService.extractToken(token) ,request);
        HttpStatus status = result.containsKey("success") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(result, status);

    }

    @PostMapping("/storeDepartement")
    public ResponseEntity storeDepartement(@RequestHeader("Authorization") String token , @Valid @RequestBody StoreDepartementRequest request)
    {
        Map<String, Object> result = adminService.createDepartement(userTokenService.extractToken(token) ,request);
        HttpStatus status = result.containsKey("success") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(result, status);

    }


    @DeleteMapping("/delete/{name}/{id}")
    public ResponseEntity delete(@RequestHeader("Authorization") String token , @PathVariable String name , @PathVariable long id)
    {
        Map<String, Object> result = adminService.deleteByName(userTokenService.extractToken(token)  , name , id);
        HttpStatus status = result.containsKey("success") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(result, status);

    }

    @PutMapping("/update/{name}/{id}")
public ResponseEntity<?> update(
        @RequestHeader("Authorization") String token,
        @PathVariable String name,
        @PathVariable long id,
        @Valid @RequestBody Object request
) 
{
        String lowercaseName = name.toLowerCase();
        Map<String, Object> responseMap = new HashMap<>();

        switch (lowercaseName) {
            case "student":
                responseMap = adminService.updateStudent(token, id, (StoreUserRequest) request);
                break;
            case "departement":
                responseMap = adminService.updateDepartement(token, id, (StoreDepartementRequest) request);
                break;
            case "filier":
                responseMap = adminService.updateFilier(token, id, (StoreFilierRequest) request);
                break;
            default:
                return new ResponseEntity<>("Invalid entity name!", HttpStatus.BAD_REQUEST);
        }

        HttpStatus status = responseMap.containsKey("success") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(responseMap, status);
}
    


    
}