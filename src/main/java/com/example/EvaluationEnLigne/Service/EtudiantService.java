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
import com.example.EvaluationEnLigne.Models.Etudiant;
import com.example.EvaluationEnLigne.Models.User;
import com.example.EvaluationEnLigne.Repository.EtudiantRepository;
import com.example.EvaluationEnLigne.Repository.RefreshTokenRepository;
import com.example.EvaluationEnLigne.Request.AuthenticationRequest;
import com.example.EvaluationEnLigne.Request.RefreshToken;
import com.example.EvaluationEnLigne.Request.RegisterRequest;
import com.example.EvaluationEnLigne.Response.AuthenticationResponse;
@Service
public class EtudiantService {
	
	@Autowired
    private EtudiantRepository EtudiantRepository;
	
	 @Autowired
	 private  PasswordEncoder passwordEncoder;
	 
	 @Autowired
	 private  RefreshTokenRepository refreshTokenRepository;
	 
	 @Autowired
	  private  JwtService jwtService;
	 
	
	 
	 @Autowired
	  private  AuthenticationManager authenticationManager;
	 
	 @Autowired
	  private  RefreshTokenService refreshTokenService;
 

	public Optional<Etudiant> getEtudiantById(Integer id) {
		return EtudiantRepository.findById(id);
	}

	public Iterable<Etudiant> getAllEtudiant() {
		return EtudiantRepository.findAll();
	}

	public boolean deleteEtudiant(Integer id) {
        Optional<Etudiant> EtudiantOpt = EtudiantRepository.findById(id);

        if (EtudiantOpt.isPresent()) {
            Etudiant Etudiant = EtudiantOpt.get();

            // Récupération de tous les RefreshTokens associés au même userID
            List<RefreshToken> refreshTokens = refreshTokenRepository.findAllByUserId(Etudiant.getId());

            // Suppression de tous les RefreshTokens trouvés
            refreshTokenRepository.deleteAll(refreshTokens);

            // Suppression de l'Etudiantistrateur
            EtudiantRepository.delete(Etudiant);

            return true; // Opération de suppression réussie
        } else {
            // L'Etudiantistrateur n'existe pas avec l'ID spécifié
            return false; // Opération de suppression échouée
        }
    }
	
 
 
 
 
 public AuthenticationResponse updateEtudiant(Integer id, Etudiant Etudiant) {
     Optional<Etudiant> EtudiantOpt = EtudiantRepository.findById(id);

     if (EtudiantOpt.isPresent()) {
         Etudiant EtudiantFromDb = EtudiantOpt.get();

         // Mettre à jour les propriétés de l'objet Etudiant avec les données de l'objet Etudiant passé en paramètre
         EtudiantFromDb.setNom(Etudiant.getNom());
         EtudiantFromDb.setPrenom(Etudiant.getPrenom());
         EtudiantFromDb.setEmail(Etudiant.getEmail());
         EtudiantFromDb.setLogin(Etudiant.getLogin());
         EtudiantFromDb.setTelephone(Etudiant.getTelephone());
         

         // Vérifier si le mot de passe a été modifié
         if (Etudiant.getPassword() != null && !Etudiant.getPassword().isEmpty()) {
             // Hacher le nouveau mot de passe
             String hashedPassword = passwordEncoder.encode(Etudiant.getPassword());
             EtudiantFromDb.setPassword(hashedPassword);
         }

         // Sauvegarder les modifications dans la base de données
         Etudiant updatedEtudiant = EtudiantRepository.save(EtudiantFromDb);

         // Générer un nouveau token JWT avec les informations mises à jour
         String jwt = jwtService.generateToken(updatedEtudiant);

         // Créer un nouveau refresh token pour l'utilisateur
         RefreshToken refreshToken = refreshTokenService.createRefreshToken(updatedEtudiant.getId());

         // Récupérer les rôles de l'utilisateur mis à jour et les convertir en une liste de String
         List<String> roles = updatedEtudiant.getRole().getAuthorities().stream()
                 .map(SimpleGrantedAuthority::getAuthority)
                 .collect(Collectors.toList());

         // Construire et renvoyer la réponse
         return AuthenticationResponse.builder()
                 .accessToken(jwt)
                 .email(updatedEtudiant.getEmail())
                 .refreshToken(refreshToken.getToken())
                 .roles(roles)
                 .tokenType(TokenType.BEARER.name())
                 .build();
     } else {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
     }
 }


	
	
public AuthenticationResponse register(Etudiant request) {
	  Etudiant Etudiant = new Etudiant(
	            request.getNom(),
	            request.getPrenom(),
	            request.getEmail(),
	            passwordEncoder.encode(request.getPassword()),
	            request.getLogin(),
	            request.getTelephone(),
	            Role.ETUDIANT
	        );
     Etudiant = EtudiantRepository.save(Etudiant);
     var jwt = jwtService.generateToken(Etudiant);
     var refreshToken = refreshTokenService.createRefreshToken(Etudiant.getId());

     var roles = Etudiant.getRole().getAuthorities()
             .stream()
             .map(SimpleGrantedAuthority::getAuthority)
             .toList();

     return AuthenticationResponse.builder()
             .accessToken(jwt)
             .email(Etudiant.getEmail())
             .id(Etudiant.getId())
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
    Etudiant Etudiant = EtudiantRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
    
    // Récupérez les rôles de l'Etudiantistrateur
    var roles = Etudiant.getRole().getAuthorities()
            .stream()
            .map(SimpleGrantedAuthority::getAuthority)
            .toList();

    // Générez le jeton JWT et le jeton de rafraîchissement
    var jwt = jwtService.generateToken(Etudiant);
    var refreshToken = refreshTokenService.createRefreshToken(Etudiant.getId());

    return AuthenticationResponse.builder()
            .accessToken(jwt)
            .roles(roles)
            .email(Etudiant.getEmail())
            .id(Etudiant.getId())
            .refreshToken(refreshToken.getToken())
            .tokenType(TokenType.BEARER.name())
            .build();
}




}
