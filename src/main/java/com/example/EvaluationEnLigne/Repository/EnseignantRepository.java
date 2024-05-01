package com.example.EvaluationEnLigne.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EvaluationEnLigne.Models.Enseignant;
import com.example.EvaluationEnLigne.Models.Etudiant;

@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant, Integer>{
	Optional<Enseignant> findByEmail(String email);
}

