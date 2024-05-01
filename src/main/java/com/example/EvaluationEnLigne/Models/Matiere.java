package com.example.EvaluationEnLigne.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.example.EvaluationEnLigne.DTO.MatiereDTO;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class Matiere implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String label;
  
    private int credit;
   
   
    
    @ManyToMany 
	private Collection<Enseignant>  ensiegnant = new ArrayList<>();
    
    @OneToMany
    private List<Evaluation> evaluation; 
    
    @ManyToMany
    private List<Etudiant> etudiant ;
    
    public MatiereDTO toMatiereDTO() {
		ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, MatiereDTO.class);
	}


}
