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

import com.example.EvaluationEnLigne.DTO.MatiereDTO;
import com.example.EvaluationEnLigne.Models.Matiere;
import com.example.EvaluationEnLigne.Service.MatiereService;


@RestController
@RequestMapping("/Matiere")
public class MatiereController {
	

	@Autowired
	private MatiereService MatiereService;
	
	@GetMapping("/{id}")
    public ResponseEntity<MatiereDTO> getMatiereById(@PathVariable Integer id) {
        try {
            Optional<Matiere> Matiere = MatiereService.getMatiereById(id);
            if (Matiere.isPresent()) {
            	MatiereDTO MatiereDTO = Matiere.get().toMatiereDTO();
                return ResponseEntity.ok(MatiereDTO);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Matiere not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteMatiere(@PathVariable Integer id) {
        try {
        	MatiereService.deleteMatiere(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<MatiereDTO>> findAll() {
        try {
            Iterable<Matiere> Matieres = MatiereService.getAllMatiere();
            List<MatiereDTO> MatiereDTOs = new ArrayList<>();
            for (Matiere Matiere: Matieres) {
            	MatiereDTOs.add(Matiere.toMatiereDTO());
            }
            return ResponseEntity.ok(MatiereDTOs);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

	   

	
    @PostMapping("add/")
    public ResponseEntity<String> addMatiere(@RequestBody MatiereDTO MatiereDto) {
        Matiere Matiere = MatiereDto.toMatiere();
        Matiere savedMatiere = MatiereService.saveMatiere(Matiere);
        
        // You can customize the confirmation message here
        String confirmationMessage = "Matiere with ID " + savedMatiere.getId() + " has been added successfully.";
        
        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
    }

	
	    @PutMapping("update/{id}")
	    public ResponseEntity<MatiereDTO> updateMatiere(@PathVariable Integer id, @RequestBody MatiereDTO MatiereDto) {
	        try {
	            Optional<Matiere> MatiereOpt = MatiereService.getMatiereById(id);
	            if (MatiereOpt.isPresent()) {
	            	Matiere Matiere = MatiereOpt.get();
	                
	            	Matiere = MatiereDto.toMatiere();
	                
	            	Matiere = MatiereService.updateMatiere(id, Matiere);
	                
	                MatiereDTO MatiereResponse = Matiere.toMatiereDTO();
	                
	                return ResponseEntity.ok(MatiereResponse);
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
