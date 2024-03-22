package com.project.project.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StoreFilierRequest {
    @NotBlank(message = "nom filier is required")
    private String  nom_filier ;
    @NotBlank(message = "department is required")
    private String department_id ;
    
}
