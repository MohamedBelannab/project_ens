package com.project.project.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.project.models.User;
import com.project.project.dto.UserDto;
import com.project.project.models.Filier;
import com.project.project.repositories.UserRepo;
import com.project.project.requests.StoreUserRequest;

@Service
public class UserService {
    
    private UserRepo etudiantRepo ;
    @Autowired
    public UserService(UserRepo etudiantRepo )
    {
        this.etudiantRepo = etudiantRepo ;

    }
    
    public List<UserDto> getAllUser()
    {
        List<UserDto> studentsDto = new ArrayList<UserDto>() ;
        List<User> students = etudiantRepo.findAllByRole("student") ;
        for (User user : students) 
        {
            studentsDto.add(UserDto.toUserDto(user)) ;
            
        }
        return  studentsDto;

    }
    
}
