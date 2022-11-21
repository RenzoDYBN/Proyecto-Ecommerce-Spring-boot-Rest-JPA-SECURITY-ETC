package com.ecommerce.renzo.personal.controller;

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
}
