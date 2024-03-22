package com.project.project.dto;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import com.project.project.models.Departement;
import com.project.project.models.Filier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartementDto {
    private Long id ;
    private String nomDepartement ;
    private Timestamp createdAt;
    private List<FilierDto> filiers  ;


    public static DepartementDto toDepartementDto(Departement departement)
    {
        List<FilierDto> f = new ArrayList<FilierDto>() ;
        DepartementDto departementDto = new DepartementDto() ;
        departementDto.setCreatedAt(departement.getCreatedAt());
        departementDto.setId(departement.getId());
        departementDto.setNomDepartement(departement.getNomDepartement());
        for (Filier filier : departement.getFilieres()) {

            f.add(FilierDto.toFilierDto(filier));
            
        }
        departementDto.setFiliers(f);

        return departementDto ;

    }
    
}
