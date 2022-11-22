package com.ecommerce.renzo.personal.service;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.renzo.personal.model.Usuario;

@Service
public class UserDetailServiceImpl implements UserDetailsService{

	@Autowired
	private IUsuarioService ususervice;
	
	
	//PARA QUE SE GUARDE UNA SESION DEL USUARIO CUANDO HAGA LOGIN
	@Autowired
	HttpSession session;
	
	private Logger log = LoggerFactory.getLogger(UserDetailServiceImpl.class);
	//BUSCAR EL USUARIO Y PASAR A OTRA CLASE QUE MAJERA SEGURIDADES EN SPRING
	
	//CARGARME EL USUARIO A TRAVEZ DEL USERNAME
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Este es el username ");
		
		Optional<Usuario> optusu = ususervice.findByEmail(username);
		
		//si se eneucentra en la base de datos, si coincidie 
		if(optusu.isPresent()) {
			log.info("Este es el user ID {}", optusu.get().getId());
			session.setAttribute("idUser", optusu.get().getId());
			Usuario usuario = optusu.get();
			return User.builder().username(usuario.getNombre())
					.password(usuario.getPassword())
					.roles(usuario.getTipo()).build();
		}else {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
	}

		
		
}
