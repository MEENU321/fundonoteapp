package com.note.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.note.model.*;



	public interface UserService {

		public User UserRegistration(User user);

		public List<User> login(User user);

		public User updateUser(String token,User user);

		public boolean deleteUser(String token);

		/*
		 * public String jwtToken(int id);
		 * 
		 * public int parseJWT(String jwt);
		 */
		
		public List<User> findByEmailId(String email);
		
		public Optional<User> findById(int id);
		
		public String sendmail(String subject, User userdetails,String appUrl); 
	}
	
