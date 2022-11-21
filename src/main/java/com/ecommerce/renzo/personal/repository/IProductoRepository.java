package com.ecommerce.renzo.personal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.renzo.personal.model.Producto;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Integer> {

}
