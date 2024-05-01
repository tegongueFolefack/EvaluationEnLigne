package com.example.EvaluationEnLigne.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.EvaluationEnLigne.DTO.EvaluationDTO;
import com.example.EvaluationEnLigne.Models.Evaluation;
import com.example.EvaluationEnLigne.Service.EvaluationService;


@RestController
@RequestMapping("/Evaluation")
public class EvaluationController {
	

	@Autowired
	private EvaluationService EvaluationService;
	
	@GetMapping("/{id}")
    public ResponseEntity<EvaluationDTO> getEvaluationById(@PathVariable Integer id) {
        try {
            Optional<Evaluation> Evaluation = EvaluationService.getEvaluationById(id);
            if (Evaluation.isPresent()) {
            	EvaluationDTO EvaluationDTO = Evaluation.get().toEvaluationDTO();
                return ResponseEntity.ok(EvaluationDTO);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Evaluation not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable Integer id) {
        try {
        	EvaluationService.deleteEvaluation(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<EvaluationDTO>> findAll() {
        try {
            Iterable<Evaluation> Evaluations = EvaluationService.getAllEvaluation();
            List<EvaluationDTO> EvaluationDTOs = new ArrayList<>();
            for (Evaluation Evaluation: Evaluations) {
            	EvaluationDTOs.add(Evaluation.toEvaluationDTO());
            }
            return ResponseEntity.ok(EvaluationDTOs);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

	   

	
    @PostMapping("add/")
    public ResponseEntity<String> addEvaluation(@RequestBody EvaluationDTO EvaluationDto) {
        Evaluation Evaluation = EvaluationDto.toEvaluation();
        Evaluation savedEvaluation = EvaluationService.saveEvaluation(Evaluation);
        
        // You can customize the confirmation message here
        String confirmationMessage = "Evaluation with ID " + savedEvaluation.getId() + " has been added successfully.";
        
        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
    }

	
	    @PutMapping("update/{id}")
	    public ResponseEntity<EvaluationDTO> updateEvaluation(@PathVariable Integer id, @RequestBody EvaluationDTO EvaluationDto) {
	        try {
	            Optional<Evaluation> EvaluationOpt = EvaluationService.getEvaluationById(id);
	            if (EvaluationOpt.isPresent()) {
	            	Evaluation Evaluation = EvaluationOpt.get();
	                
	            	Evaluation = EvaluationDto.toEvaluation();
	                
	            	Evaluation = EvaluationService.updateEvaluation(id, Evaluation);
	                
	                EvaluationDTO EvaluationResponse = Evaluation.toEvaluationDTO();
	                
	                return ResponseEntity.ok(EvaluationResponse);
	            } else {
	                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	            }
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }
	    
	    
	    	
	    @ExceptionHandler(ResponseStatusException.class)
	    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
	        return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<String> handleException(Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
	    }


}
