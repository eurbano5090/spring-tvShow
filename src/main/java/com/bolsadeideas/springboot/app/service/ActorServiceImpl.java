package com.bolsadeideas.springboot.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.app.models.entity.Actor;
import com.bolsadeideas.springboot.app.repositories.ActorRepository;



@Service
public class ActorServiceImpl implements IActorService {


	
	@Autowired
	private ActorRepository acRepo;

	@Override
	public List<Actor> findAll() {
		// TODO Auto-generated method stub
		return acRepo.findAll();
	}

	@Override
	 public Actor buscarPorId(Long idActor) {
	        return acRepo.getById(idActor);
	        }

	@Override
	public Actor guardar(Actor actor) {
	 return acRepo.save(actor);

	}

	@Override
	public List<Actor> buscarDestacadas() {
		
		return acRepo.findByDestacado(1);
	}

	@Override
	public void eliminar(Long idVacante) {
		acRepo.deleteById(idVacante);
		
	}

	@Override
	public List<Actor> buscarByExample(Example<Actor> example) {
		return acRepo.findAll(example);
		
	}

	@Override
	public Page<Actor> buscarTodas(Pageable page) {
		//
		return acRepo.findAll(page);
	}

}
