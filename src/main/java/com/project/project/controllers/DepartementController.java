package com.project.project.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.project.services.DepartementService;

@RestController
@RequestMapping("/departements")
public class DepartementController {
    private DepartementService departementService ;

    @Autowired
    public  DepartementController(DepartementService departementService)
    {
        this.departementService = departementService ;
    }

    @GetMapping("/allDepartements")
    public ResponseEntity index()
    {
        Map<String, Object> result = new HashMap<>();
        result.put("departements", departementService.getAllDepartement());
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
    
}
