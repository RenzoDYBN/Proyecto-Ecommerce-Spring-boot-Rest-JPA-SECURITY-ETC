package com.ecommerce.renzo.personal.service;

import java.util.List;
import java.util.Optional;

import com.ecommerce.renzo.personal.model.Producto;

public interface ProductoService  {
	public Producto save(Producto producto);
	//Optional es validar si el objeto que mandamos a llamar de la base de
	//datos existe o no
	public Optional<Producto> get(Integer id);
	
	public void update(Producto producto);
	
	public void delete(Integer id);
	
	public List<Producto> listarProductos();
	
}
