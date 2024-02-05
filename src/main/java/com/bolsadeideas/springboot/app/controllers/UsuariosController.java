package com.bolsadeideas.springboot.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.entity.Usuario;
import com.bolsadeideas.springboot.app.service.IUsuarioService;



@Controller
@RequestMapping("/usuarios")
public class UsuariosController {
	
	@Autowired
	private IUsuarioService serviceUsuarios;
	
	
	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		List<Usuario>lista= serviceUsuarios.buscarTodos();
		model.addAttribute("usuarios", lista);
		return "usuarios/listUsuarios";
	}
	
    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable("id")Integer idUsuario,RedirectAttributes attributes) {
    	serviceUsuarios.eliminar(idUsuario);
    	attributes.addFlashAttribute("msg","El usuario ha sido eliminado");
		return "redirect:/usuarios/indexPaginate";
    }
    
   @GetMapping("/unlock/{id}")
	public String activar(@PathVariable("id") int idUsuario, RedirectAttributes attributes) {		
    	serviceUsuarios.activar(idUsuario);
		attributes.addFlashAttribute("msg", "El usuario fue activado y ahora tiene acceso al sistema.");		
		return "redirect:/usuarios/indexPaginate";
	}
    
	
	@GetMapping("/lock/{id}")
	public String bloquear(@PathVariable("id") int idUsuario, RedirectAttributes attributes) {		
		serviceUsuarios.bloquear(idUsuario);
		attributes.addFlashAttribute("msg", "El usuario fue bloqueado y no tendra acceso al sistema.");		
		return "redirect:/usuarios/index";
	}
     

    @GetMapping("/indexPaginate")
    public String mostrarIndexPaginado(Model model, Pageable page) {
	Page<Usuario> lista = serviceUsuarios.mostrarTodos(page);
	model.addAttribute("usuarios", lista);
	return "usuarios/listUsuarios";
}}