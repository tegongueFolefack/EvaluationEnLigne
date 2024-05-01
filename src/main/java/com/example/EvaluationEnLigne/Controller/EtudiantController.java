package com.example.EvaluationEnLigne.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.EvaluationEnLigne.AuthService.RefreshTokenService;
import com.example.EvaluationEnLigne.DTO.EtudiantDTO;
import com.example.EvaluationEnLigne.Models.Etudiant;
import com.example.EvaluationEnLigne.Request.AuthenticationRequest;
import com.example.EvaluationEnLigne.Request.RefreshTokenRequest;
import com.example.EvaluationEnLigne.Response.AuthenticationResponse;
import com.example.EvaluationEnLigne.Response.RefreshTokenResponse;
import com.example.EvaluationEnLigne.Service.EtudiantService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Authentication", description = "The Authentication API. Contains operations like login, logout, refresh-token etc.")
//@SecurityRequirements() 
@RestController
@RequestMapping("/Etudiant")
@CrossOrigin(origins = "*")
public class EtudiantController {
	
		@Autowired
		  private  RefreshTokenService refreshTokenService ;
		@Autowired
		  private  AuthenticationManager authenticationManager;

	
		
		@Autowired
		private EtudiantService EtudiantService;
		
		@GetMapping("/{id}")
	    public ResponseEntity<EtudiantDTO> getEtudiantById(@PathVariable Integer id) {
	        try {
	            Optional<Etudiant> Etudiant = EtudiantService.getEtudiantById(id);
	            if (Etudiant.isPresent()) {
	            	EtudiantDTO EtudiantDTO = Etudiant.get().toEtudiantDTO();
	                return ResponseEntity.ok(EtudiantDTO);
	            } else {
	                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Etudiant not found with ID: " + id);
	            }
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }

	    @DeleteMapping("/delete/{id}")
	    public ResponseEntity<Void> deleteEtudiant(@PathVariable Integer id) {
	        try {
	        	EtudiantService.deleteEtudiant(id);
	            return ResponseEntity.noContent().build();
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }

	    @GetMapping()
	    public ResponseEntity<List<EtudiantDTO>> findAll() {
	        try {
	            Iterable<Etudiant> Etudiants = EtudiantService.getAllEtudiant();
	            List<EtudiantDTO> EtudiantDTOs = new ArrayList<>();
	            for (Etudiant Etudiant: Etudiants) {
	            	EtudiantDTOs.add(Etudiant.toEtudiantDTO());
	            }
	            return ResponseEntity.ok(EtudiantDTOs);
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }

		
	    @PostMapping("/register")
	    public ResponseEntity<?> register(@Valid @RequestBody Etudiant request) {
	    	
	        try {
	            AuthenticationResponse response = EtudiantService.register(request);
	            return ResponseEntity.ok(response);
	        } catch (ResponseStatusException e) {
	            throw e;
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while registering the client", e);
	        }
	    }

		 
		  @PostMapping("/authenticate")
		  public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest  request) {
			  System.out.println("ggggggg");
			  return ResponseEntity.ok(EtudiantService.authenticate(request));
		 }
		  @PostMapping("/refresh-token")
		  public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
		    return ResponseEntity.ok(refreshTokenService.generateNewToken(request));
		 }
		  @GetMapping("/info")
		    public Authentication getAuthentication(@RequestBody AuthenticationRequest request){
		        return authenticationManager.authenticate(
		                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
		   
		  }
		  
		  @PutMapping("/update/{id}")
		    public ResponseEntity<AuthenticationResponse> updateEtudiant(@PathVariable 	Integer id, @RequestBody EtudiantDTO ClientDto) {
		        try {
		        	Etudiant client = ClientDto.toEtudiant();
		            AuthenticationResponse response = EtudiantService.updateEtudiant(id, client);
		            return ResponseEntity.ok(response);
		        } catch (ResponseStatusException e) {
		            throw e;
		        } catch (Exception e) {
		            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
		        }
		    }
		
		    	
		    @ExceptionHandler(ResponseStatusException.class)
		    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
		        return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
		    }

		    @ExceptionHandler(Exception.class)
		    public ResponseEntity<String> handleException(Exception ex) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		    }


	


}
