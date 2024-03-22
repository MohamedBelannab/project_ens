package com.project.project.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreUserLoginRequest {

    @NotBlank(message = "cne is required")
    private String cne ;
    @Size(min = 8, message = "Password must be at least 8 characters ")
    private String password ;
    
}
