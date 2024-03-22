package com.project.project.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.project.dto.DepartementDto;
import com.project.project.models.Departement;
import com.project.project.repositories.DepartementRepo;

@Service
public class DepartementService {

    private DepartementRepo departementRepo ;

    @Autowired 
    public DepartementService(DepartementRepo departementRepo)
    {
        this.departementRepo = departementRepo ;
    }

    public List<DepartementDto> getAllDepartement()
    {
        List<DepartementDto> departementdto = new ArrayList<DepartementDto>() ;
        List<Departement> departements = departementRepo.findAll() ;
        for (Departement departement : departements ) 
        {
            departementdto.add(DepartementDto.toDepartementDto(departement)) ;
            
        }
        return  departementdto;

    }



    
}
