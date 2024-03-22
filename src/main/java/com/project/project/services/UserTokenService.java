package com.project.project.services;

import java.util.UUID;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.project.dto.UserDto;
import com.project.project.models.User;
import com.project.project.repositories.UserRepo;

@Service
public class UserTokenService {

    private final UserRepo userRepository;
    @Autowired
    public UserTokenService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    public String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public String extractToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }

    public UserDto getUserOfToken(String token) {
        Optional<User> userOptional = userRepository.findUserByToken(token);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return UserDto.toUserDto(user);
        } 
            return null; 
        
    }

    public boolean isValidToken(String token)
    {
        Optional<User> userOptional = userRepository.findUserByToken(token);
        return userOptional.isPresent() ? true : false ;

    }
}
