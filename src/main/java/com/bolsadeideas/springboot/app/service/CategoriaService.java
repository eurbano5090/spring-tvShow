package com.bolsadeideas.springboot.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bolsadeideas.springboot.app.models.entity.Categoria;



public interface CategoriaService {

	void guardar(Categoria categoria);
	List<Categoria>mostrarTodas();
	Categoria buscarPorId(Integer idCategoria);
	void eliminar (Integer idCategoria);
	Page<Categoria>buscarTodas(Pageable page);
}
