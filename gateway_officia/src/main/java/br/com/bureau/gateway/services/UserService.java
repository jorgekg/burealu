package br.com.bureau.gateway.services;

import java.util.Date;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.bureau.gateway.exception.EmailExistsException;
import br.com.bureau.gateway.exception.ObjectNotFoundException;
import br.com.bureau.gateway.models.User;
import br.com.bureau.gateway.repositories.UserRepository;
import br.com.bureau.gateway.security.UserSS;

@Service
public class UserService {

	@Inject
	private UserRepository userRepository;

	@Inject
	private BCryptPasswordEncoder bCryptPasswordEncode;

	public User find(Integer id) {
		Optional<User> optional = this.userRepository.findById(id);
		if (!optional.isPresent()) {
			throw new ObjectNotFoundException(id.toString());
		}
		return optional.get();
	}

	public User findEmail(String email) {
		User user = this.userRepository.findByEmail(email);
		if (user == null) {
			throw new ObjectNotFoundException("This e-mail not exists");
		}
		return user;
	}

	public User create(User user) {
		try {
			this.findEmail(user.getEmail());
			throw new EmailExistsException();
		} catch (ObjectNotFoundException e) {
		} catch (EmailExistsException e) {
			throw e;
		}
		user.setPassword(this.bCryptPasswordEncode.encode(user.getPassword()));
		user.setCreateAt(new Date());
		user.setUpdateAt(new Date());
		return this.userRepository.save(user);
	}

	public String toCrypt(String password) {
		return this.bCryptPasswordEncode.encode(password);
	}

	public User getUserLogged() {
		return this.find(this.authenticated().getId());
	}
	
	public UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}

}
