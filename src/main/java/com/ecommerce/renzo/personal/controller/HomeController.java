package com.ecommerce.renzo.personal.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.renzo.personal.model.DetalleOrden;
import com.ecommerce.renzo.personal.model.Orden;
import com.ecommerce.renzo.personal.model.Producto;
import com.ecommerce.renzo.personal.model.Usuario;
import com.ecommerce.renzo.personal.service.DetalleOrdenServiceImpl;
import com.ecommerce.renzo.personal.service.IDetalleOrdenService;
import com.ecommerce.renzo.personal.service.IOrdenService;
import com.ecommerce.renzo.personal.service.IUsuarioService;
import com.ecommerce.renzo.personal.service.ProductoService;

@Controller
//esto apunta a la raiz 
@RequestMapping("/")
public class HomeController {

	// VER LA TRANZABILIDAD DE LAS VARIABLES Y CODIGO
	private Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	@Autowired // permit inyectar una instancia de la clase ProductService.
	private ProductoService servicios;
	
	@Autowired
	private IUsuarioService uservice;
	
	@Autowired
	private IOrdenService oservice;
	
	@Autowired
	private IDetalleOrdenService detservice;
	
	
	// guardaremos una lista de detalles (detalles de la orden donde se alamacenera)
	// Para almacenar los productos de la orden (o detalles de la orden)
	List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();

	// ALMACENA LOS DATOS DE LA ORDEN DATOS DE LA ORDEN
	Orden orden = new Orden();

	@RequestMapping("")
	public String mostar(Model modelo,HttpSession session) {
		modelo.addAttribute("productos", servicios.listarProductos());
		
		LOGGER.info("obtener usuario {}",session.getAttribute("idUser"));
		return "usuario/home";
	}

	// TU CUANDO COLOCAS UN {id} tu le estas enviando un parametro men.
	@GetMapping("/productoHome/{id}")
	public String productoHome(Model modelo, @PathVariable Integer id) {
		LOGGER.info("Id producto enviado como parametro {}", id);
		Producto prod = servicios.get(id).get();
		LOGGER.info("Producto para enviar {}", prod);
		modelo.addAttribute("producto", prod);
		return "usuario/productohome";
	}

	@PostMapping("/cart")
	public String addCart(HttpSession session, @RequestParam Integer id, @RequestParam Integer cantidad, Model modelo) {
		DetalleOrden detalleOrden = new DetalleOrden();
		Producto product = servicios.get(id).get();
		double sumaTotal = 0;

		LOGGER.info("Producto en si para enviar {}", product);
		LOGGER.info("Producto cantidad para enviar {}", cantidad);

		detalleOrden.setCantidad(cantidad);
		detalleOrden.setPrecio(product.getPrecio());
		detalleOrden.setNombre(product.getNombre());
		detalleOrden.setTotal(product.getPrecio() * cantidad);
		detalleOrden.setProducto(product);

		Integer idProducto = product.getId();
		boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId() == idProducto);

		if (!ingresado) {
			detalles.add(detalleOrden);
		}

		// ok parare aqui para estudiar funcion LAMBDA regreso en 1 o 2 dias. go.
		// FUNCION LAMBDA
		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

		orden.setTotal(sumaTotal);
		LOGGER.info("viendo orden 1 {}", orden);
//		modelo.addAttribute("cart",detalles);
//		modelo.addAttribute("orden",orden);

		session.setAttribute("cart", detalles);
		session.setAttribute("orden", orden);
		LOGGER.info("viendo orden 2 {}", orden);

		return "redirect:/carritoListar";
	}

	//PARA QUE SE MANTENGAN LOS DATOS SIEMPRE USAR session.setAttribute en las 
	// 
	@GetMapping("/carritoListar")
	public String productoHomes(HttpSession session) {
//		session.setAttribute("cart", detalles);
//		session.setAttribute("orden", orden);
		return "usuario/carrito";
	}

	@GetMapping("/deleteCart/cart/{id}")
	public String quitarElemento(HttpSession session, @PathVariable Integer id, Model modelo) {
		// lista nueva de productos
		List<DetalleOrden> ordenesNueva = new ArrayList<DetalleOrden>();

		for (DetalleOrden detalleOrden : detalles) {
			if (detalleOrden.getProducto().getId() != id) {
				ordenesNueva.add(detalleOrden);
			}
		}

		// Lista nueva con los productos restantes
		detalles = ordenesNueva;

		Double sumaTotal = 0.0;

		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
		orden.setTotal(sumaTotal);
		session.setAttribute("cart", detalles);
		session.setAttribute("orden", orden);
		return "redirect:/carritoListar";
	}
	
	
	@GetMapping("/order")
	public String order(Model modelo, HttpSession session) {
		
		
			//COMO ES UN OBJETO SE USA TOSTRING session .toString para
			//parsear a integer
		Usuario user = uservice.findById(Integer.parseInt(session.getAttribute("idUser").toString())).get();

		modelo.addAttribute("usuario", user);
		return "usuario/resumenorden";
	}
	
	@GetMapping("/saveOrder")
	public String saveOrder(HttpSession session) {
		Date fechaCreacion = new Date();
		orden.setFechaCreacion(fechaCreacion);
		orden.setNumero(oservice.generarNumeroOrden());
		
		//Usuario
		Usuario user = uservice.findById(Integer.parseInt(session.getAttribute("idUser").toString())).get();
		
		orden.setUsuario(user);
		oservice.save(orden);
		
		
		//Detalles
		for(DetalleOrden dt:detalles) {
			dt.setOrden(orden);
			detservice.save(dt);
		}
		
		//limpiar Lista y orden
		orden = new Orden();
		detalles.clear();
		return "redirect:/";
	}	
	
	@PostMapping("/search")
	public String buscar(@RequestParam String nombre, Model modelo) {
		LOGGER.info("Nombre del producto: {}", nombre);
		
		
		List<Producto> productos = servicios.listarProductos().stream()
				.filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
				.collect(Collectors.toList());
		modelo.addAttribute("productos",productos);
		
		
		return "usuario/home";
	}
}
