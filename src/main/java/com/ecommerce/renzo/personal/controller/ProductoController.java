package com.ecommerce.renzo.personal.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.renzo.personal.model.Producto;
import com.ecommerce.renzo.personal.model.Usuario;
import com.ecommerce.renzo.personal.service.IUsuarioService;
import com.ecommerce.renzo.personal.service.ProductoService;
import com.ecommerce.renzo.personal.service.UploadFileService;

@Controller
@RequestMapping("/productos")
public class ProductoController {

	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	private IUsuarioService uservices;
	
	@Autowired
	private ProductoService servicios;

	@Autowired
	private UploadFileService servicioImagen;

	@GetMapping("")
	public String show(Model modelo) {
		modelo.addAttribute("productos", servicios.listarProductos());
		return "productos/show";
	}

	@GetMapping("/create")
	public String create(Model modelo) {
		return "productos/create";
	}

	@PostMapping("/save")
	public String save(HttpSession session , Producto producto, @RequestParam("img") MultipartFile file) throws IOException {

		// la apertura de llaves format , en la cual vendra una variable u objeto
		LOGGER.info("Este es el objeto producto {}", producto);
		
		//RECORDAR QUE TRAE UN OPTIONAL POR ESO USAR .get()
		Usuario u = uservices.findById(Integer.parseInt(session.getAttribute("idUser").toString())).get();
		producto.setUsuario(u);

		// imagen validar si una imagen es cargada por
		// primera vez siempre sera nullo la imagen
		if (producto.getId() == null) { // cuando se crea un producto
			String nombreImagen = servicioImagen.saveImage(file);
			producto.setImagen(nombreImagen);
		}else {
			
		}

		servicios.save(producto);
		return "redirect:/productos";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable(name = "id") Integer id, Model modelo) {
		Producto producto = new Producto();
		Optional<Producto> product = servicios.get(id);
		producto = product.get();

		LOGGER.info("Producto buscado: {}", producto);
		modelo.addAttribute("producto", producto);

		return "productos/edit";
	}

	@PostMapping("/procesar")
	public String editProcesar(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
		Producto p = new Producto();
		p=servicios.get(producto.getId()).get();
		
		
		if(file.isEmpty()) { //es cuando editamos el producto pero no cambiamos la imagen
			
			producto.setImagen(p.getImagen());
		}else { //CUANDO SE EDITA TBN LA IMAGEN
			
			//ELIMINAR CUANDO NO SEA LA IMAGEN POR DEFECTO.
			if(!p.getImagen().equals("default.jpg")) {			
				servicioImagen.deleteImage(p.getImagen());
			}
			
			String nombreImagen = servicioImagen.saveImage(file);
			producto.setImagen(nombreImagen);
		}
		
		producto.setUsuario(p.getUsuario());
		servicios.update(producto);

		return "redirect:/productos";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable(name = "id") Integer id, Model modelo) {

		Producto p = new Producto();
		p = servicios.get(id).get();
		
		//ELIMINAR CUANDO NO SEA LA IMAGEN POR DEFECTO.
		if(!p.getImagen().equals("default.jpg")) {			
			servicioImagen.deleteImage(p.getImagen());
		}
		
		servicios.delete(id);

		return "redirect:/productos";
	}
}
