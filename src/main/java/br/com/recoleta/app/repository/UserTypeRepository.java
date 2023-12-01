package br.com.recoleta.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.recoleta.app.model.UserType;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {
	
	Optional<UserType> findByName(String userType);

}
