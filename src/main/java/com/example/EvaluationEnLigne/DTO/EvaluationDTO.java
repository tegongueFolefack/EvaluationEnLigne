package com.example.EvaluationEnLigne.DTO;

import org.modelmapper.ModelMapper;

import com.example.EvaluationEnLigne.Models.Evaluation;

import lombok.Data;

@Data
public class EvaluationDTO {

	private Double note;
	
	  public Evaluation toEvaluation() {
	        ModelMapper modelMapper = new ModelMapper();
	        return modelMapper.map(this, Evaluation.class);
	    }
}
