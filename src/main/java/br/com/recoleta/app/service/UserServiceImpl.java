package br.com.recoleta.app.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import br.com.recoleta.app.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{


	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEnconder;

	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public User save(UserRegistrationDto registrationDto) {

		User user = new User(registrationDto.getFirstName(), registrationDto.getLastName(),
				registrationDto.getEmail(), passwordEnconder.encode(registrationDto.getPassword()),
				Arrays.asList(new Role("ROLE_USER")), Arrays.asList(new UserType("COLLECTS_WASTE")));

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

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){

		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
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

}
