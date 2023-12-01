package br.com.recoleta.app.service;


import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.recoleta.app.dto.UserRegistrationDto;
import br.com.recoleta.app.model.Role;
import br.com.recoleta.app.model.User;
import br.com.recoleta.app.model.UserType;
import br.com.recoleta.app.repository.RoleRepository;
import br.com.recoleta.app.repository.UserRepository;
import br.com.recoleta.app.repository.UserTypeRepository;


@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserTypeRepository userTypeRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEnconder;
	

	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public User save(UserRegistrationDto registrationDto) {
		
		Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new IllegalArgumentException("Role not found"));
		UserType userType1 = userTypeRepository.findByName("PRODUCES_WASTE").orElseThrow(() -> new IllegalArgumentException("Role not found"));
		UserType userType2 = userTypeRepository.findByName("COLLECTS_WASTE").orElseThrow(() -> new IllegalArgumentException("Role not found"));
		
		User user = new User(registrationDto.getFirstName(), registrationDto.getLastName(),
				registrationDto.getEmail(), passwordEnconder.encode(registrationDto.getPassword()));
		
		user.setRoles(userRole);
		
		if(userType1.getName().equals(registrationDto.getUserType())) {
			user.setUserType(userType1);
		}
		
		if(userType2.getName().equals(registrationDto.getUserType())) {
			user.setUserType(userType2);
		}
		
		return userRepository.save(user);
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("usuário não encontrado.");
		}

		return new org.springframework.security.core.userdetails.User(user.getEmail(),
				user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}


	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Role role) {
	    return Stream.of(new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
	public List<User> getAll() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> findUser(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public Optional<User> editUser(Long id, User user) {
		Optional<User> userFound = userRepository.findById(id);

		if (userFound.isPresent()) {
			User existingUser = userFound.get();

			// Verificar e atualizar as propriedades que foram fornecidas no objeto 'user'
			if (user.getFirstName() != null) {
				existingUser.setFirstName(user.getFirstName());
			}
			if (user.getLastName() != null) {
				existingUser.setLastName(user.getLastName());
			}

			if(user.getUserType() != null) {
				existingUser.setUserType(user.getUserType());
			}

			if(user.getPassword() != null) {
				existingUser.setPassword(passwordEnconder.encode(user.getPassword()));
			}

			// Salvar o usuário atualizado
			User saved = userRepository.save(existingUser);
			return Optional.of(saved);
		}
		return Optional.empty();
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public void saveAdmin() {
		
		Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new IllegalArgumentException("Role not found"));
		
		User adminUser = new User("admin", null,
				"admin@admin.com", passwordEnconder.encode("admin"));
		
		adminUser.setRoles(adminRole);

		userRepository.save(adminUser);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}
