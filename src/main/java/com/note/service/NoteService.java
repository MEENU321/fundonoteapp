package com.note.service;

import java.util.List;

import com.note.model.Note;

public interface NoteService {

	//public Note createNote(Note user, String token);
	public Note createNote(Note note, String token);
	public Note findById(int userId);

	public Note updateNote(Note note, String token);

	public String deleteNote(int noteId, String token);

	public Note getNoteInfo(int noteId);

	public List<Note> getAllNotes();

	public List<Note> getNotes(String token);
}