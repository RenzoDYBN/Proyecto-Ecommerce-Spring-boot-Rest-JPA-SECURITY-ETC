package com.ecommerce.renzo.personal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.renzo.personal.model.DetalleOrden;
import com.ecommerce.renzo.personal.repository.IDetalleOrdenRepository;

@Service
public class DetalleOrdenServiceImpl implements IDetalleOrdenService{

	@Autowired
	private IDetalleOrdenRepository detallerepositorio;
	
	@Override
	public DetalleOrden save(DetalleOrden detalleOrden) {
		return detallerepositorio.save(detalleOrden);
	}

}
