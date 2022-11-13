package com.ecommerce.renzo.personal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.renzo.personal.model.Producto;
import com.ecommerce.renzo.personal.service.ProductoService;

@Controller
@RequestMapping("/") //esto apunta a la raiz 
public class HomeController {
	
	//VER LA TRANZABILIDAD DE LAS VARIABLES Y CODIGO
	private Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	
	
	@Autowired //permit inyectar una instancia de la clase ProductService.
	private ProductoService servicios;
	
	@RequestMapping("")
	public String mostar(Model modelo) {
		modelo.addAttribute("productos", servicios.listarProductos());
		return "usuario/home";
	}
	
	@GetMapping("/productoHome/{id}")
	public String productoHome(Model modelo, @PathVariable Integer id) {
		LOGGER.info("Id producto enviado como parametro {}",id);
		Producto prod = servicios.get(id).get();
		LOGGER.info("Producto para enviar {}",prod);
		modelo.addAttribute("producto",prod);
		return "usuario/productohome";
	}
	
	
}
