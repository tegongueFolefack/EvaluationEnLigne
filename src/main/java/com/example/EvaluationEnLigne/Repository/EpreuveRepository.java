package com.example.EvaluationEnLigne.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EvaluationEnLigne.Models.Epreuve;

@Repository
public interface EpreuveRepository extends JpaRepository<Epreuve, Integer>{

}
