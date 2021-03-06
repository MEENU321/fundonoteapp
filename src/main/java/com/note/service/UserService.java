package com.note.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.note.model.LoginRequest;
import com.note.model.UserDetails;




public interface UserService {

	public UserDetails UserRegistration(UserDetails user,HttpServletRequest request);

	public List<UserDetails> login(LoginRequest user);

	public UserDetails updateUser(String token,UserDetails user);

	public boolean deleteUser(String token);
	
	public String securePassword(String password);
	//public UserDetails retrieveLogin(String email, String password) ;
	/*
	 * public String jwtToken(int id);
	 * 
	 * public int parseJWT(String jwt);
	 */
	
	public List<UserDetails> findByEmailId(String email);
	
	public Optional<UserDetails> findById(int id);
	
	public String sendmail(String subject, UserDetails userdetails,String appUrl);

	//List<UserDetails> loginTest(String email, String password);

//	public  List<UserDetails> loginTest(String email, String password);
		

	//public List<UserDetails> fetchData();

	//public String login(LoginRequest loginReq); 
}