package br.com.recoleta.app.controller;

import org.springframework.security.core.Authentication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponse {
	
	private Long userId;
	private Authentication authentication;
	

}
