package com.bolsadeideas.springboot.app.config;


import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
		.requestMatchers("/uploads/**","/img-portada/**", "/img-actores/**", "/style.css/**", "/script.js/**").permitAll()
		.requestMatchers("/home", "/", "/login", "/listar/**","/search", "/registration/**","/bcrypt/**",
				"/ver/**","/form/**","/actores/**","/editar/**").permitAll()
		.requestMatchers("/categorias/**", "/delete/**").hasAnyAuthority("ADMINISTRADOR","SUPERVISOR")
		.requestMatchers("/usuarios/**").hasAnyAuthority("ADMINISTRADOR")
        .anyRequest().authenticated().and().formLogin().permitAll();
		return http.build();
	}

	@Bean
	public UserDetailsManager users(DataSource dataSource) {
	JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
	users.setUsersByUsernameQuery("select username, password, estatus from usuarios where username=?");
	users.setAuthoritiesByUsernameQuery("select u.username, p.perfil from usuario_perfil up " + 
	"inner join usuarios u on u.id = up.id_usuario " + 
	"inner join perfiles p on p.id = up.id_perfil " + 
	"where u.username = ?");
	return users;


	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();

	}
}
