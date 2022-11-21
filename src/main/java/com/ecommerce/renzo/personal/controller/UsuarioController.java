package com.ecommerce.renzo.personal.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.renzo.personal.model.Usuario;
import com.ecommerce.renzo.personal.service.IUsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private IUsuarioService usuService;
	
	@GetMapping("/registro")
	public String mostrar() {
		return "usuario/registro";
	}
	
	@PostMapping("/save")
	public String registrar(Usuario usuario) {
		logger.info("Usuario registro {}", usuario);
		usuario.setTipo("USER");
		
		usuService.save(usuario);
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {
//		usuService.findById(null)
		
		return "usuario/login";
	}
	
	@PostMapping("/acceder")
	public String acceder(Usuario usuario, HttpSession session){
		logger.info("acceso {}",usuario);
		
		Optional<Usuario> user = usuService.findByEmail(usuario.getEmail());
		logger.info("correo inf {}",user);
		
		//POR ESO ES BUENO TRABAJAR CON OPTIONAL SI HAY REGISTRO
		//CON ESE EMAIL , PERMITE USAR el isPresent()
		if(user.isPresent()) {
			session.setAttribute("idUser", user.get().getId());
			if(user.get().getTipo().equals("ADMIN")) {
				return "redirect:/administrador";
			}else {
				return "redirect:/";
			}
		}else {
			logger.info("Usuario no existe");
		}
		
		return "redirect:/";
	}
	
}
