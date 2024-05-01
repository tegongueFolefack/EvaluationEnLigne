package com.example.EvaluationEnLigne.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.example.EvaluationEnLigne.DTO.EnseignantDTO;
import com.example.EvaluationEnLigne.Enum.Role;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@Entity
@AllArgsConstructor
@Table(name = "enseigant")
@DiscriminatorValue("enseignant")
public class Enseignant extends User  {
    
    
	private static final long serialVersionUID = 1L;
   
	@Temporal(TemporalType.DATE)
    private Date datePriseEns;

    // Autres attributs et méthodes de la classe...

    @PrePersist
    public void prePersist() {
        this.datePriseEns = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.datePriseEns = new Date();
    }
    
    @OneToMany 
   	private List<Evaluation> evaluations =new ArrayList<>();
    
    @ManyToMany
	private Collection<Matiere> matiere = new ArrayList<>();
    
    public Enseignant() {
		super();
		// TODO Auto-generated constructor stub
	}

	
       
	@OneToMany
	private List<Epreuve>epreuves = new ArrayList<>();

	public EnseignantDTO toEnseignantDTO() {
		ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, EnseignantDTO.class);
	}

	public Enseignant(Integer id, String nom, String prenom, String email, String password, String login,
			String telephone, Role role) {
		super(id, nom, prenom, email, password, login, telephone, role);
		// TODO Auto-generated constructor stub
	}

	public Enseignant(String nom, String prenom, String email, String password, String login, String telephone,
			Role role, Date datePriseEns) {
		super(nom, prenom, email, password, login, telephone, role);
		
		// TODO Auto-generated constructor stub
	}

	public Enseignant(Date datePriseEns) {
		super();
		this.datePriseEns = datePriseEns;
	}

	

	

	

	

	

	

	
	
	
	

}
