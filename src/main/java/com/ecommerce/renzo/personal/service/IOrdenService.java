package com.ecommerce.renzo.personal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.renzo.personal.model.Orden;
import com.ecommerce.renzo.personal.model.Usuario;

@Service
public interface IOrdenService {
	
	List<Orden> findAll();
	
	Orden save(Orden orden);
	
	String generarNumeroOrden();
	
	List<Orden> findByUsuario (Usuario usuario);
	
	Optional<Orden> findById(Integer id);
}
