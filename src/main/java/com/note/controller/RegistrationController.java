package com.note.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.note.model.UserDetails;
import com.note.service.UserService;



@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RegistrationController {

	@Autowired
	UserService userService;
//
//	@RequestMapping(value = "/registration", method = RequestMethod.POST)
//	public UserDetails createStudent(@RequestBody UserDetails user,HttpServletRequest request) {
//		return userService.UserRegistration(user,request);
//	}
	@PostMapping(value = "/registration")
    public ResponseEntity<String> createUser(@RequestBody UserDetails user,HttpServletRequest request) {

         userService.UserRegistration(user,request);
         return new ResponseEntity<>("{registration succesfull}", HttpStatus.OK);
    }

}
