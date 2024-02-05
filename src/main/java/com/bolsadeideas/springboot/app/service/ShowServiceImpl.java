package com.bolsadeideas.springboot.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.app.models.entity.Show;
import com.bolsadeideas.springboot.app.repositories.ShowRepository;



@Service
public class ShowServiceImpl implements ShowService {

	@Autowired
	private ShowRepository showRepository;
	
	private List<Show>show;
	

	public List<Show> getShow() {
		return show;
	}

	public void setShow(List<Show> show) {
		this.show = show;
	}

	// Buscar uno
	public Show findById(Integer id) {
		Optional<Show> optionalShow = showRepository.findById(id);            
		if (optionalShow.isPresent()) {
			return optionalShow.get();
		} else {
			return showRepository.findById(id).orElse(null);
		}
	}

	// Find All 
	public List<Show> findAllShows() {
		return showRepository.findAll();
	}
	
	// Create - Save
	public void saveShow(Show show) {
		showRepository.save(show);
	}

	// Create - Update
	public void updateShow(Integer id) {
		
		Optional<Show> currentShow=showRepository.findById(id);
		 showRepository.save(currentShow);
	}

	// Delete
	public void deleteShow(Integer id) {
		showRepository.deleteById(id);
	}

	@Override
	public Page<Show> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return showRepository.findAll(pageable);
	}

	@Override
	public List<Show> buscarDestacados() {
		// TODO Auto-generated method stub
		return showRepository.findByDestacado(1);
	}

	@Override
	public List<Show>buscarByExample(Example<Show> example) {
		return showRepository.findAll(example);
		
	}

	@Override
	public List<Show> findByCategoria(Integer Categoria) {
		// TODO Auto-generated method stub
		return showRepository.findByCategoria(Categoria);
}
}