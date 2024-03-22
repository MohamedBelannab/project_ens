package com.project.project.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.project.dto.FilierDto;
import com.project.project.models.Filier;
import com.project.project.repositories.FilierRepo;

@Service
public class FilierService {
    FilierRepo filierRepo ;
    @Autowired
    public FilierService(FilierRepo filierRepo)
    {
        this.filierRepo = filierRepo ;
    }

    public List<FilierDto> getAllFilier()
    {
        List<FilierDto> filiersDto = new ArrayList<FilierDto>() ;
        List<Filier> filiers = filierRepo.findAll() ;
        for (Filier filier : filiers ) 
        {
            filiersDto.add(FilierDto.toFilierDto(filier)) ;
            
        }
        return  filiersDto;

    }

    


    
}
