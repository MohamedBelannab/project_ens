package com.project.project.services;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.project.dto.UserDto;
import com.project.project.models.PersonalAccesToken;
import com.project.project.models.User;
import com.project.project.repositories.FilierRepo;
import com.project.project.repositories.PersonalAccessTokenRepository;
import com.project.project.repositories.UserRepo;
import com.project.project.requests.StoreUserLoginRequest;
import com.project.project.requests.StoreUserRequest;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
   
    private final UserRepo userRepository ;
    private final FilierRepo filierRepo;
    private final PersonalAccessTokenRepository personalAccesTokenRepository;
    private  UserTokenService userTokenService;
    @Autowired
    public AuthService(FilierRepo filierRepo ,UserRepo userRepository, UserTokenService userTokenService,PersonalAccessTokenRepository personalAccesTokenRepository) {
        this.userRepository = userRepository;
        this.filierRepo = filierRepo ;
        this.userTokenService = userTokenService;
        this.personalAccesTokenRepository = personalAccesTokenRepository;
        
    }

    public String storeToken(User user){
        List<PersonalAccesToken> tokensToDelete = personalAccesTokenRepository.findByUser(user);
        personalAccesTokenRepository.deleteAll(tokensToDelete);
        PersonalAccesToken  personalAccesToken = new PersonalAccesToken();
        String token = userTokenService.generateToken();
        personalAccesToken.setToken(token);
        personalAccesToken.setUser(user);
        personalAccesTokenRepository.save(personalAccesToken);
        return token;
    }

    
    public Map<String, Object> register(StoreUserRequest request){
        Optional<User> oldUser = userRepository.findByCne(request.getCne().toUpperCase());
        Map<String, Object> responseMap = new HashMap<>();
        if (oldUser.isPresent()) {
            responseMap.put("error", "The CNE already exists");
            return responseMap;
        }
        User user   = userRepository.save(User.toUser(request , filierRepo));
        
        responseMap.put("user",UserDto.toUserDto(user));
        responseMap.put("token",storeToken(user));

        return responseMap;
        
       
    }

    public Map<String, Object> loginUser(@Valid StoreUserLoginRequest request) {
        Map<String, Object> responseMap = new HashMap<>();

        userRepository.findByCneAndPassword(request.getCne().toUpperCase(), User.hashPassword(request.getPassword()))
                .ifPresentOrElse(
                        user -> {
                            responseMap.put("user", UserDto.toUserDto(user));
                            responseMap.put("token", storeToken(user));
                        },
                        () -> responseMap.put("error", "Data mismatch")
                );

        return responseMap;
    }

    public  boolean logoutUser(String token) {
        UserDto user = userTokenService.getUserOfToken(token);
        if (user != null){
               Optional<PersonalAccesToken> pat = personalAccesTokenRepository.findFirstByToken(token);
               if(pat.isPresent()){
                   personalAccesTokenRepository.delete(pat.get());
                   return true;
               }
           }
     
           return false ;
    }

    public Map<String, Object> checkCneUser(String cne) {
        Map<String, Object> responseMap = new HashMap<>();

        userRepository.findByCne(cne.toUpperCase())
                .ifPresentOrElse(
                        user -> {
                            responseMap.put("success", false);
                        },
                        () -> responseMap.put("success", true)
                );

        return responseMap;
    }

    public Map<String, Object> checkUser(String token) {
        Map<String, Object> responseMap = new HashMap<>();
        
        Optional<User> userOptional = userRepository.findUserByToken(token);
        
        userOptional.ifPresentOrElse(
            user -> {
                responseMap.put("user", UserDto.toUserDto(user));
            },
            () -> responseMap.put("error", "Data mismatch")
        );

        return responseMap;
    }
    
}
