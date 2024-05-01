package com.example.EvaluationEnLigne.Models;

import java.io.Serializable;

import org.modelmapper.ModelMapper;

import com.example.EvaluationEnLigne.DTO.EvaluationDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class Evaluation implements Serializable {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private Double note;
	
	@ManyToOne
	private Enseignant enseignant;
    
    @ManyToOne
    private Etudiant etudiant;
    
    @ManyToOne
    private Matiere matiere;
    
    public EvaluationDTO toEvaluationDTO() {
		ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, EvaluationDTO.class);
	}

    
    

}
