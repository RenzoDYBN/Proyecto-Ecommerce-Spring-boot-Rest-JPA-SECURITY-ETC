package com.ecommerce.renzo.personal.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.renzo.personal.model.Orden;
import com.ecommerce.renzo.personal.model.Producto;
import com.ecommerce.renzo.personal.model.Usuario;
import com.ecommerce.renzo.personal.service.IOrdenService;
import com.ecommerce.renzo.personal.service.IUsuarioService;
import com.ecommerce.renzo.personal.service.ProductoService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private IUsuarioService usuService;
	
	@Autowired
	private IOrdenService ordenService;
	
	BCryptPasswordEncoder passEconder = new BCryptPasswordEncoder();
	
	
	@GetMapping("/registro")
	public String mostrar() {
		return "usuario/registro";
	}
	
	@PostMapping("/save")
	public String registrar(Usuario usuario) {
		logger.info("Usuario registro {}", usuario);
		usuario.setTipo("USER");
		usuario.setPassword(passEconder.encode(usuario.getPassword()));
		usuService.save(usuario);
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {
//		usuService.findById(null)
		
		return "usuario/login";
	}
	
	@GetMapping("/acceder")
	public String acceder(Usuario usuario, HttpSession session){
		logger.info("acceso {}",usuario);
		
		Optional<Usuario> user = usuService.findById(Integer.parseInt(session.getAttribute("idUser").toString()));
		logger.info("correo inf {}",user);
		
		//POR ESO ES BUENO TRABAJAR CON OPTIONAL SI HAY REGISTRO
		//CON ESE EMAIL , PERMITE USAR el isPresent()
		if(user.isPresent()) {
			session.setAttribute("idUser", user.get().getId());
			if(user.get().getTipo().equals("ADMIN")) {
				return "redirect:/administrador";
			}else {
				return "redirect:/";
			}
		}else {
			logger.info("Usuario no existe");
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/compras")
	public String obteenrCompras(HttpSession session, Model modelo) {
		modelo.addAttribute("sesion",session.getAttribute("idUser"));
		Usuario usuario = usuService.findById(Integer.parseInt(session.getAttribute("idUser").toString())).get();
		List<Orden> ordenes = ordenService.findByUsuario(usuario);
		
		modelo.addAttribute("ordenes", ordenes);
		return "usuario/compras";
	}
	
	@GetMapping("/verDetalle/{id}")
	public String verDetaCompras(@PathVariable Integer id, HttpSession session, Model modelo) {
		//SESSION
		
		Usuario usuario = usuService.findById(Integer.parseInt(session.getAttribute("idUser").toString())).get();
		Optional<Orden> orden = ordenService.findById(id);
		
		modelo.addAttribute("detalles", orden.get().getDetalleorden());
		modelo.addAttribute("sesion",session.getAttribute("idUser"));
		
		return "usuario/detallecompra";
	}
	
	@GetMapping("/cerrar")
	public String cerrarSession(HttpSession session) {
		session.removeAttribute("idUser");
		
		return "redirect:/";
	}
	
}
