package com.example.EvaluationEnLigne.DTO;

import org.modelmapper.ModelMapper;

import com.example.EvaluationEnLigne.Models.Admin;

public class AdminDTO extends UserDTO {
	
	public Admin toEnseignant() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Admin.class);
    }

}
