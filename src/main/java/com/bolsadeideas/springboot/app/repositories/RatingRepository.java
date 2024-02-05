package com.bolsadeideas.springboot.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bolsadeideas.springboot.app.models.entity.Rating;


@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer>{

}
