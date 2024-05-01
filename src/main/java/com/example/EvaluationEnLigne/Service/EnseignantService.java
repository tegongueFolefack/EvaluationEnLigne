package com.example.EvaluationEnLigne.Service;

import java.util.Date;
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
import com.example.EvaluationEnLigne.Models.Enseignant;
import com.example.EvaluationEnLigne.Models.Enseignant;
import com.example.EvaluationEnLigne.Repository.EnseignantRepository;
import com.example.EvaluationEnLigne.Repository.EnseignantRepository;
import com.example.EvaluationEnLigne.Request.AuthenticationRequest;
import com.example.EvaluationEnLigne.Request.RefreshToken;
import com.example.EvaluationEnLigne.Response.AuthenticationResponse;



@Service
public class EnseignantService {
	
	@Autowired
    private EnseignantRepository EnseignantRepository;
	
	 @Autowired
	 private  PasswordEncoder passwordEncoder;
	 
	 @Autowired
	  private  JwtService jwtService;
	 
	
	 
	 @Autowired
	  private  AuthenticationManager authenticationManager;
	 
	 @Autowired
	  private  RefreshTokenService refreshTokenService;
 

	public Optional<Enseignant> getEnseignantById(Integer id) {
		return EnseignantRepository.findById(id);
	}

	public Iterable<Enseignant> getAllEnseignant() {
		return EnseignantRepository.findAll();
	}

	public boolean deleteEnseignant(Integer id) {
		Optional<Enseignant> EnseignantOpt = getEnseignantById(id);
		if (EnseignantOpt.isPresent()) {
			EnseignantRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
	
 
 
 
 
 public AuthenticationResponse updateEnseignant(Integer id, Enseignant Enseignant) {
     Optional<Enseignant> EnseignantOpt = EnseignantRepository.findById(id);

     if (EnseignantOpt.isPresent()) {
         Enseignant EnseignantFromDb = EnseignantOpt.get();

         // Mettre à jour les propriétés de l'objet Enseignant avec les données de l'objet Enseignant passé en paramètre
         EnseignantFromDb.setNom(Enseignant.getNom());
         EnseignantFromDb.setPrenom(Enseignant.getPrenom());
         EnseignantFromDb.setEmail(Enseignant.getEmail());
         EnseignantFromDb.setLogin(Enseignant.getLogin());
         EnseignantFromDb.setDatePriseEns(Enseignant.getDatePriseEns());

         // Vérifier si le mot de passe a été modifié
         if (Enseignant.getPassword() != null && !Enseignant.getPassword().isEmpty()) {
             // Hacher le nouveau mot de passe
             String hashedPassword = passwordEncoder.encode(Enseignant.getPassword());
             EnseignantFromDb.setPassword(hashedPassword);
         }

         // Sauvegarder les modifications dans la base de données
         Enseignant updatedEnseignant = EnseignantRepository.save(EnseignantFromDb);

         // Générer un nouveau token JWT avec les informations mises à jour
         String jwt = jwtService.generateToken(updatedEnseignant);

         // Créer un nouveau refresh token pour l'utilisateur
         RefreshToken refreshToken = refreshTokenService.createRefreshToken(updatedEnseignant.getId());

         // Récupérer les rôles de l'utilisateur mis à jour et les convertir en une liste de String
         List<String> roles = updatedEnseignant.getRole().getAuthorities().stream()
                 .map(SimpleGrantedAuthority::getAuthority)
                 .collect(Collectors.toList());

         // Construire et renvoyer la réponse
         return AuthenticationResponse.builder()
                 .accessToken(jwt)
                 .email(updatedEnseignant.getEmail())
                 .refreshToken(refreshToken.getToken())
                 .roles(roles)
                 .tokenType(TokenType.BEARER.name())
                 .build();
     } else {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
     }
 }


	
	
public AuthenticationResponse register(Enseignant request) {
	  Enseignant Enseignant = new Enseignant(
	            request.getNom(),
	            request.getPrenom(),
	            request.getEmail(),
	            passwordEncoder.encode(request.getPassword()),
	            request.getLogin(),
	            request.getTelephone(),
	            Role.ENSEIGNANT,
	            request.getDatePriseEns()
	        );
     Enseignant = EnseignantRepository.save(Enseignant);
     var jwt = jwtService.generateToken(Enseignant);
     var refreshToken = refreshTokenService.createRefreshToken(Enseignant.getId());

     var roles = Enseignant.getRole().getAuthorities()
             .stream()
             .map(SimpleGrantedAuthority::getAuthority)
             .toList();

     return AuthenticationResponse.builder()
             .accessToken(jwt)
             .email(Enseignant.getEmail())
             .id(Enseignant.getId())
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
    Enseignant Enseignant = EnseignantRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
    
    // Récupérez les rôles de l'Enseignantistrateur
    var roles = Enseignant.getRole().getAuthorities()
            .stream()
            .map(SimpleGrantedAuthority::getAuthority)
            .toList();

    // Générez le jeton JWT et le jeton de rafraîchissement
    var jwt = jwtService.generateToken(Enseignant);
    var refreshToken = refreshTokenService.createRefreshToken(Enseignant.getId());

    return AuthenticationResponse.builder()
            .accessToken(jwt)
            .roles(roles)
            .email(Enseignant.getEmail())
            .id(Enseignant.getId())
            .refreshToken(refreshToken.getToken())
            .tokenType(TokenType.BEARER.name())
            .build();
}



}
