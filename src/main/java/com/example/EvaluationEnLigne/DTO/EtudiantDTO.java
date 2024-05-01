package com.example.EvaluationEnLigne.DTO;

import org.modelmapper.ModelMapper;

import com.example.EvaluationEnLigne.Models.Etudiant;

import lombok.Data;

@Data
public class EtudiantDTO extends UserDTO {
	
	
	
	   
	    
	  public Etudiant toEtudiant() {
	        ModelMapper modelMapper = new ModelMapper();
	        return modelMapper.map(this, Etudiant.class);
	    }
}
