package com.project.project.models;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Optional;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.project.project.repositories.FilierRepo;
import com.project.project.requests.StoreUserRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.security.MessageDigest;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Entity
@Data
public class User {
    public User() { 
        this.role = "student";
        this.status = false ;

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id  ;
    private String cne ;
    private String prenom ;
    private String tele ;
    private String password;
    private String role;
    private boolean status;
  

    @ManyToOne(fetch = FetchType.LAZY)
    private Filier filiere;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    


    public static String hashPassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(password.getBytes());
            byte[] hashedBytes = messageDigest.digest();

            StringBuilder hexStringBuilder = new StringBuilder();
            for (byte hashedByte : hashedBytes) {
                hexStringBuilder.append(String.format("%02x", hashedByte));
            }

            return hexStringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public boolean isAdmin()
    {
        return role.equals("admin");
    }

    public boolean isStatus(){
        return status  ;
    }


    public static User toUser(StoreUserRequest request , FilierRepo filierRepo) {
        User user = new User();
        user.setPrenom(request.getPrenom());;
        user.setCne(request.getCne().toUpperCase());
        user.setPassword(User.hashPassword(request.getPassword()) );
        user.setTele(request.getTele());
        System.err.println(request.isStatus());
        user.setStatus(request.isStatus());
        // Find and set the Filiere
        Optional<Filier> filierOptional = filierRepo.findFirstById(Long.parseLong(request.getFiliere_id()));
        filierOptional.ifPresent(user::setFiliere);
        return user;
    }


    public String getNameFilier() {
        return this.getFiliere().getNomFilier() ;
    }
    
}
