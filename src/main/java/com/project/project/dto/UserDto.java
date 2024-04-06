package com.project.project.dto;

import java.sql.Timestamp;

import com.project.project.models.Filier;
import com.project.project.models.User;
import com.project.project.repositories.FilierRepo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id  ;
    private String cne ;
    private String Prenom ;
    private String tele ;
    private Timestamp createdAt;
    private String role;
    private FilierDto filiere;
    private boolean isAdmin;
    private boolean status;

    static public UserDto toUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setPrenom(user.getPrenom());
        userDto.setCne(user.getCne());
        userDto.setTele(user.getTele());
        userDto.setRole(user.getRole());
        userDto.setFiliere(FilierDto.toFilierDto(user.getFiliere()));
        userDto.setAdmin(user.isAdmin());
        userDto.setStatus(user.isStatus());
        userDto.setCreatedAt(user.getCreatedAt());
        return userDto;
    }
    
}
