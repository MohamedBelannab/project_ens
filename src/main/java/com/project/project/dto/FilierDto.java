package com.project.project.dto;
import java.sql.Timestamp;

import com.project.project.models.Filier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilierDto {
    private Long id  ;
    private String nomFilier ;
    private String departement ;
     private Timestamp createdAt;


    public static FilierDto toFilierDto(Filier filier)
    {
        FilierDto filierDto = new FilierDto() ;
        filierDto.setId(filier.getId());
        filierDto.setDepartement(filier.getNameDepartement());
        filierDto.setNomFilier(filier.getNomFilier());
        filierDto.setCreatedAt(filier.getCreatedAt());
        return filierDto ;


    }
    
}
