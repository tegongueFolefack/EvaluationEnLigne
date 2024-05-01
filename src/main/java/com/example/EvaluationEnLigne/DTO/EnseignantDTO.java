package com.example.EvaluationEnLigne.DTO;

import java.util.Date;

import org.modelmapper.ModelMapper;

import com.example.EvaluationEnLigne.Models.Enseignant;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
public class EnseignantDTO extends UserDTO {
	
	
	
	   
	    

	   
	    @Temporal(TemporalType.DATE)
	    private Date datePriseEns;

	    public Enseignant toEnseignant() {
	        ModelMapper modelMapper = new ModelMapper();
	        return modelMapper.map(this, Enseignant.class);
	    }
}
