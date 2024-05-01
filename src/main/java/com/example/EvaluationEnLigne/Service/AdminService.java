package com.example.EvaluationEnLigne.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.EvaluationEnLigne.AuthService.JwtService;
import com.example.EvaluationEnLigne.AuthService.RefreshTokenService;
import com.example.EvaluationEnLigne.Enum.Role;
import com.example.EvaluationEnLigne.Enum.TokenType;
import com.example.EvaluationEnLigne.Models.Admin;
import com.example.EvaluationEnLigne.Repository.AdminRepository;
import com.example.EvaluationEnLigne.Request.AuthenticationRequest;
import com.example.EvaluationEnLigne.Request.RefreshToken;
import com.example.EvaluationEnLigne.Response.AuthenticationResponse;

@Service
public class AdminService {
	
	@Autowired
    private AdminRepository AdminRepository;
	
	 @Autowired
	 private  PasswordEncoder passwordEncoder;
	 @Autowired
	  private  JwtService jwtService;
	 
	
	 
	 @Autowired
	  private  AuthenticationManager authenticationManager;
	 
	 @Autowired
	  private  RefreshTokenService refreshTokenService;
 

	public Optional<Admin> getAdminById(Integer id) {
		return AdminRepository.findById(id);
	}

	public Iterable<Admin> getAllAdmin() {
		return AdminRepository.findAll();
	}

	public boolean deleteAdmin(Integer id) {
		Optional<Admin> AdminOpt = getAdminById(id);
		if (AdminOpt.isPresent()) {
			AdminRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
	
 
 
 
 
 public AuthenticationResponse updateAdmin(Integer id, Admin Admin) {
     Optional<Admin> AdminOpt = AdminRepository.findById(id);

     if (AdminOpt.isPresent()) {
         Admin AdminFromDb = AdminOpt.get();

         // Mettre à jour les propriétés de l'objet Admin avec les données de l'objet Admin passé en paramètre
         AdminFromDb.setNom(Admin.getNom());
         AdminFromDb.setPrenom(Admin.getPrenom());
         AdminFromDb.setEmail(Admin.getEmail());
         AdminFromDb.setLogin(Admin.getLogin());

         // Vérifier si le mot de passe a été modifié
         if (Admin.getPassword() != null && !Admin.getPassword().isEmpty()) {
             // Hacher le nouveau mot de passe
             String hashedPassword = passwordEncoder.encode(Admin.getPassword());
             AdminFromDb.setPassword(hashedPassword);
         }

         // Sauvegarder les modifications dans la base de données
         Admin updatedAdmin = AdminRepository.save(AdminFromDb);

         // Générer un nouveau token JWT avec les informations mises à jour
         String jwt = jwtService.generateToken(updatedAdmin);

         // Créer un nouveau refresh token pour l'utilisateur
         RefreshToken refreshToken = refreshTokenService.createRefreshToken(updatedAdmin.getId());

         // Récupérer les rôles de l'utilisateur mis à jour et les convertir en une liste de String
         List<String> roles = updatedAdmin.getRole().getAuthorities().stream()
                 .map(SimpleGrantedAuthority::getAuthority)
                 .collect(Collectors.toList());

         // Construire et renvoyer la réponse
         return AuthenticationResponse.builder()
                 .accessToken(jwt)
                 .email(updatedAdmin.getEmail())
                 .refreshToken(refreshToken.getToken())
                 .roles(roles)
                 .tokenType(TokenType.BEARER.name())
                 .build();
     } else {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
     }
 }


	
	
public AuthenticationResponse register(Admin request) {
	  Admin Admin = new Admin(
	            request.getNom(),
	            request.getPrenom(),
	            request.getEmail(),
	            passwordEncoder.encode(request.getPassword()),
	            request.getLogin(),
	            request.getTelephone(),
	            Role.ADMIN
	        );
     Admin = AdminRepository.save(Admin);
     var jwt = jwtService.generateToken(Admin);
     var refreshToken = refreshTokenService.createRefreshToken(Admin.getId());

     var roles = Admin.getRole().getAuthorities()
             .stream()
             .map(SimpleGrantedAuthority::getAuthority)
             .toList();

     return AuthenticationResponse.builder()
             .accessToken(jwt)
             .email(Admin.getEmail())
             .id(Admin.getId())
             .refreshToken(refreshToken.getToken())
             .roles(roles)
             .tokenType( TokenType.BEARER.name())
             .build();
 }




public AuthenticationResponse authenticate(AuthenticationRequest request) {
	 System.out.println("11111111");
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    System.out.println("hhhhhh");
    // Utilisez .orElseThrow() pour gérer l'Optional
    Admin Admin = AdminRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
    
    // Récupérez les rôles de l'Administrateur
    var roles = Admin.getRole().getAuthorities()
            .stream()
            .map(SimpleGrantedAuthority::getAuthority)
            .toList();

    // Générez le jeton JWT et le jeton de rafraîchissement
    var jwt = jwtService.generateToken(Admin);
    var refreshToken = refreshTokenService.createRefreshToken(Admin.getId());

    return AuthenticationResponse.builder()
            .accessToken(jwt)
            .roles(roles)
            .email(Admin.getEmail())
            .id(Admin.getId())
            .refreshToken(refreshToken.getToken())
            .tokenType(TokenType.BEARER.name())
            .build();
}




}
