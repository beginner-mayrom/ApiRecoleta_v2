package br.com.recoleta.app.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import br.com.recoleta.app.dto.UserRegistrationDto;
import br.com.recoleta.app.model.User;

public interface UserService extends UserDetailsService{
	
	User save(UserRegistrationDto registrationDto);
	List<User> getAll();

}
