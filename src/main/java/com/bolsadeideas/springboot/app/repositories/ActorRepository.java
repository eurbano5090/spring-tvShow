package com.bolsadeideas.springboot.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bolsadeideas.springboot.app.models.entity.Actor;


public interface ActorRepository extends JpaRepository<Actor, Long>{

	List<Actor> findByDestacado(int destacado);
}

