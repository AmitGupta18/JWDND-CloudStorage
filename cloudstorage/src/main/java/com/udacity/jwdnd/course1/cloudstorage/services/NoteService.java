package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.data.Note;
import com.udacity.jwdnd.course1.cloudstorage.data.mappers.NoteMapper;

@Service
public class NoteService {
	private final NoteMapper noteMapper;

	public NoteService(NoteMapper noteMapper) {
		this.noteMapper = noteMapper;
	}

	/**
	 * @param username
	 * @return
	 */
	public List<Note> getNotes(Integer userid) {
		return noteMapper.getNotesByUser(userid);
	}

	/**
	 * @param user
	 * @return
	 */
	public int addNote(Note note, Integer userId) {
		return noteMapper.insert(new Note(null, note.getNoteTitle(), note.getNoteDescription(), userId));
	}

	public void deleteNote(Integer noteId) {
		noteMapper.deleteNote(noteId);
	}

	public int updateNote(Note note) {
		return noteMapper.updateNote(note);
	}

}
