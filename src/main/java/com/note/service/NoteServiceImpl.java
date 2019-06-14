package com.note.service;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.note.model.Label;
import com.note.model.Note;
import com.note.repository.LabelRepository;
import com.note.repository.NoteReposirory;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {

	@Autowired
	NoteReposirory noteRepository;

	

	@Autowired
	private LabelRepository labelRepository;

	@Override
	public Note createNote(Note note, String token) {
		int userId = com.note.util.Utility.parseJWT(token);
		System.out.println(userId);
	Date date = new Date();

			
			LocalDateTime time=LocalDateTime.now();
			note.setCreatedOn(time);
			return noteRepository.save(note);
			}

	

	@Override
	public Note findById(int userId) {
		List<Note> noteInfo = noteRepository.findByUserId(userId);
		return noteInfo.get(0);
	}

	@Override      
	public Note updateNote(Note note, String token) {
		 
	//int noteId = note.getNoteId();
	
	//Optional<Note> maybeNote = noteRepository.findByNoteIdAndUserId(noteId, com.note.util.Utility.parseJWT(token));
	Optional<Note> maybeNote = noteRepository.findByNoteIdAndUserId(note.getNoteId(), note.getUserId());
	//Optional<Note> maybeNote = noteRepository.findByIdAndUserId();
	System.out.println("maybeNote :" + maybeNote);
	Note presentNote = maybeNote.map(existingNote -> {
	System.out.println("noteee here");
	
	existingNote.setDescription(
	note.getDescription() != null ? note.getDescription() : maybeNote.get().getDescription());
	existingNote.setTitle(note.getTitle() != null ? note.getTitle() : maybeNote.get().getTitle());
	existingNote.setInTrash(note.isInTrash());
	existingNote.setArchive(note.isArchive());
	existingNote.setPinned(note.isPinned());
	return existingNote;
	}).orElseThrow(() -> new RuntimeException("Note Not Found"));

	return noteRepository.save(presentNote);
	}

	

	@Override
	public String deleteNote(int noteId) {
		//int userId = com.note.util.Utility.parseJWT(token);
		//List<Note> noteInfo = noteRepository.findByNoteIdAndUserId(noteId, userId);
		noteRepository.deleteByNoteId(noteId);
		return "Deleted";
	}

	@Override
	public Note getNoteInfo(int noteId) {
		List<Note> noteInfo = noteRepository.findByNoteId(noteId);
		return noteInfo.get(0);
	}

	@Override
	public List<Note> getAllNotes() {

		return noteRepository.findAll();
	}

	@Override
	public List<Note> getNotes(String token) {
		int id = com.note.util.Utility.parseJWT(token);
		List<Note> list = noteRepository.findByUserId(id);
		return list;
	}

	@Override
	public Label labelCreate(Label label, String token) {
		int userId =com.note.util.Utility.parseJWT(token);
		label.setUserId(userId);

		return labelRepository.save(label);
	}

	@Override
	public Label labelUpdate(Label label, String token, int labelId) {
		int userId = com.note.util.Utility.parseJWT(token);
		List<Label> list = labelRepository.findByUserIdAndLabelId(userId, labelId);
		list.forEach(userLabel -> {
			userLabel.setLabelName(label.getLabelName() != null ? label.getLabelName() : list.get(0).getLabelName());
		});
		label.setLabelId(labelId);
		label.setUserId(userId);
		return labelRepository.save(label);
	}

	@Override
	public String labelDelete(String token, int labelId) {
		int userId = com.note.util.Utility.parseJWT(token);
		List<Label> list = labelRepository.findByUserIdAndLabelId(userId, labelId);
		labelRepository.delete(list.get(0));
		return "Deleted";
	}

	@Override
	public List<Label> getLabels(String token) {
		int userId = com.note.util.Utility.parseJWT(token);
		List<Label> list = labelRepository.findByUserId(userId);
		return list;
	}
}
