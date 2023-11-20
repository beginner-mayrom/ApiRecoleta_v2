package br.com.recoleta.app.controller;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.recoleta.app.dto.UserRegistrationDto;
import br.com.recoleta.app.model.User;
import br.com.recoleta.app.service.UserService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

	private UserService userService;

	private AuthenticationManager authenticationManager;

	/*
	 * @ModelAttribute("user") public UserRegistrationDto userRegistrationDto() {
	 * return new UserRegistrationDto(); }
	 */

	/*
	 * @GetMapping public String showRegistrationForm() { return "registration"; }
	 */
	
	/*
	 * public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto
	 * registrationDto) {
	 * 
	 * userService.save(registrationDto); return "redirect:/registration?success"; }
	 */

	@PostMapping("/registration")
	public ResponseEntity<User> registerUserAccount(@RequestBody UserRegistrationDto registrationDto){
		
		User save = userService.save(registrationDto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(save);
		
	}
	
	/*
	 * @PostMapping("/login") public ResponseEntity<User>
	 * loadUserAccount(@RequestBody User user){
	 * 
	 * String email = user.getEmail(); String password = user.getPassword();
	 * 
	 * UserDetails loadUserByUsername = userServiceImpl.loadUserByUsername(email);
	 * 
	 * if(loadUserByUsername.getPassword() == password) {
	 * 
	 * return ResponseEntity.ok().build();
	 * 
	 * } return ResponseEntity.notFound().build(); }
	 */
	
	@PostMapping("/login")
	public ResponseEntity<?> loadUserAccount(@RequestBody User user) {
	    String email = user.getEmail();
	    String password = user.getPassword();

	    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

	    try {
	        Authentication authentication = authenticationManager.authenticate(authenticationToken);
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        return ResponseEntity.ok(authenticationToken);
	    } catch (AuthenticationException e) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getUser(@PathVariable Long id) {
		
		Optional<User> userExists = userService.findUser(id);
		
		if(userExists.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(userExists.get());
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<User>> findall(){
		List<User> allUsers = userService.getAll();
		return ResponseEntity.ok(allUsers); 
	}
	
	@PutMapping("/{id}")
	ResponseEntity<User> editUser(@PathVariable Long id, @RequestBody User user) {

		Optional<User> actualUser = userService.editUser(id, user);

		if(actualUser.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(actualUser.get());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		userService.deleteUser(id);
		return ResponseEntity.ok().build();
	}
	
	@PostConstruct
    public void initAdmin() {
        userService.saveAdmin();
    }

}
