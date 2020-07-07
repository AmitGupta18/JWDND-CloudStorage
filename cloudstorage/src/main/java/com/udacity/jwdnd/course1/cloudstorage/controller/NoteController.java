package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.udacity.jwdnd.course1.cloudstorage.data.Note;
import com.udacity.jwdnd.course1.cloudstorage.data.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;

@Controller
@RequestMapping("/note")
public class NoteController {

	private NoteService noteService;

	public NoteController(NoteService noteService) {
		this.noteService = noteService;
	}

	@PostMapping
	public String addUpdateNote(Authentication authentication, Note note) {
		User user = (User) authentication.getPrincipal();

		if (note.getNoteId() > 0) {
			noteService.updateNote(note);
		} else {
			noteService.addNote(note, user.getUserId());
		}
		return "redirect:/result?success";
	}

	@GetMapping("/delete/{id}")
	public String deleteNote(@PathVariable("id") Integer noteId, RedirectAttributes attr) {
		if(noteId < 0) {
			attr.addFlashAttribute("errorMsg", "Note with id "+noteId+ " is not available.");
			return "redirect:/result?error";
		}
		noteService.deleteNote(noteId);
		return "redirect:/result?success";
	}

}
