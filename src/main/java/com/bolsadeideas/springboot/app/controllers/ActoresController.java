package com.bolsadeideas.springboot.app.controllers;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.entity.Actor;
import com.bolsadeideas.springboot.app.models.entity.Show;
import com.bolsadeideas.springboot.app.service.CategoriaService;
import com.bolsadeideas.springboot.app.service.IActorService;
import com.bolsadeideas.springboot.app.service.IUploadFileService;
import com.bolsadeideas.springboot.app.service.ShowService;
import com.bolsadeideas.springboot.app.util.Util;



@Controller
@RequestMapping("/actores")
public class ActoresController {
	
	
	@Autowired
	private IActorService actorService;
	
	@Autowired
	private IUploadFileService uploadService;
	
	@Autowired
	private ShowService showService;
	
	@Autowired
	private CategoriaService icat;
	
	@GetMapping("/crear")
	public String crear(Actor actor) {
	
		return "actores/formActor";
	}
	
/*	
	@PostMapping("/save")
	public String guardar(Actor actor,BindingResult result,RedirectAttributes attributes,Model model,
			RedirectAttributes flash,@RequestParam("files") List<MultipartFile> files,SessionStatus status) throws IOException {
		if(result.hasErrors()) {
			for(ObjectError error:result.getAllErrors()) {
				System.out.println("Ocurrio un error :"+ error.getDefaultMessage());
			}
			return"actores/formActor";
		}
		if (!files.isEmpty()) {
			
			String nombreFoto;
			try {
				nombreFoto = (String) uploadService.saveMultipleFiles(files);
				actor.setFoto(nombreFoto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String nombreImagen = (String) uploadService.saveMultipleFiles(files);
			
			
			actor.setImagenPortada(nombreImagen);
			}
		actorService.guardar(actor);
		attributes.addFlashAttribute("msg","Registro Guardado");
		System.out.println("Actor"+ actor);
	
		return "redirect:/actores/listActores";
}
	
*/
	@PostMapping("/save")
	public String guardar(Actor actor, BindingResult result, Model model, 
			@RequestParam("file") MultipartFile multipart, RedirectAttributes flash, SessionStatus status) {
		
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear show");
			return "formActor";
		}
		
		if (!multipart.isEmpty()) {
			
			Path directorioRecursos = Paths.get("src//main//resources//static//img-actores");

			Path directorioRecursos2 = Paths.get("src//main//resources//static//img-actores");
			String rootPath = directorioRecursos.toFile().getAbsolutePath();
			String rootPath2 = directorioRecursos2.toFile().getAbsolutePath();
			try {

				byte[] bytes = multipart.getBytes();
				Path rutaCompleta = Paths.get(rootPath + "//" + multipart.getOriginalFilename());
				Path rutaCompleta2 = Paths.get(rootPath2 + "//" + multipart.getOriginalFilename());
				Files.write(rutaCompleta, bytes);
				Files.write(rutaCompleta2, bytes);
				flash.addFlashAttribute("info", "Has subido correctamente '" + multipart.getOriginalFilename() + "'");
 
				actor.setFoto(multipart.getOriginalFilename());
				actor.setImagenPortada(multipart.getOriginalFilename());
                
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String mensajeFlash = (actor.getId() != null) ? "Actor editado con éxito!" : "Actor creado con éxito!";

		actorService.guardar(actor);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/actores/listActores";
	
	}
	
	@GetMapping("/edit/{id}")
    public String editar(@PathVariable("id") Long idActor,Model model) {
		Actor actor = actorService.buscarPorId(idActor);
		model.addAttribute("actor",actor);
		return "actores/formActor";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
	webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		List<Actor>lista=actorService.findAll();
		model.addAttribute("actores", lista);
		return "actores/listActores";
	}
	
	@GetMapping(value="/indexPaginate")
	public String mostrarIndexPaginado(Model model,Pageable page) {
		Page<Actor>lista=actorService.buscarTodas(page);
		model.addAttribute("actores",lista);
		return "actores/listActores";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") Long idActor,RedirectAttributes attributes, Model model) {
		System.out.println("borrando Actor con id:" + idActor);
		actorService.eliminar(idActor);
		attributes.addFlashAttribute("msg","El actor ha sido eliminado");
		return "redirect:/actores/index";
		
	}

	@GetMapping("/view/{id}")
	public String verDetalle(@PathVariable("id") Long idActor,Model model) {
		
		Actor actor=actorService.buscarPorId(idActor);
		List<Show>shows=showService.findAllShows();
		System.out.println("IdActor:" + idActor);
		model.addAttribute("actor", actor);
		model.addAttribute("shows", shows);
		
		return "actores/detalle";
		
		
	}
}
