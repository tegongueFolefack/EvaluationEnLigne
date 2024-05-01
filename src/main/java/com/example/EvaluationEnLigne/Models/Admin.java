package com.example.EvaluationEnLigne.Models;

import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.example.EvaluationEnLigne.DTO.AdminDTO;
import com.example.EvaluationEnLigne.DTO.EnseignantDTO;
import com.example.EvaluationEnLigne.Enum.Role;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;


@Entity
@AllArgsConstructor
@Table(name = "etudiant")
@DiscriminatorValue("admin")
public class Admin extends User{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AdminDTO toAdminDTO() {
		ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, AdminDTO.class);
	}

public Admin(Integer id, String nom, String prenom, String email, String password, String login, String telephone,
		Role role) {
	super(id, nom, prenom, email, password, login, telephone, role);
	// TODO Auto-generated constructor stub
}

public Admin(String nom, String prenom, String email, String password, String login, String telephone, Role role) {
	super(nom, prenom, email, password, login, telephone, role);
	// TODO Auto-generated constructor stub
}
	
	

}
