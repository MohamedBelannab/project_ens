package com.project.project.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.project.dto.UserDto;
import com.project.project.models.Departement;
import com.project.project.models.Filier;
import com.project.project.models.User;
import com.project.project.repositories.DepartementRepo;
import com.project.project.repositories.FilierRepo;
import com.project.project.repositories.UserRepo;
import com.project.project.requests.StoreDepartementRequest;
import com.project.project.requests.StoreFilierRequest;
import com.project.project.requests.StoreUserRequest;

import jakarta.validation.Valid;

@Service
public class AdminService {
    private UserTokenService userTokenService;
    private UserRepo userRepo ; 
    private FilierRepo filierRepo ;
    private DepartementRepo departementRepo ;

    @Autowired
    public AdminService(UserTokenService userTokenService ,DepartementRepo departementRepo ,  FilierRepo filierRepo , UserRepo userRepo){

        this.userTokenService = userTokenService;
        this.userRepo = userRepo ;
        this.filierRepo = filierRepo ;
        this.departementRepo = departementRepo ;


    }

    public Map<String, Object> createStudent(String token, StoreUserRequest request) {
        UserDto user = userTokenService.getUserOfToken(token);
        Map<String, Object> responseMap = new HashMap<>();
        
        if (user == null || !user.isAdmin()) {
            responseMap.put("error", "You have no permission to do this action!");
            return responseMap;
        }
    
        Optional<User> oldUser = userRepo.findByCne(request.getCne());
        if (oldUser.isPresent()) {
            responseMap.put("error", "The CNE already exists");
            return responseMap;
        }
    
        User student = userRepo.save(User.toUser(request));
        responseMap.put("success", "Student created!");
        return responseMap;
    }


    public Map<String, Object> createFilier(String token, StoreFilierRequest request) {
        UserDto user = userTokenService.getUserOfToken(token);
        Map<String, Object> responseMap = new HashMap<>();
        
        if (user == null || !user.isAdmin()) {
            responseMap.put("error", "You have no permission to do this action!");
            return responseMap;
        }
    
        Optional<Filier> oldFilier = filierRepo.findByNomFilier(request.getNom_filier());
        if (oldFilier.isPresent()) {
            responseMap.put("error", "The Filier already exists");
            return responseMap;
        }
    
        Filier filier = filierRepo.save(Filier.toFilier(request));
        responseMap.put("success", "Filier created!");
        return responseMap;
    }

    public Map<String, Object> createDepartement(String token, StoreDepartementRequest request) {
        UserDto user = userTokenService.getUserOfToken(token);
        Map<String, Object> responseMap = new HashMap<>();
        
        if (user == null || !user.isAdmin()) {
            responseMap.put("error", "You have no permission to do this action!");
            return responseMap;
        }
    
        Optional<Departement> oldDepartement = departementRepo.findByNomDepartement(request.getNom_departement());
        if (oldDepartement.isPresent()) {
            responseMap.put("error", "The Departement already exists");
            return responseMap;
        }
    
        Departement departement = departementRepo.save(Departement.toDepartement(request));
        responseMap.put("success", "Departement created!");
        return responseMap;
    }


    public Map<String, Object> deleteByName(String token, String name, long id) {
        UserDto user = userTokenService.getUserOfToken(token);
        Map<String, Object> responseMap = new HashMap<>();
        
        if (user == null || !user.isAdmin()) {
            responseMap.put("error", "You have no permission to do this action!");
            return responseMap;
        }

        switch (name.toLowerCase()) {
            case "student":
                Optional<User> oldUser = userRepo.findFirstById(id);
                if (oldUser.isPresent()) {
                    userRepo.delete(oldUser.get());
                    responseMap.put("success", "Student Deleted!");
                    return responseMap;
                }
                break;
            
            case "filier":
                Optional<Filier> oldFilier = filierRepo.findFirstById(id);
                if (oldFilier.isPresent()) {
                    filierRepo.delete(oldFilier.get());
                    responseMap.put("success", "Filier Deleted!");
                    return responseMap;
                }
                break;
            
            case "departement":
                Optional<Departement> oldDepartement = departementRepo.findFirstById(id);
                if (oldDepartement.isPresent()) {
                    departementRepo.delete(oldDepartement.get());
                    responseMap.put("success", "Departement Deleted!");
                    return responseMap;
                }
                break;
            
            default:
                responseMap.put("error", "Invalid entity name!");
        }
    
        responseMap.put("error", "Something went wrong!");
        return responseMap;

    }

    public Map<String, Object> updateStudent(String token, long id, @Valid StoreUserRequest storeUserRequest) {
        UserDto user = userTokenService.getUserOfToken(token);
        Map<String, Object> responseMap = new HashMap<>();
        
        if (user == null || !user.isAdmin()) {
            responseMap.put("error", "You have no permission to do this action!");
            return responseMap;
        }
    
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent()) {
            User updateUser = optionalUser.get();
            updateUser.setCne(storeUserRequest.getCne());
            updateUser.setPrenom(storeUserRequest.getPrenom());
            updateUser.setTele(storeUserRequest.getTele());
    
            if (storeUserRequest.getFiliere_id() != null) {
                Long filiereId = Long.parseLong(storeUserRequest.getFiliere_id());
                Filier filiere = filierRepo.findById(filiereId).orElse(null);
                updateUser.setFiliere(filiere);
            }
    
            userRepo.save(updateUser);
            responseMap.put("success", "Student Updated!");
        } else {
            responseMap.put("error", "User not found!");
        }
        
        return responseMap;
    }

    public Map<String, Object> updateDepartement(String token, long id,
            @Valid StoreDepartementRequest storeDepartementRequest) {
        UserDto user = userTokenService.getUserOfToken(token);
        Map<String, Object> responseMap = new HashMap<>();
        
        if (user == null || !user.isAdmin()) {
            responseMap.put("error", "You have no permission to do this action!");
            return responseMap;
        }
        return responseMap ;
    }

    public Map<String, Object> updateFilier(String token, long id, @Valid StoreFilierRequest storeFilierRequest) {
        UserDto user = userTokenService.getUserOfToken(token);
        Map<String, Object> responseMap = new HashMap<>();
        
        if (user == null || !user.isAdmin()) {
            responseMap.put("error", "You have no permission to do this action!");
            return responseMap;
        }
        return responseMap ;
    }



    
}
