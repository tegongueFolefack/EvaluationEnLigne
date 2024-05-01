package com.example.EvaluationEnLigne.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EvaluationEnLigne.Models.Etudiant;

public interface EtudiantRepository extends JpaRepository<Etudiant, Integer>{
	Optional<Etudiant> findByEmail(String email);

}
