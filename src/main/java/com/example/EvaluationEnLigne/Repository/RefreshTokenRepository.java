package com.example.EvaluationEnLigne.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.EvaluationEnLigne.Request.RefreshToken;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	 
	  Optional<RefreshToken> findByToken(String token);
	  
	  @Query("SELECT rf FROM RefreshToken rf WHERE rf.user.id = :userId")
	  Optional<RefreshToken> findByUserId(Long userId);
	  List<RefreshToken> findAllByUserId(Integer integer);

	 
}