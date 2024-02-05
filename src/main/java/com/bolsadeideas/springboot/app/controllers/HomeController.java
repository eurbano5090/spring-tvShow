package com.bolsadeideas.springboot.app.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.entity.Actor;
import com.bolsadeideas.springboot.app.models.entity.Perfil;
import com.bolsadeideas.springboot.app.models.entity.Show;
import com.bolsadeideas.springboot.app.models.entity.Usuario;
import com.bolsadeideas.springboot.app.service.IActorService;
import com.bolsadeideas.springboot.app.service.IUsuarioService;
import com.bolsadeideas.springboot.app.service.ShowService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	private ShowService showS;

	@Autowired
	private IUsuarioService userS;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private IActorService actorService;


	 public HomeController() { new ArrayList<Show>(); }
	

	public void setShow(Show show) {

	}

	@GetMapping( "/")
	public String listar(Model model) {

		List<Show> shows = showS.buscarDestacados();
		List<Show> todosShows = showS.findAllShows();
		List<Actor>actores=actorService.buscarDestacadas();
		model.addAttribute("titulo", "Listado de Shows");
		model.addAttribute("titles", todosShows);
		model.addAttribute("actores",actores);
		model.addAttribute("shows", shows);

		return "home";
	}

	@GetMapping("/bcrypt/{texto}")
	@ResponseBody
	public String encriptar(@PathVariable("texto") String texto) {
		return texto + " encriptado en Bcrypt :" + passwordEncoder.encode(texto);
	}

	@GetMapping("/login")
	public String mostrarLogin() {
		return "login";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(request, null, null);
		return "redirect:/";
	}

	@GetMapping("/index")
	public String mostrarIndex(Authentication authentication, HttpSession session) {

		String username = authentication.getName();
		for (GrantedAuthority rol : authentication.getAuthorities()) {
			System.out.println("ROL: " + rol.getAuthority());
		}

		if (session.getAttribute("user") == null) {
			Usuario user = userS.buscarPorUsername(username);
			System.out.println("user :" + user);
			session.setAttribute("user", user);
		}

		return "redirect:/";
	}

	@GetMapping("/registration")
	public String registerForm(@Valid @ModelAttribute("user") Usuario user) {
		return "registration";
	}

	@PostMapping("/registration")
	public String registration(Usuario usuario, BindingResult result,
			RedirectAttributes attributes) {
		String pwdPlano = usuario.getPassword();
		
		String pwdEncriptado = passwordEncoder.encode(pwdPlano); 
		usuario.setPassword(pwdEncriptado);	
		usuario.setEstatus(1); // Activado por defecto
		usuario.setFechaRegistro(new Date()); // Fecha de Registro, la fecha actual del servidor
	
		Perfil perfil = new Perfil();
		perfil.setId(3); // Perfil USUARIO
		usuario.agregar(perfil);
		
		userS.guardar(usuario);
				
		attributes.addFlashAttribute("msg", "Has sido registrado. ¡Ahora puedes ingresar al sistema!");
		
		return "redirect:/login";
	}
	}


