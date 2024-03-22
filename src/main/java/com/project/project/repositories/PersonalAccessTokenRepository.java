package com.project.project.repositories;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.project.models.PersonalAccesToken;
import com.project.project.models.User;

@Repository
public interface PersonalAccessTokenRepository extends JpaRepository<PersonalAccesToken , Long>  {

    List<PersonalAccesToken> findByUser(User user);

    Optional<PersonalAccesToken> findFirstByToken(String token);
    
}
