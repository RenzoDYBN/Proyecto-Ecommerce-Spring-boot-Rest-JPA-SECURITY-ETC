package com.ecommerce.renzo.personal.controller;

import java.util.Optional;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.renzo.personal.model.Producto;
import com.ecommerce.renzo.personal.service.ProductoService;

@Controller
@RequestMapping("/productos")
public class ProductoController {

	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
	
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

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable(name="id") Integer id,Model modelo) {
		Producto producto = new Producto();
		Optional<Producto> product = servicios.get(id);
		producto = product.get();
		
		LOGGER.info("Producto buscado: {}", producto);
		modelo.addAttribute("producto", producto);
		
		return "productos/edit";
	}
	
	@PostMapping("/procesar")
	public String editProcesar(Producto producto) {

		servicios.update(producto);
		
		return "redirect:/productos";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable(name="id") Integer id,Model modelo) {
		
		servicios.delete(id);
		
		return "redirect:/productos";
	}
}
