package com.project.project.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.project.services.FilierService;

@RestController
@RequestMapping("/filiers")
public class FilierController {
    private FilierService filierService ;

    @Autowired 
    public FilierController(FilierService filierService)
    {
        this.filierService = filierService ;
    }

    @GetMapping("/allFiliers")
    public ResponseEntity index()
    {
        Map<String, Object> result = new HashMap<>();
        result.put("filiers", filierService.getAllFilier());
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
    
}
