package com.example.EvaluationEnLigne.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EvaluationEnLigne.AuthService.AuthenticationService;
import com.example.EvaluationEnLigne.AuthService.RefreshTokenService;
import com.example.EvaluationEnLigne.Request.AuthenticationRequest;
import com.example.EvaluationEnLigne.Request.RefreshTokenRequest;
import com.example.EvaluationEnLigne.Request.RegisterRequest;
import com.example.EvaluationEnLigne.Request.RegisterRequest;
import com.example.EvaluationEnLigne.Response.AuthenticationResponse;
import com.example.EvaluationEnLigne.Response.RefreshTokenResponse;
import com.example.EvaluationEnLigne.Response.RefreshTokenResponse;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Authentication", description = "The Authentication API. Contains operations like login, logout, refresh-token etc.")
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
//@SecurityRequirements() 
/*
This API won't have any security requirements. Therefore, we need to override the default security requirement configuration
with @SecurityRequirements()
*/
@RequiredArgsConstructor
public class AuthenticationController {
	
	 private final AuthenticationService authenticationService;
	  private final RefreshTokenService refreshTokenService ;
	  
	  private final AuthenticationManager authenticationManager;

	   
	 
	 
	  @PostMapping("/register")
	  public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
	    return ResponseEntity.ok(authenticationService.register(request));
	 }
	 
	  @PostMapping("/authenticate")
	  public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
	    return ResponseEntity.ok(authenticationService.authenticate(request));
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
	  
	  @GetMapping("/hello")
	  public String greet() {
		  return "hello";
	  }

}
