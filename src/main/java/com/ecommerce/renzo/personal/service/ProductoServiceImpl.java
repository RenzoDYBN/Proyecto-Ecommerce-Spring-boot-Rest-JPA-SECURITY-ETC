package com.ecommerce.renzo.personal.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.renzo.personal.model.Producto;
import com.ecommerce.renzo.personal.model.Usuario;
import com.ecommerce.renzo.personal.repository.IProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {

	@Autowired
	private IProductoRepository repositorio;
	

	
	//Logger , para hacer pruebas en la parte del backend
	//system.print con ello para saber si la variable llega 
	// lo recomendable  es usar un logger que nos indica todos los cambios que
	//se realiza
	//NOS AYUDA A VERIFICAR DONDE SE ENCUENTRAN LOS ERRORES DE MANERA PROFUNDA
	private  Logger LOGGER = LoggerFactory.getLogger(ProductoService.class);
	
	@Override
	public Producto save(Producto producto) {
		
		return repositorio.save(producto);
	}

	@Override
	public Optional<Producto> get(Integer id) {
		return repositorio.findById(id);
	}

	@Override
	public void update(Producto producto) {
		repositorio.save(producto);
		
	}

	@Override
	public void delete(Integer id) {
		repositorio.deleteById(id);
		
	}

	@Override
	public List<Producto> listarProductos() {
		// TODO Auto-generated method stub
		return repositorio.findAll();
	}

}
