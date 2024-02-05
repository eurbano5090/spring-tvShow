package com.bolsadeideas.springboot.app.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bolsadeideas.springboot.app.models.entity.Show;




public interface ShowService {

	public Show findById(Integer id);
	public List<Show> findAllShows();
	public Page<Show> findAll(Pageable pageable);
	public void saveShow(Show show) ;
	public void updateShow(Integer id);
	public void deleteShow(Integer id);
	List<Show>buscarDestacados();
	List<Show>buscarByExample(Example<Show> example);
	List<Show>findByCategoria(Integer Categoria);
}
