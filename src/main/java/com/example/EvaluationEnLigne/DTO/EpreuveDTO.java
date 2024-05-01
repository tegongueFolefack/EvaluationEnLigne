package com.example.EvaluationEnLigne.DTO;

import org.modelmapper.ModelMapper;

import com.example.EvaluationEnLigne.Models.Epreuve;

import lombok.Data;

@Data
public class EpreuveDTO {
	
	
	
		
		private String nom;
		private String contenu;
		
		  public Epreuve toEpreuve() {
		        ModelMapper modelMapper = new ModelMapper();
		        return modelMapper.map(this, Epreuve.class);
		    }

		

}
