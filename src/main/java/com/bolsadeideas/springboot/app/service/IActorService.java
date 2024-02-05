package com.bolsadeideas.springboot.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bolsadeideas.springboot.app.models.entity.Actor;

public interface IActorService {

	List<Actor> findAll();
   Actor buscarPorId(Long idActor);
	Actor guardar(Actor actor);
	List<Actor>buscarDestacadas();
	void eliminar (Long idActor);
	List<Actor>buscarByExample(Example<Actor> example);
	Page<Actor>buscarTodas(Pageable page);
}
