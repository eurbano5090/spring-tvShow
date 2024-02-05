package com.bolsadeideas.springboot.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bolsadeideas.springboot.app.models.entity.Show;



@Repository
public interface ShowRepository extends JpaRepository<Show, Integer>{

	void save(Optional<Show> currentShow);

	List<Show> findByDestacado(Integer destacado);
	
	List<Show>findByCategoria(Integer Categoria);
}
