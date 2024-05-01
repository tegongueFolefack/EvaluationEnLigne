package com.example.EvaluationEnLigne.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.EvaluationEnLigne.Models.Matiere;
import com.example.EvaluationEnLigne.Repository.MatiereRepository;

@Service
public class MatiereService {
	
	@Autowired
    private MatiereRepository MatiereRepository;
 

	public Optional<Matiere> getMatiereById(Integer id) {
		return MatiereRepository.findById(id);
	}

	public Iterable<Matiere> getAllMatiere() {
		return MatiereRepository.findAll();
	}

	public boolean deleteMatiere(Integer id) {
		Optional<Matiere> MatiereOpt = getMatiereById(id);
		if (MatiereOpt.isPresent()) {
			MatiereRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
	public Matiere updateMatiere(Integer id, Matiere Matiere2) {
	    Optional<Matiere> MatiereOpt = MatiereRepository.findById(id);
	    
	    if (MatiereOpt.isPresent()) {
	    	Matiere Matiere = MatiereOpt.get();
	    	
	    	
	        // Mise à jour des propriétés de l'objet Comptable avec les données du ComptableDTO
	    	Matiere.setLabel(Matiere2.getLabel());
	    	Matiere.setCredit(Matiere2.getCredit());
	    	
	    	
	        
	        // Sauvegarder les modifications dans la base de données
	        return MatiereRepository.save(Matiere);
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	    }
	}

 
 public Matiere saveMatiere(Matiere Matiere) {
		return MatiereRepository.save(Matiere);
	}

}
