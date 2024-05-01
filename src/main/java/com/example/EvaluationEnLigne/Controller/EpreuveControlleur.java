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

import com.example.EvaluationEnLigne.DTO.EpreuveDTO;
import com.example.EvaluationEnLigne.Models.Epreuve;
import com.example.EvaluationEnLigne.Service.EpreuveService;


@RestController
@RequestMapping("/Epreuve")
public class EpreuveControlleur {
	

	@Autowired
	private EpreuveService EpreuveService;
	
	@GetMapping("/{id}")
    public ResponseEntity<EpreuveDTO> getEpreuveById(@PathVariable Integer id) {
        try {
            Optional<Epreuve> Epreuve = EpreuveService.getEpreuveById(id);
            if (Epreuve.isPresent()) {
            	EpreuveDTO EpreuveDTO = Epreuve.get().toEpreuveDTO();
                return ResponseEntity.ok(EpreuveDTO);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Epreuve not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteEpreuve(@PathVariable Integer id) {
        try {
        	EpreuveService.deleteEpreuve(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<EpreuveDTO>> findAll() {
        try {
            Iterable<Epreuve> Epreuves = EpreuveService.getAllEpreuve();
            List<EpreuveDTO> EpreuveDTOs = new ArrayList<>();
            for (Epreuve Epreuve: Epreuves) {
            	EpreuveDTOs.add(Epreuve.toEpreuveDTO());
            }
            return ResponseEntity.ok(EpreuveDTOs);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

	   

	
    @PostMapping("add/")
    public ResponseEntity<String> addEpreuve(@RequestBody EpreuveDTO EpreuveDto) {
        Epreuve Epreuve = EpreuveDto.toEpreuve();
        Epreuve savedEpreuve = EpreuveService.saveEpreuve(Epreuve);
        
        // You can customize the confirmation message here
        String confirmationMessage = "Epreuve with ID " + savedEpreuve.getId() + " has been added successfully.";
        
        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
    }

	
	    @PutMapping("update/{id}")
	    public ResponseEntity<EpreuveDTO> updateEpreuve(@PathVariable Integer id, @RequestBody EpreuveDTO EpreuveDto) {
	        try {
	            Optional<Epreuve> EpreuveOpt = EpreuveService.getEpreuveById(id);
	            if (EpreuveOpt.isPresent()) {
	            	Epreuve Epreuve = EpreuveOpt.get();
	                
	            	Epreuve = EpreuveDto.toEpreuve();
	                
	            	Epreuve = EpreuveService.updateEpreuve(id, Epreuve);
	                
	                EpreuveDTO EpreuveResponse = Epreuve.toEpreuveDTO();
	                
	                return ResponseEntity.ok(EpreuveResponse);
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
