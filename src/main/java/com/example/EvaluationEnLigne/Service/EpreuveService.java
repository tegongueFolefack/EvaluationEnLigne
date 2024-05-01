package com.example.EvaluationEnLigne.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.EvaluationEnLigne.Models.Epreuve;
import com.example.EvaluationEnLigne.Repository.EpreuveRepository;

@Service
public class EpreuveService {
	
	@Autowired
    private EpreuveRepository EpreuveRepository;
 

	public Optional<Epreuve> getEpreuveById(Integer id) {
		return EpreuveRepository.findById(id);
	}

	public Iterable<Epreuve> getAllEpreuve() {
		return EpreuveRepository.findAll();
	}

	public boolean deleteEpreuve(Integer id) {
		Optional<Epreuve> EpreuveOpt = getEpreuveById(id);
		if (EpreuveOpt.isPresent()) {
			EpreuveRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
	public Epreuve updateEpreuve(Integer id, Epreuve Epreuve2) {
	    Optional<Epreuve> EpreuveOpt = EpreuveRepository.findById(id);
	    
	    if (EpreuveOpt.isPresent()) {
	    	Epreuve Epreuve = EpreuveOpt.get();
	    	
	    	
	        // Mise à jour des propriétés de l'objet Comptable avec les données du ComptableDTO
	    	Epreuve.setNom(Epreuve2.getNom());
	    	Epreuve.setContenu(Epreuve2.getContenu());
	    	
	    	
	        
	        // Sauvegarder les modifications dans la base de données
	        return EpreuveRepository.save(Epreuve);
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	    }
	}

 
 public Epreuve saveEpreuve(Epreuve Epreuve) {
		return EpreuveRepository.save(Epreuve);
	}

}
