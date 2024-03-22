package com.project.project.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreDepartementRequest {
    @NotBlank(message = "nom departement is required")
    private String nom_departement ;
    
}
