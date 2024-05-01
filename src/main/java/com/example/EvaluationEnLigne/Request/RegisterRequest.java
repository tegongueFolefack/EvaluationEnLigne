package com.example.EvaluationEnLigne.Request;



import com.example.EvaluationEnLigne.Enum.Role;
import com.example.EvaluationEnLigne.Validation.StrongPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
	

		 
		 // @NotBlank(message = "firstname is required")
		  private String nom;
		  //@NotBlank(message = "lastname is required")
		  private String prenom;
//		  @NotBlank(message = "email is required")
//		  @Email(message = "email format is not valid")
		  private String email;
//		  @NotBlank(message = "password is required")
//		  @StrongPassword
		  private String password;
		  //@NotNull
		  private Role role;
		  private String login;
		  private String telephone;
			
			
			
			

}
