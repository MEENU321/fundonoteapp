package com.note.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.note.model.LoginRequest;
import com.note.model.UserDetails;
import com.note.repository.UserRepository;
import com.note.util.Utility;



@Service
@Transactional

public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	JavaMailSender sender;
	@Autowired
	Utility tokenClass;

	private LoginRequest user;
	

	
	@Override
	public UserDetails UserRegistration(UserDetails user, HttpServletRequest request) {
		System.out.println(securePassword(user.getPassword()));
		user.setPassword(securePassword(user.getPassword()));
		userRepository.save(user);
//		Optional<UserDetails> user1 = userRepository.findByUserId(user.getUserId());
		if (user != null) {
			System.out.println("Sucessfull reg");
			// Optional<User> maybeUser = userRep.findById(user.getId());
			String tokenGen = tokenClass.jwtToken(user.getId());
//			UserDetails u = user1.get();
			StringBuffer requestUrl = request.getRequestURL();
			System.out.println(requestUrl);
			String appUrl = requestUrl.substring(0, requestUrl.lastIndexOf("/"));
			appUrl = appUrl + "/activestatus/" + "token=" + tokenGen;
			System.out.println(appUrl);
			String subject = "User Activation";

			String s = sendmail(subject, user, appUrl);
			System.out.println(s);
			// return "Mail Sent Successfully";
			return user;

		}

		else {
			System.out.println("Not sucessful reg");
		}
		return user;
	}

	@Override
	public List<UserDetails> login(LoginRequest user) {
		List<UserDetails> userList = userRepository.findByEmailAndPassword(user.getEmail(),
				securePassword(user.getPassword()));
		return userList;
	}
//	public UserDetails retrieveCourse(String email, String password) {
//		Student student = retrieveStudent(studentId);
///List<UserDetails> userList = userService.login(user);

///	/if (userList.size() != 0)
//		if (student == null) {
//			return null;
//		}
//
//		for (Course course : student.getCourses()) {
//			if (course.getId().equals(courseId)) {
//				return course;
//			}
//		}
//
//		return null;
	

	@Override
	public String securePassword(String password) {
		String generatedPassword = null;
		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			// Add password bytes to digest
			md.update(password.getBytes());
			// Get the hash's bytes
			byte[] bytes = md.digest();
			// This bytes[] has bytes in decimal format;
			// Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			// Get complete hashed password in hex format
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		System.out.println(generatedPassword);

		return generatedPassword;
	}

	@Override
	public UserDetails updateUser(String token, UserDetails user) {
		int varifiedUserId = Utility.parseJWT(token);
		Optional<UserDetails> maybeUser = userRepository.findById(varifiedUserId);
		UserDetails presentUser = maybeUser.map(existingUser -> {
			existingUser.setEmail(user.getEmail() != null ? user.getEmail() : maybeUser.get().getEmail());
			existingUser.setMobileNo(user.getMobileNo() != null ? user.getMobileNo() : maybeUser.get().getMobileNo());
			existingUser.setUserName(user.getUserName() != null ? user.getUserName() : maybeUser.get().getUserName());
			existingUser.setPassword(user.getPassword() != null ? securePassword(user.getPassword())
					: securePassword(maybeUser.get().getPassword()));
			return existingUser;
		}).orElseThrow(() -> new RuntimeException("User Not Found"));

		return userRepository.save(presentUser);
	}

	@Override
	public boolean deleteUser(String token) {
		int varifiedUserId = Utility.parseJWT(token);

		// return userRep.deleteById(varifiedUserId);
		Optional<UserDetails> maybeUser = userRepository.findById(varifiedUserId);
		return maybeUser.map(existingUser -> {
			userRepository.delete(existingUser);
			return true;
		}).orElseGet(() -> false);
	}

	@Override
	public List<UserDetails> findByEmailId(String email) {

		return userRepository.findByEmail(email);
	}

	@Override
	public Optional<UserDetails> findById(int id) {
		return userRepository.findById(id);
	}

	public String sendmail(String subject, UserDetails userdetails, String appUrl) {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {

			helper.setTo(userdetails.getEmail());
			helper.setText("To reset your password, click the link below:\n" + appUrl);
			helper.setSubject(subject);
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Error while sending mail ..";
		}
		sender.send(message);
		return "Mail Sent Success!";
	}

//	@Override
//	public List<UserDetails> loginTest(String email, String password) {
//		List<UserDetails> userList = userRepository.findByEmailAndPassword(user.getEmail(),
//				securePassword(user.getPassword()));
//		return userList;
//	}

	
}
