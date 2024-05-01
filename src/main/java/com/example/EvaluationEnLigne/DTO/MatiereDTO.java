package com.example.EvaluationEnLigne.DTO;

import org.modelmapper.ModelMapper;

import com.example.EvaluationEnLigne.Models.Matiere;

import lombok.Data;

@Data
public class MatiereDTO {
	
	private String label;
	  
    private int credit;
    
    public Matiere toMatiere() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Matiere.class);
    }

}
