package br.com.recoleta.app.service;


import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.recoleta.app.model.Role;
import br.com.recoleta.app.repository.RoleRepository;




@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private EntityManager entityManager;

	public void salveRole(Role role) {
		role = entityManager.merge(role); // Reanexa a entidade ao contexto de persistência
		roleRepository.save(role); // Salva a entidade reanexada
	}

	public void saveRoleAdmin() {

		Optional<Role> existingRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
		if (existingRoleAdmin.isEmpty()) {
			Role roleAdmin = new Role("ROLE_ADMIN");
			roleRepository.save(roleAdmin);
		}
	}

	public void saveRoleUser() {

		Optional<Role> existingRoleAdmin = roleRepository.findByName("ROLE_USER");
		if (existingRoleAdmin.isEmpty()) {
			Role roleAdmin = new Role("ROLE_USER");
			roleRepository.save(roleAdmin);
		}
	}

}
