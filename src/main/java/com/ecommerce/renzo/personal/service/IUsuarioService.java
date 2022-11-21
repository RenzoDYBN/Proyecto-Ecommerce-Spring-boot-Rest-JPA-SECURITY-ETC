package com.ecommerce.renzo.personal.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.renzo.personal.model.Usuario;

@Service
public interface IUsuarioService {

	Optional<Usuario> findById(Integer id);
	Usuario save(Usuario usuario);
}
