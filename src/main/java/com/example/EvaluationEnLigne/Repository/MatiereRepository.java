package com.example.EvaluationEnLigne.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EvaluationEnLigne.Models.Matiere;

@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Integer>{

}
