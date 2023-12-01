package br.com.recoleta.app.service;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.recoleta.app.model.UserType;
import br.com.recoleta.app.repository.UserTypeRepository;

@Service
public class UserTypeService {
	
	@Autowired
	private UserTypeRepository userTypeRepository;

	@Autowired
	private EntityManager entityManager;

	public void salveUserType(UserType userType) {
		userType = entityManager.merge(userType); // Reanexa a entidade ao contexto de persistência
		userTypeRepository.save(userType); // Salva a entidade reanexada
	}

	public void saveUserTypeProduces() {

		Optional<UserType> existingUserTypeProduces = userTypeRepository.findByName("PRODUCES_WASTE");
		if (existingUserTypeProduces.isEmpty()) {
			UserType TypeProduces = new UserType("PRODUCES_WASTE");
			userTypeRepository.save(TypeProduces);
		}
	}

	public void saveUserTypeCollects() {

		Optional<UserType> existingUserTypeCollects = userTypeRepository.findByName("COLLECTS_WASTE");
		if (existingUserTypeCollects.isEmpty()) {
			UserType TypeCollects = new UserType("COLLECTS_WASTE");
			userTypeRepository.save(TypeCollects);
		}
	}
}
