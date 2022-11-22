package com.ecommerce.renzo.personal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.renzo.personal.model.Producto;
import com.ecommerce.renzo.personal.model.Usuario;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Integer> {
	
	
}
