package com.ecommerce.renzo.personal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

	@RequestMapping("")
	public String home() {
		return "administrador/home";
	}
	
}
