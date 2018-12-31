 package com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.Rating;
import com.repository.RatingRepository;

@Service
public class RatingService {
   
	@Autowired
	RatingRepository ratingRepository;

	public void saveRatingAndComment(Rating ratingObj) {
		ratingRepository.save(ratingObj);
	}
	
	
}
