package com.note.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.note.util.*;
import com.note.model.Note;

@Repository
public interface NoteReposirory extends JpaRepository<Note, Long> {

	public List<Note> findByUserId(int id);

	public List<Note> findByNoteId(int noteId);

	public Optional<Note> findByNoteIdAndUserId(int noteId, int userId);
  //  public Optional<Note> findByUserIdAndNoteId(Utility.parsejwt(jwt),NoteId);
	
//Util.tokenVerification(token), note.getNoteId());
	public void deleteByNoteId(int noteId);
}