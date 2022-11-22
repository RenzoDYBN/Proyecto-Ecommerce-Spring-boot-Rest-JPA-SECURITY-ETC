package com.ecommerce.renzo.personal.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.renzo.personal.model.Orden;
import com.ecommerce.renzo.personal.model.Producto;
import com.ecommerce.renzo.personal.model.Usuario;
import com.ecommerce.renzo.personal.service.IOrdenService;
import com.ecommerce.renzo.personal.service.IUsuarioService;
import com.ecommerce.renzo.personal.service.ProductoService;


@Controller
@RequestMapping("/administrador")
public class AdministradorController {
	
	@Autowired
	private ProductoService servicios;
	
	@Autowired
	private IUsuarioService uservices;
	
	@Autowired
	private IOrdenService oservices;
	
	
	private Logger log = LoggerFactory.getLogger(AdministradorController.class);
	
	@RequestMapping("")
	public String home(Model modelo) {
		List<Producto> productos = servicios.listarProductos();
		modelo.addAttribute("productos",productos);
		
		return "administrador/home";
	}
	
	@GetMapping("/usuarios")
	public String usuarios(Model modelo) {
		
		List<Usuario> obtUsu = uservices.findAll();
		
		modelo.addAttribute("usuarios",obtUsu);
		
		return "administrador/usuarios";
	}
	
	@GetMapping("/ordenes")
	public String ordenes(Model modelo) {
		
		modelo.addAttribute("ordenes", oservices.findAll());
		
		return "administrador/ordenes";
	}
	
	@GetMapping("/detalle/{id}")
	public String detalle(Model modelo, @PathVariable Integer id) {
		
		log.info("Id de la orden {}",id);
		Orden orden =oservices.findById(id).get();
		modelo.addAttribute("detallorden",orden.getDetalleorden());
		return "administrador/detalleorden";
	}
	
	
	
}
