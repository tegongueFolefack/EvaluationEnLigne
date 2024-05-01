package com.example.EvaluationEnLigne.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.example.EvaluationEnLigne.DTO.EtudiantDTO;
import com.example.EvaluationEnLigne.Enum.Role;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
@Table(name = "etudiant")
@DiscriminatorValue("etudiant")
public class Etudiant extends User implements Serializable {

    private static final long serialVersionUID = 1L;
   
    
    @ManyToMany
	private Collection<Matiere> matiere = new ArrayList<>();
    
    
    @OneToMany
    private List<Evaluation> evaluation;
    
    public EtudiantDTO toEtudiantDTO() {
		ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, EtudiantDTO.class);
	}

	public Etudiant() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Etudiant(String nom, String prenom, String email, String password, String login, String telephone,
			Role role) {
		super(nom, prenom, email, password, login, telephone, role);
		// TODO Auto-generated constructor stub
	}

	
	

}
