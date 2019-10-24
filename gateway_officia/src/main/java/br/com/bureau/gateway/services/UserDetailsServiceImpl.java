package br.com.bureau.gateway.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.bureau.gateway.models.User;
import br.com.bureau.gateway.models.enums.Role;
import br.com.bureau.gateway.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService service;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = this.service.findEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException(email);
		}
		List<Role> roles = user.getRoles().stream().map(role -> role.getRole()).collect(Collectors.toList());
		return new UserSS(user.getId(), user.getEmail(), user.getPassword(), roles);
	}

}
