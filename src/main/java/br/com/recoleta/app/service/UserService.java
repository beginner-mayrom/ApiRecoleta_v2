package br.com.recoleta.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import br.com.recoleta.app.dto.UserRegistrationDto;
import br.com.recoleta.app.model.User;

public interface UserService extends UserDetailsService{
	
	User save(UserRegistrationDto registrationDto);
	
	List<User> getAll();
	
	Optional<User> findUser(Long id);

	Optional<User> editUser(Long id, User user);

	void deleteUser(Long id);

	void saveAdmin();

	User findByEmail(String email);

}
