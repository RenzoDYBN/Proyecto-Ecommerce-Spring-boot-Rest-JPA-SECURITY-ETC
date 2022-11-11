package com.ecommerce.renzo.personal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.renzo.personal.model.Producto;
import com.ecommerce.renzo.personal.service.ProductoService;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {
	
	@Autowired
	private ProductoService servicios;
	
	@RequestMapping("")
	public String home(Model modelo) {
		List<Producto> productos = servicios.listarProductos();
		modelo.addAttribute("productos",productos);
		
		return "administrador/home";
	}
	
}
