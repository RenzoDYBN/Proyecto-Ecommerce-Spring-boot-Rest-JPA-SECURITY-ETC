package com.ecommerce.renzo.personal.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.renzo.personal.model.Producto;
import com.ecommerce.renzo.personal.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {

	@Autowired
	private ProductoRepository repositorio;
	
	
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

}
