package com.project.project.requests;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StoreUserRequest  {
    @NotBlank(message = "cne is required")
    private String cne ;
    @NotBlank(message = "prenom is required")
    private String prenom ;
    @NotBlank(message = "tele is required")
    private String tele ;
    @Size(min = 8, message = "Password must be at least 8 characters ")
    private String password ;
    @NotBlank(message = "Password Confirmation is required")
    private String password_confirme ;
    @NotBlank(message = "filier is required")
    private String filiere_id ;
    private boolean status ;
   

    public  boolean passwordCheck(){
        return password.equals(password_confirme);
    }

    
}
