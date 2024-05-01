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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.EvaluationEnLigne.AuthService.RefreshTokenService;
import com.example.EvaluationEnLigne.DTO.EnseignantDTO;
import com.example.EvaluationEnLigne.Models.Enseignant;
import com.example.EvaluationEnLigne.Request.AuthenticationRequest;
import com.example.EvaluationEnLigne.Request.RefreshTokenRequest;
import com.example.EvaluationEnLigne.Response.AuthenticationResponse;
import com.example.EvaluationEnLigne.Response.RefreshTokenResponse;
import com.example.EvaluationEnLigne.Service.EnseignantService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/enseignant")
@CrossOrigin(origins = "*")
public class EnseignantController {
	
	@Autowired
	  private  RefreshTokenService refreshTokenService ;
	@Autowired
	  private  AuthenticationManager authenticationManager;


	
	@Autowired
	private EnseignantService EnseignantService;
	
	@GetMapping("/{id}")
  public ResponseEntity<EnseignantDTO> getEnseignantById(@PathVariable Integer id) {
      try {
          Optional<Enseignant> Enseignant = EnseignantService.getEnseignantById(id);
          if (Enseignant.isPresent()) {
          	EnseignantDTO EnseignantDTO = Enseignant.get().toEnseignantDTO();
              return ResponseEntity.ok(EnseignantDTO);
          } else {
              throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Enseignant not found with ID: " + id);
          }
      } catch (Exception e) {
          throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
      }
  }

  @DeleteMapping("delete/{id}")
  public ResponseEntity<Void> deleteEnseignant(@PathVariable Integer id) {
      try {
      	EnseignantService.deleteEnseignant(id);
          return ResponseEntity.noContent().build();
      } catch (Exception e) {
          throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
      }
  }

  @GetMapping("/")
  public ResponseEntity<List<EnseignantDTO>> findAll() {
      try {
          Iterable<Enseignant> Enseignants = EnseignantService.getAllEnseignant();
          List<EnseignantDTO> EnseignantDTOs = new ArrayList<>();
          for (Enseignant Enseignant: Enseignants) {
          	EnseignantDTOs.add(Enseignant.toEnseignantDTO());
          }
          return ResponseEntity.ok(EnseignantDTOs);
      } catch (Exception e) {
          throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
      }
  }

	
  @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody Enseignant request) {
  	
      try {
          AuthenticationResponse response = EnseignantService.register(request);
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
		  return ResponseEntity.ok(EnseignantService.authenticate(request));
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
	
	    	
	    @ExceptionHandler(ResponseStatusException.class)
	    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
	        return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<String> handleException(Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
	    }




}
