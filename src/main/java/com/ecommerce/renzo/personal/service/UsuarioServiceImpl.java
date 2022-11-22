package com.ecommerce.renzo.personal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.renzo.personal.model.Usuario;
import com.ecommerce.renzo.personal.repository.IUsuarioRepository;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	private IUsuarioRepository urepositorio;
	
	@Override
	public Optional<Usuario> findById(Integer id) {
		
		return urepositorio.findById(id);
	}

	@Override
	public Usuario save(Usuario usuario) {
		
		return urepositorio.save(usuario);
	}

	@Override
	public Optional<Usuario> findByEmail(String email) {
		return urepositorio.findByEmail(email);
	}

	@Override
	public List<Usuario> findAll() {
		
		return urepositorio.findAll();
	}


}
