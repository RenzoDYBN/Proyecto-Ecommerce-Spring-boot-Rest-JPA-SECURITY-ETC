package com.ecommerce.renzo.personal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.renzo.personal.service.ProductoService;

@Controller
@RequestMapping("/") //esto apunta a la raiz 
public class HomeController {
	
	@Autowired //permit inyectar una instancia de la clase ProductService.
	private ProductoService servicios;
	
	@RequestMapping("")
	public String mostar(Model modelo) {
		modelo.addAttribute("productos", servicios.listarProductos());
		return "usuario/home";
	}
	
}
