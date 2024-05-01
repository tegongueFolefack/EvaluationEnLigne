package com.example.EvaluationEnLigne.Models;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;

import com.example.EvaluationEnLigne.DTO.EpreuveDTO;

@Data
@AllArgsConstructor
@Entity
public class Epreuve {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String nom;
	private String contenu;
	
	@ManyToOne
	private Enseignant enseignant;
	
	@OneToMany
	private List<Epreuve>Questions = new ArrayList<>();
	
	public EpreuveDTO toEpreuveDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, EpreuveDTO.class);
    }

}
