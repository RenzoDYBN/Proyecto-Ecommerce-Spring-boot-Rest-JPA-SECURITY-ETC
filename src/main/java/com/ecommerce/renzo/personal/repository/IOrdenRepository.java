package com.ecommerce.renzo.personal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.renzo.personal.model.Orden;
import com.ecommerce.renzo.personal.model.Usuario;

@Repository
public interface IOrdenRepository extends JpaRepository<Orden, Integer>{

	//CREAR METODO NOS PERMITA OBTENER TODAS LAS ORDENES POR USUARIO
	//REPO HACER CUALQUIER FILTRO ESTE ATRIBUTO , ESTE COMO PROPIEDAD O
	//TIENE QUE ESTAR COMO PROPIEDAD O ATRIBUTO LA CLASE ORDEN 

	List<Orden> findByUsuario (Usuario usuario);
	 
}
