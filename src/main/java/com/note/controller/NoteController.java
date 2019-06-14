package com.note.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.note.model.Note;
import com.note.service.NoteService;

import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NoteController {

	@Autowired
	private NoteService noteservice;

	/*
	 * // @RequestMapping(value = "/notecreate", method = RequestMethod.POST) //
	 * public Note noteCreate(@RequestBody Note note, HttpServletRequest request) {
	 * // String token = request.getHeader("token"); // // return
	 * noteService.createNote(note, token); // // }
	 */	/*
	 * // @PostMapping(value = "/notecreate/{token}") // public
	 * ResponseEntity<String> createUser(@PathVariable String token, @RequestBody
	 * Note note, // HttpServletRequest request) { // // // String token =
	 * request.getHeader("token"); // // return new
	 * ResponseEntity<>("{note created succesfully}", HttpStatus.OK); // }
	 */
	@PostMapping(value = "/notecreate/{token}")
	public ResponseEntity<Note> noteCreate(@PathVariable String token,  @RequestBody Note note, HttpServletRequest request) {
	//String token = request.getHeader("token");
	return new ResponseEntity<Note>(noteservice.createNote(note, token),HttpStatus.CREATED);

	}
	
		
	@RequestMapping(value = "/noteupdate/{token}", method = RequestMethod.PUT)
	public Note noteUpdate(@RequestBody Note note,@PathVariable String token, HttpServletRequest request) {
		//String token = request.getHeader("token");
		return noteservice.updateNote(note, token);

	}
	
	@RequestMapping(value = "/notedelete/{noteId}", method = RequestMethod.DELETE)
	public String noteDelete(@PathVariable int noteId, HttpServletRequest request) {
		//String token = request.getHeader("token");
		return noteservice.deleteNote(noteId);

	}

	@RequestMapping(value = "/note/{noteId}", method = RequestMethod.GET)
	public Note noteInfo(@PathVariable int noteId) {
		return noteservice.getNoteInfo(noteId);

	}
	@Cacheable(value = "users", key = "#userId")
	@GetMapping("/testRedis/{userId}")
	//@ApiResponse(response = String.class, message = "Test Redis	", code = 200)
	public String testRedis(@ApiParam("userId") @PathVariable String userId) {
	return "Success" + userId;
	}

	//@CachePut(key = "test")
	@Cacheable(value = "users", key = "#userId")
	@PostMapping("/testRedis/{userId}")
	@ApiResponse(response = String.class, message = "Test Redis post", code = 200)
	public String postRedis(@ApiParam("userId") @PathVariable String userId) {
	return "{}";
	}

	@RequestMapping(value = "/viewnotes", method = RequestMethod.GET)
	public List<Note> noteList() {
		return noteservice.getAllNotes();
	}

	@RequestMapping(value = "/notelist", method = RequestMethod.GET)
	public List<Note> noteList(HttpServletRequest request) {
		String token = request.getHeader("token");
		return noteservice.getNotes(token);
	}
}