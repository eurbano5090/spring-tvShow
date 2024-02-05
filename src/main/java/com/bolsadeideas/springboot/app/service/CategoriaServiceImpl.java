package com.bolsadeideas.springboot.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.app.models.entity.Categoria;
import com.bolsadeideas.springboot.app.repositories.CategoriasRepository;



@Service
@Primary
public class CategoriaServiceImpl implements CategoriaService {

	
	@Autowired
	private CategoriasRepository catRepo;
	
	@Override
	public void guardar(Categoria categoria) {
		catRepo.save(categoria);

	}

	@Override
	public List<Categoria> mostrarTodas() {
		
		return catRepo.findAll();
	}

	@Override
	public Categoria buscarPorId(Integer idCategoria) {
		Optional<Categoria> optional=catRepo.findById(idCategoria);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public void eliminar(Integer idCategoria) {
		catRepo.deleteById(idCategoria);
		
	}

	@Override
	public Page<Categoria> buscarTodas(Pageable page) {
		
		return catRepo.findAll(page);
	}

}
