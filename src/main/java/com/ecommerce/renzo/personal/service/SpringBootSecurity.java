package com.ecommerce.renzo.personal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


//LA CLASE SERA DE CONFIGURACION PARA LA INTERCEPCION DE DATOS
//ENABLEWEBSECURITY CONTIENE LA CONFIGURACION ES UN INTERCEPTOR DEL USUARIO Y SISTEMA
//GRACIAS A ESTA PERMITIRA EL INGRESO DE PAGINAS DE ACUERDO AL ROL DEL USUARIO.

@EnableWebSecurity
@Configuration
public class SpringBootSecurity {

		Logger log = LoggerFactory.getLogger(SpringBootSecurity.class);
		
		@Bean
		public AuthenticationManager authenticationManager(
		        AuthenticationConfiguration authConfig) throws Exception {
		    return authConfig.getAuthenticationManager();
		}
	    
	    @Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	 
	    @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	     //restringir ciertas cosas al usurio
	    	//csrf ayuda a que no se inyecte codigo malicioso ala aplicacion
	    	//metodo disable
	    	//authorizerequest
	    	 //antmatcher a que vistas va a tener acceso de acuerdo al rol
	    	
	    	http.csrf().disable().authorizeRequests().antMatchers("/administrador/**")
	    	.hasRole("ADMIN").antMatchers("/productos/**").hasRole("ADMIN").and().formLogin()
	    	.loginPage("/usuario/login").permitAll().defaultSuccessUrl("/usuario/acceder");
	    	
	 
	        return http.build();
	    }
	
	
	
}
