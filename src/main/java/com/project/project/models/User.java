package com.project.project.models;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id  ;
    private String cne ;
    private String Prenom ;
    private String tele ;
    private String password;
    private String role;
  

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


    public static User toUser(StoreUserRequest request) {
        User user = new User();
        user.setPrenom(request.getPrenom());;
        user.setCne(request.getCne());
        user.setPassword(User.hashPassword(request.getPassword()) );
        user.setTele(request.getTele());
        Filier filiere = new Filier();
        filiere.setId(Long.parseLong(request.getFiliere_id()));
        user.setFiliere(filiere);
        return user;
    }


    public String getNameFilier() {
        return filiere != null ? filiere.getNomFilier() : null;
    }
    
}
