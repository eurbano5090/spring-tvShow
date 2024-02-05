package com.bolsadeideas.springboot.app.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bolsadeideas.springboot.app.models.entity.Usuario;



public interface IUsuarioService {

	void guardar(Usuario usuario);
	void eliminar(Integer idUsuario);
	List<Usuario> buscarTodos();
	Usuario buscarPorId(Integer idUsuario);
	Usuario buscarPorUsername(String username);
	int bloquear(int id);
	int activar(int id);
	Page<Usuario>mostrarTodos(Pageable page);
}

