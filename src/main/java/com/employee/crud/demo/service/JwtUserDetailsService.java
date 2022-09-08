package com.employee.crud.demo.service;

import java.util.ArrayList;

import com.employee.crud.demo.model.UserDao;
import com.employee.crud.demo.repository.JwtUserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private JwtUserDetailsRepository jwtUserDetailsRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDao user = jwtUserDetailsRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}
	
	public UserDao save(com.employee.crud.demo.model.User user) {
		UserDao newUser = new UserDao();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		return jwtUserDetailsRepository.save(newUser);
	}
}