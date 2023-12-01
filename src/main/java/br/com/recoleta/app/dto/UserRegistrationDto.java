package br.com.recoleta.app.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegistrationDto {
	
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String userType;

}
