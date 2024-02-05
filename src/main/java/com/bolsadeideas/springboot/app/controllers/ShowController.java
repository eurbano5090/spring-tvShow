package com.bolsadeideas.springboot.app.controllers;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.entity.Show;
import com.bolsadeideas.springboot.app.service.CategoriaService;
import com.bolsadeideas.springboot.app.service.IUploadFileService;
import com.bolsadeideas.springboot.app.service.IUsuarioService;
import com.bolsadeideas.springboot.app.service.RatingService;
import com.bolsadeideas.springboot.app.service.ShowService;


@Controller
@SessionAttributes("shows")
public class ShowController {

	@Autowired
	private ShowService showService;
	
	@Autowired
	private RatingService ratingService;
	
	@Autowired
	private CategoriaService icat;
	
	@Autowired
	private IUsuarioService userService;
	
	@Autowired
	private IUploadFileService uploadFileService;


	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value="id") Integer id, Map<String, Object> model, RedirectAttributes flash) {

		Show show = showService.findById(id);
		if (show == null) {
			flash.addFlashAttribute("error", "El Show no existe en la base de datos");
			return "redirect:/listar";
		}

		model.put("show",show);
		model.put("foto", show.getFoto());
		model.put("network",show.getShowNetwork());
		
		model.put("duracion", show.getDuracion());
		model.put("descripcion",show.getDescripcion());
		return "ver";
	}

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar( Model model) {
		
		List<Show>todosShows=showService.findAllShows();
		model.addAttribute("titulo", "Listado de Shows");
		model.addAttribute("titles",todosShows);
		return "buscar";
	}

	//crear
	@RequestMapping(value = "/form")
	public String crear(@ModelAttribute Show show) {

		return "form";
	}


	@PostMapping("/form")
	public String guardar(Show show, BindingResult result, Model model, 
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {
		
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear show");
			return "form";
		}
		
		if (!foto.isEmpty()) {
			
			Path directorioRecursos = Paths.get("src//main//resources//static//uploads");
			String rootPath = directorioRecursos.toFile().getAbsolutePath();

			try {

				byte[] bytes = foto.getBytes();
				Path rutaCompleta = Paths.get(rootPath + "//" + foto.getOriginalFilename());
				Files.write(rutaCompleta, bytes);
				flash.addFlashAttribute("info", "Has subido correctamente '" + foto.getOriginalFilename() + "'");

				show.setFoto(foto.getOriginalFilename());

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String mensajeFlash = (show.getId() != null) ? "Show editado con éxito!" : "Show creado con éxito!";

		showService.saveShow(show);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listar";
	
	}
	//eliminar
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Integer id, RedirectAttributes flash) {

		if (id > 0) {
			showService.deleteShow(id);
			flash.addFlashAttribute("success", "Show eliminado con éxito!");
		}
		return "redirect:/listar";
	}
	
	// Update
	   @GetMapping(value = "/editar/{id}")
		public String editShow(@PathVariable("id")Integer id, Model model) {
			Show show = showService.findById(id);
			model.addAttribute("show", show);
			model.addAttribute("ratings",ratingService.findAllRatings());
			return "form";
		} 

		@PostMapping(value = "/save/{id}")
		public String updateShow(Show show,@PathVariable(value="id") Integer id,Model model, RedirectAttributes flash) {
			Show showUpdate = showService.findById(id);
			if (id > 0) {
				showService.updateShow(id);
				model.addAttribute("show", showUpdate);
				flash.addFlashAttribute("success", "Show update con éxito!");
			}
			return "listar";
		}
		
		@GetMapping("/search")
		public String buscar(@ModelAttribute("search") Show show,Model model) {
			System.out.println("buscando por:" + show);
		
		//	show.setDestacado(1);
			ExampleMatcher matcher= ExampleMatcher.
					matching().withMatcher("descripcion", ExampleMatcher.GenericPropertyMatchers.
							contains());
		
			Example<Show> exam=Example.of(show,matcher);
			List<Show>filtro=showService.buscarByExample(exam);
		    model.addAttribute("shows", filtro);
			System.out.println("shows encontrados:"+ filtro.size());
			return "buscar";
			
		}
		

		 @ModelAttribute
	     public void setGenericos(Model model) {
	     Show showSearch=new Show();
	     List<Show>todosShows=showService.findAllShows();
         showSearch.reset();
         model.addAttribute("shows",todosShows);
	     model.addAttribute("categorias",icat.mostrarTodas());
	     model.addAttribute("search", showSearch);
		 }
		
 /* @GetMapping(value = "/uploads/{filename:.+}")
   public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

	Resource recurso = null;

	try {
		recurso = uploadFileService.load(filename);
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
			.body(recurso);
}*/
}
		






