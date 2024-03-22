package com.project.project.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.project.models.Departement;
@Repository
public interface DepartementRepo extends JpaRepository<Departement , Long>  {

    Optional<Departement> findFirstById(long id);

    Optional<Departement> findByNomDepartement(String nomDepartement); 
    
}
