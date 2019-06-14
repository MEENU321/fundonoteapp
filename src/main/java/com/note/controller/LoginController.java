package com.note.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.note.model.LoginRequest;
import com.note.model.UserDetails;
import com.note.repository.UserRepository;
import com.note.service.UserService;
import com.note.util.Utility;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class LoginController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private Utility utility;

//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public String userLogin(@RequestBody UserDetails user, HttpServletRequest request, HttpServletResponse response) {
//		List<UserDetails> userList = userService.login(user);
//		if (userList.size() != 0) {
//			String token = Utility.jwtToken(userList.get(0).getId());
//			response.setHeader("JwtToken", token);
//			return "Welcome " + userList.get(0).getUserName() + " JWT--->" + token;
//		} else
//			return "Invalid Credentials";
//
//	}
	@PostMapping(value = "/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest user, HttpServletRequest request,
			HttpServletResponse response) {
		List<UserDetails> userList = userService.login(user);

		if (userList.size() != 0) {
			String token = Utility.jwtToken(userList.get(0).getId());
			response.setHeader("JwtToken", token);
			response.addHeader("Access-Control-Allow-Headers", "*");
			response.addHeader("Access-Control-Expose-Headers", "*");

			return new ResponseEntity<String>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(" {invalid user}", HttpStatus.BAD_REQUEST);

		}

	}

	// UPDATE
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public String userUpdate(HttpServletRequest request, @RequestBody UserDetails user) {
		String token = request.getHeader("jwtToken");
		System.out.println(token);
		userService.updateUser(token, user);
		return "Updated";

	}

	// DELETE
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public String userDelete(HttpServletRequest request) {
		String token = request.getHeader("jwtToken");
		System.out.println(token);
		userService.deleteUser(token);
		return "Deleted";

	}

	@GetMapping(value = "/fetching")
	// @RequestMapping(value = "/fetching", method = RequestMethod.GET)
	public List<UserDetails> fetch(HttpServletRequest request) {
		return userRepository.findAll();

	}

}