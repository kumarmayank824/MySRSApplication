package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long>{
  
}

