package com.ecommerce.renzo.personal.controller;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.renzo.personal.model.Producto;
import com.ecommerce.renzo.personal.model.Usuario;
import com.ecommerce.renzo.personal.service.ProductoService;

@Controller
@RequestMapping("/productos")
public class ProductoController {
	
	//Logger , para hacer pruebas en la parte del backend
	//system.print con ello para saber si la variable llega 
	// lo recomendable  es usar un logger que nos indica todos los cambios que
	//se realiza
	//NOS AYUDA A VERIFICAR DONDE SE ENCUENTRAN LOS ERRORES DE MANERA PROFUNDA
	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	private ProductoService servicios;
	
	@GetMapping("")
	public String show() {
		return "productos/show";
	}
	
	@GetMapping("/create")
	public String create(Model modelo) {
		return "productos/create";
	}
	
	@PostMapping("/save")
	public String save(Producto producto) {
		//la apertura de llaves format , en la cual vendra una variable u objeto
		LOGGER.info("Este es el objeto producto {}",producto);
		Usuario u =new Usuario(1, "", "", "", "", "", "", "");
		producto.setUsuario(u);
		servicios.save(producto);
		return "redirect:/productos";
	}

}
