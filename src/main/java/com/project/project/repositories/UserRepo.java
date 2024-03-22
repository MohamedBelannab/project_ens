package com.project.project.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.project.project.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User , Long>{

    @Query(value = "SELECT u.* " +
    "FROM user u " +
    "INNER JOIN personal_acces_token pat ON u.id = pat.user_id " +
    "WHERE pat.token = :token LIMIT 1", nativeQuery = true)
    Optional<User> findUserByToken(@Param("token") String token);

    Optional<User> findByCne(String cne);

    Optional<User> findByCneAndPassword(String cne, String hashPassword);

    Optional<User> findFirstById(long id);

    List<User> findAllByRole(String role);

    

}