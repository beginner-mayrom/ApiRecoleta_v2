package br.com.recoleta.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.recoleta.app.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

	Optional<Role> findByName(String role);

}
