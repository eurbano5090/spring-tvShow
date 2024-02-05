package com.bolsadeideas.springboot.app.service;

import java.util.List;

import com.bolsadeideas.springboot.app.models.entity.Rating;
import com.bolsadeideas.springboot.app.models.entity.Show;


public interface RatingService {

	public List<Rating> findAllRatings();
	public Rating findRatingById(Integer id);
	public void addRating(Rating rating);

}
