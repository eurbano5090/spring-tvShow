package com.bolsadeideas.springboot.app.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.entity.Categoria;
import com.bolsadeideas.springboot.app.service.CategoriaService;



@Controller
@RequestMapping("/categorias")
public class CategoriasController {
	
	@Autowired
	private CategoriaService icat;

    @GetMapping("/index")
	public String mostrarIndex(Model model) {
    List <Categoria>lista=icat.mostrarTodas();
    model.addAttribute("categorias", lista);
	return "categorias/listCategorias";
	}
    
	@GetMapping(value="/indexPaginate")
	public String mostrarIndexPaginado(Model model,Pageable page) {
		Page<Categoria>lista=icat.buscarTodas(page);
		model.addAttribute("categorias",lista);
		return "categorias/listCategorias";
	}
	
    @GetMapping("/create")
	public String crear(Categoria categoria) {
	return "categorias/formCategoria";
	}

    @PostMapping("/save")
	public String guardar(Categoria categoria,BindingResult result,RedirectAttributes attributes) {
    	if(result.hasErrors()) {
			for(ObjectError error:result.getAllErrors()) {
				System.out.println("Ocurrio un error :"+ error.getDefaultMessage());
			}
			return"categorias/formCategoria";
		}
    	icat.guardar(categoria);
		attributes.addFlashAttribute("msg","Registro Guardado");
		System.out.println("Categoria"+ categoria);
		
		return "redirect:/categorias/indexPaginate";
	}
    
    @GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idCategoria,RedirectAttributes attributes) {
		System.out.println("borrando categoria con id:" + idCategoria);
		icat.eliminar(idCategoria);
		attributes.addFlashAttribute("msg","La categoria fue eliminada");
		return "redirect:/categorias/index";
		
	}
    @GetMapping("/edit/{id}")
    public String editar(@PathVariable("id") int idCategoria,Model model) {
		Categoria categoria = icat.buscarPorId(idCategoria);
		model.addAttribute("categoria",categoria);
		model.addAttribute("categorias",icat.mostrarTodas());
		return "categorias/formCategoria";
	}
	
    @InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	
}
