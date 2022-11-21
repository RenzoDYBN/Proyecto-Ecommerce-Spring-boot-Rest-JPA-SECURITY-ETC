package com.ecommerce.renzo.personal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.renzo.personal.model.Orden;

@Service
public interface IOrdenService {
	
	List<Orden> findAll();
	
	Orden save(Orden orden);
	
	String generarNumeroOrden();
}
