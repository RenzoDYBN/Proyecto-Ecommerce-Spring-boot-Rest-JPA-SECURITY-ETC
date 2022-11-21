package com.ecommerce.renzo.personal.service;

import org.springframework.stereotype.Service;

import com.ecommerce.renzo.personal.model.DetalleOrden;

@Service
public interface IDetalleOrdenService {
	DetalleOrden save(DetalleOrden detalleOrden);
	
}
