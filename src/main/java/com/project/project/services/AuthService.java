package com.project.project.services;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.project.project.dto.UserDto;
import com.project.project.models.PersonalAccesToken;
import com.project.project.models.User;
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
    private final PersonalAccessTokenRepository personalAccesTokenRepository;
    private  UserTokenService userTokenService;
    @Autowired
    public AuthService(UserRepo userRepository, UserTokenService userTokenService,PersonalAccessTokenRepository personalAccesTokenRepository) {
        this.userRepository = userRepository;
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
        Optional<User> oldUser = userRepository.findByCne(request.getCne());
        Map<String, Object> responseMap = new HashMap<>();
        if (oldUser.isPresent()) {
            responseMap.put("error", "The CNE already exists");
            return responseMap;
        }
        User user   = userRepository.save(User.toUser(request));
        responseMap.put("user",UserDto.toUserDto(user));
        responseMap.put("token",storeToken(user));

        return responseMap;
        
       
    }

    public Map<String, Object> loginUser(@Valid StoreUserLoginRequest request) {
        Map<String, Object> responseMap = new HashMap<>();

        userRepository.findByCneAndPassword(request.getCne(), User.hashPassword(request.getPassword()))
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
    
}
