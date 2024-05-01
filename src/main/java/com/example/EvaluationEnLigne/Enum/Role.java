package com.example.EvaluationEnLigne.Enum;



import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;


import lombok.Getter;
import static com.example.EvaluationEnLigne.Enum.Privilege.*;
import lombok.RequiredArgsConstructor;


	
	@RequiredArgsConstructor
	public enum Role {
	  
		 ENSEIGNANT(
			      Set.of(READ_PRIVILEGE,WRITE_PRIVILEGE,UPDATE_PRIVILEGE,DELETE_PRIVILEGE)
			 ),
		 ADMIN(
				 Set.of(READ_PRIVILEGE,WRITE_PRIVILEGE,UPDATE_PRIVILEGE,DELETE_PRIVILEGE)
				 ),
			  ETUDIANT(
			      Set.of(READ_PRIVILEGE,WRITE_PRIVILEGE,UPDATE_PRIVILEGE,DELETE_PRIVILEGE)
			 );
			  
			 
			  @Getter
			  private final Set<Privilege> privileges;
			 
			  

			public List<SimpleGrantedAuthority> getAuthorities(){
			    List<SimpleGrantedAuthority> authorities = getPrivileges()
			       .stream()
			       .map(privilege -> new SimpleGrantedAuthority(privilege.name()))
			       .collect(Collectors.toList());
			    authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
			    return authorities;
			 }

}
