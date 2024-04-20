package com.project.project.models;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.project.requests.StoreFilierRequest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Filier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id  ;
    private String nomFilier ;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonManagedReference
    private Departement department;


    public static Filier toFilier(StoreFilierRequest request)
    {

        Filier filier = new Filier() ;
        filier.setNomFilier(request.getNom_filier().toLowerCase());
        Departement departement = new Departement() ;
        departement.setId(Long.parseLong(request.getDepartment_id()));
        filier.setDepartment(departement);
        return filier ;


    }


    public String getNameDepartement() {
       return department != null ? department.getNomDepartement() : null ;
    }
}
