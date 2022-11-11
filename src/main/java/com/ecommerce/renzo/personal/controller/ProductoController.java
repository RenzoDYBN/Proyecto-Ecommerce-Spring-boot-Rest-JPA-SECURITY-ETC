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

	@Autowired
	private ProductoService servicios;
	
	@GetMapping("")
	public String show(Model modelo) {
		modelo.addAttribute("productos",servicios.listarProductos());
		return "productos/show";
	}
	
	@GetMapping("/create")
	public String create(Model modelo) {
		return "productos/create";
	}
	
	@PostMapping("/save")
	public String save(Producto producto) {
		servicios.save(producto);
		return "redirect:/productos";
	}

}
