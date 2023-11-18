package br.com.recoleta.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_type")
public class UserType {
	
	@Id
	@SequenceGenerator(name = "user_type_sequence", sequenceName = "user_type_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	
	public UserType(String name) {
		super();
		this.name = name;
	}
	
	//UserType userType1 = new UserType("PRODUCES_WASTE", 1L);
	//UserType userType2 = new UserType("COLLECTS_WASTE", 2L);
}
