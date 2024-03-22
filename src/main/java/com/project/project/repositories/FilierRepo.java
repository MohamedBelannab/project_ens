package com.project.project.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.project.models.Filier;
@Repository
public interface FilierRepo extends  JpaRepository<Filier , Long> {

    Optional<Filier> findFirstById(long id);


    Optional<Filier> findByNomFilier(String nomFilier);
    
}