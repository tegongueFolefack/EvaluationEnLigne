package com.example.EvaluationEnLigne.DTO;

import org.modelmapper.ModelMapper;

import com.example.EvaluationEnLigne.DTO.UserDTO;
import com.example.EvaluationEnLigne.Models.User;

import lombok.Data;

@Data
public class UserDTO {
	private Integer id;
	private String nom;
	  private String prenom;
	  private String email;
	  private String password;
	  private String login;
	  private String telephone;
	
	public User toUtilisateur() {
	    ModelMapper modelMapper = new ModelMapper();
	    return modelMapper.map(this, User.class);
	}
	

}
