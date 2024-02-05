package com.bolsadeideas.springboot.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.bolsadeideas.springboot.app.models.entity.Rating;
import com.bolsadeideas.springboot.app.models.entity.Show;
import com.bolsadeideas.springboot.app.repositories.RatingRepository;
import com.bolsadeideas.springboot.app.repositories.ShowRepository;

@Service
public class RatingServiceImpl implements RatingService {

	@Autowired
	private RatingRepository ratingRepository;

	@Autowired
	private ShowRepository showRepository;
	
	public List<Rating> findAllRatings() {
			return ratingRepository.findAll();
	}

	public Rating findRatingById(Integer id) {
		Optional<Rating> optional=ratingRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	public void addRating(Rating rating) {
		ratingRepository.save(rating);
	}






}