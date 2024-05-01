package com.example.EvaluationEnLigne.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.EvaluationEnLigne.Models.Evaluation;
import com.example.EvaluationEnLigne.Repository.EvaluationRepository;

@Service
public class EvaluationService {
	
	@Autowired
    private EvaluationRepository EvaluationRepository;
 

	public Optional<Evaluation> getEvaluationById(Integer id) {
		return EvaluationRepository.findById(id);
	}

	public Iterable<Evaluation> getAllEvaluation() {
		return EvaluationRepository.findAll();
	}

	public boolean deleteEvaluation(Integer id) {
		Optional<Evaluation> EvaluationOpt = getEvaluationById(id);
		if (EvaluationOpt.isPresent()) {
			EvaluationRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
	public Evaluation updateEvaluation(Integer id, Evaluation Evaluation2) {
	    Optional<Evaluation> EvaluationOpt = EvaluationRepository.findById(id);
	    
	    if (EvaluationOpt.isPresent()) {
	    	Evaluation Evaluation = EvaluationOpt.get();
	    	
	    	
	        // Mise à jour des propriétés de l'objet Comptable avec les données du ComptableDTO
	    	Evaluation.setNote(Evaluation2.getNote());
	    	
	    	
	    	
	        
	        // Sauvegarder les modifications dans la base de données
	        return EvaluationRepository.save(Evaluation);
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	    }
	}

 
 public Evaluation saveEvaluation(Evaluation Evaluation) {
		return EvaluationRepository.save(Evaluation);
	}

}
